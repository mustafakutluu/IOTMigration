package com.migration.service;

import com.migration.configuration.AsyncConfiguration;
import com.migration.configuration.LogExecutionTime;
import com.migration.constants.QueryConstants;
import com.migration.dao.FirstDaoImpl;
import com.migration.dao.SecondDaoImpl;
import com.migration.exception.ChunkSizeException;
import com.migration.model.Bank;
import com.migration.model.TaxCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Service
public class MigrationService{

    private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);

    @Value("${spring.migrate.poolSize}")
    private int poolSize;

    @Autowired
    private AsyncMigrateProcessor processor;

    @Autowired
    private SecondDaoImpl secondDao;

    @Autowired
    private DataSource secondDataSource;

    @Autowired
    private AsyncConfiguration threadPoolTaskExecutor;

    @Autowired
    private FirstDaoImpl firstDao;

    @Autowired
    private DataSource firstDataSource;

    @LogExecutionTime
    public void migrate () throws InterruptedException, ChunkSizeException {

        Map<String, Long> bankMap = null;
        Map<String, BigDecimal> kdvMap = null;
        Map<String, BigDecimal> oivMap = null;
        Map<String, Integer> threadDataCounts = null;
        List<CompletableFuture<Boolean>> futureList;

        secondDao.setDataSource(secondDataSource);

        int migrationDataCount = secondDao.count(QueryConstants.migrationData);

        if(migrationDataCount < poolSize) {
            logger.error("Data count is less than pool size. Data count: " + migrationDataCount + ", Pool size: " + poolSize);
            throw new ChunkSizeException("Data count cannot be less than thread pool size!");
        }

        List<Bank> bankList = secondDao.getBankData();

        if(bankList != null && !bankList.isEmpty()) {
            bankMap = bankList.stream()
                    .collect(Collectors.toMap(Bank::getBank, Bank::getBankCode));
        }

        List<TaxCategory> taxCategoryList = secondDao.getTaxCategoryData();

        if(taxCategoryList != null && !taxCategoryList.isEmpty()){
            kdvMap = taxCategoryList.stream().filter(e -> e.getTaxName().startsWith("KDV"))
                    .collect(Collectors.toMap(TaxCategory::getTaxCode, TaxCategory::getTaxRate));
            oivMap = taxCategoryList.stream().filter(e -> e.getTaxName().startsWith("Special"))
                    .collect(Collectors.toMap(TaxCategory::getTaxCode, TaxCategory::getTaxRate));
        }

        if(migrationDataCount > 0) {

            futureList = new ArrayList<>();
            threadDataCounts = calculateDataChunkSize(migrationDataCount);

            int start = 0;
            int finish = threadDataCounts.get("chunkSize");


            for(int i=1; i<=poolSize; i++) {

                if(i == poolSize)
                    finish = finish + threadDataCounts.get("dataRemainderCount");

                CompletableFuture<Boolean> feature= processor.process(bankMap, kdvMap, oivMap, start, finish);
                futureList.add(feature);

                logger.info("Number of currently active threads: " + threadPoolTaskExecutor.getActiveThreadCount());

                start = (threadDataCounts.get("chunkSize")*i) + 1;
                finish = threadDataCounts.get("chunkSize") * (i+1);

            }

            //await for all futures
            if (!futureList.isEmpty()) {
                try {
                    CompletableFuture.allOf(futureList.toArray(new CompletableFuture<?>[0])).join();
                    logger.info("Number of currently active threads after finish: " + threadPoolTaskExecutor.getActiveThreadCount());
                } catch (CompletionException e) {
                    logger.info("insertion failed!!!!");
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<String, Integer> calculateDataChunkSize(int dataCount){

        int threadDataCount = dataCount/poolSize;
        int totalDataRemainderCount = dataCount - (threadDataCount*poolSize);

        Map<String, Integer> countResult = new HashMap<>();
        countResult.put("chunkSize", threadDataCount);
        countResult.put("dataRemainderCount", totalDataRemainderCount);

        return countResult;
    }
}
