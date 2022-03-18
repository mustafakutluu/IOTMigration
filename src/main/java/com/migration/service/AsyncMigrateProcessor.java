package com.migration.service;

import com.migration.constants.QueryConstants;
import com.migration.dao.SecondDaoImpl;
import com.migration.model.MigrationPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class AsyncMigrateProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AsyncMigrateProcessor.class);

    @Autowired
    private SecondDaoImpl secondDao;

    @Async
    public CompletableFuture<Boolean> process(Map<String, Long> banks, Map<String, BigDecimal> kdvRates,
                                              Map<String, BigDecimal> oivRates, int start, int finish) throws InterruptedException {
        List<MigrationPojo> resultList = this.secondDao.getMigrationData(start, finish);

        for(MigrationPojo iot:resultList) {
            String taxCode = iot.getTaxCode();

            if(taxCode != null && taxCode.equals("NO_TAX")){
                iot.setKDVOran(BigDecimal.ZERO);
                iot.setOIVOran(BigDecimal.ZERO);
            } else{
                iot.setKDVOran(kdvRates.get(iot.getTaxCode()));
                iot.setOIVOran(oivRates.get(iot.getTaxCode()));
            }

            iot.setBankaKodu(banks.get(iot.getBank()));
        }

        logger.debug("migrate data count: " + resultList.size());

        if(resultList != null && !resultList.isEmpty()){
            logger.debug("starting insert migration data!");

            /*https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/scheduling.html
            firstDao.setDataSource(firstDataSource);

            firstDao.batchInsert(QueryConstants.insert, resultList);*/

            this.secondDao.batchInsert(QueryConstants.insert, resultList);

            logger.debug("insert migration data finished!");
        }

        return CompletableFuture.completedFuture(true);
    }

}
