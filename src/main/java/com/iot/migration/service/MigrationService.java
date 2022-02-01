package com.iot.migration.service;

import com.iot.migration.constants.QueryConstants;
import com.iot.migration.dao.I2IDaoImpl;
import com.iot.migration.dao.IOTDaoImpl;
import com.iot.migration.model.Bank;
import com.iot.migration.model.IOTPojo;
import com.iot.migration.model.TaxCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MigrationService{

    private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);

    @Value("${spring.iot.poolSize}")
    private int poolSize;

    @Autowired
    private IOTDaoImpl iotDao;

    @Autowired
    private DataSource iotDataSource;

    @Autowired
    private I2IDaoImpl i2iDao;

    @Autowired
    private DataSource i2iDataSource;


    public void migrate () {

        Map<String, Long> bankMap = null;
        Map<String, BigDecimal> kdvMap = null;
        Map<String, BigDecimal> oivMap = null;
        Map<String, Integer> threadDataCounts = null;
        List<CompletableFuture<Boolean>> futureList;

        iotDao.setDataSource(iotDataSource);

        List<Bank> bankList = iotDao.getBankData();

        if(bankList != null && !bankList.isEmpty()) {
            bankMap = bankList.stream()
                    .collect(Collectors.toMap(Bank::getBank, Bank::getBankCode));
        }

        List<TaxCategory> taxCategoryList = iotDao.getTaxCategoryData();

        if(taxCategoryList != null && !taxCategoryList.isEmpty()){
            kdvMap = taxCategoryList.stream().filter(e -> e.getTaxCatName().startsWith("KDV"))
                    .collect(Collectors.toMap(TaxCategory::getServCatCode, TaxCategory::getTaxRate));
            oivMap = taxCategoryList.stream().filter(e -> e.getTaxCatName().startsWith("Special"))
                    .collect(Collectors.toMap(TaxCategory::getServCatCode, TaxCategory::getTaxRate));
        }

        //toplam data count
        int migrationDataCount = 0;
        migrationDataCount = iotDao.count(QueryConstants.migrationData);

        //hesap(/)
        if(migrationDataCount > 0) {

            futureList = new ArrayList<>();
            threadDataCounts = calculateDataChunkSize(migrationDataCount);

            int start = 0;
            int finish = threadDataCounts.get("chunkSize");


            for(int i=1; i<=poolSize; i++) {

                if(i == poolSize)
                    finish = finish + threadDataCounts.get("dataRemainderCount");

                CompletableFuture<Boolean> feature= process(bankMap, kdvMap, oivMap, start, finish);
                futureList.add(feature);

                start = (threadDataCounts.get("chunkSize")*i) + 1;
                finish = threadDataCounts.get("chunkSize") * ++i;

            }

            //await for all futures
            if (!futureList.isEmpty()) {
                try {
                    CompletableFuture.allOf(futureList.toArray(new CompletableFuture<?>[0])).join();
                } catch (CompletionException e) {
                    logger.debug("insertion failed!!!!");
                    e.printStackTrace();
                }
            }
        }
    }


    @Async
    public CompletableFuture<Boolean> process(Map<String, Long> banks, Map<String, BigDecimal> kdvRates,
                                                    Map<String, BigDecimal> oivRates, int start, int finish){

        List<IOTPojo> resultList = iotDao.getMigrationData(start, finish);

        for(IOTPojo iot:resultList) {
            iot.setBankaKodu(banks.get(iot.getBank()));
            iot.setKDVOran(kdvRates.get(iot.getServCatCode()));
            iot.setOIVOran(oivRates.get(iot.getServCatCode()));
        }

        logger.debug("migrate data count: " + resultList.size());

        if(resultList != null && !resultList.isEmpty()){
            logger.debug("starting insert migration data!");

            /*https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/scheduling.html
            i2iDao.setDataSource(i2iDataSource);

            i2iDao.batchInsert(QueryConstants.iotInsert, resultList);*/

            iotDao.batchInsert(QueryConstants.iotInsert, resultList);

            logger.debug("insert migration data finished!");
        }

        return CompletableFuture.completedFuture(true);
    }

    private Map<String, Integer> calculateDataChunkSize(int dataCount){

        int threadDataCount = dataCount/poolSize;

        int totalDataRemainderCount = dataCount - (threadDataCount*poolSize);

        Map<String, Integer> countResult = new HashMap<>();
        countResult.put("chunkSize", threadDataCount);
        countResult.put("dataRemainderCount", totalDataRemainderCount);


        return countResult;
    }

    /*private long decideBankCode(String bankParameter, List<Bank> banks) {
        long bankCode = 0l;

        for(Bank bank : banks){
            if(bank.getBank().equals(bankParameter)){
                bankCode = bank.getBankCode();
                break;
            }
        }
        return bankCode;
    }

    private BigDecimal decideTaxRate(String servcatCode, List<TaxCategory> taxCategories, String taxPrefix) {

        BigDecimal oiv = BigDecimal.ZERO;
        for(TaxCategory taxCategory : taxCategories) {
            if(taxCategory.getServCatCode().equals(servcatCode) && taxCategory.getTaxCatName().startsWith(taxPrefix)){
                oiv = taxCategory.getTaxRate();
                break;
            }
        }
        return oiv;
    }*/
}
