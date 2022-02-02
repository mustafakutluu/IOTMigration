package com.iot.migration.service;

import com.iot.migration.constants.QueryConstants;
import com.iot.migration.dao.IOTDaoImpl;
import com.iot.migration.model.IOTPojo;
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
    private IOTDaoImpl iotDao;

    @Async
    public CompletableFuture<Boolean> process(Map<String, Long> banks, Map<String, BigDecimal> kdvRates,
                                              Map<String, BigDecimal> oivRates, int start, int finish, IOTDaoImpl iotDao) throws InterruptedException {
        List<IOTPojo> resultList = this.iotDao.getMigrationData(start, finish);

        for(IOTPojo iot:resultList) {
            String servcatCode = iot.getServCatCode();

            if(servcatCode != null && servcatCode.equals("EXEMPT")){
                iot.setKDVOran(BigDecimal.ZERO);
                iot.setOIVOran(BigDecimal.ZERO);
            } else{
                iot.setKDVOran(kdvRates.get(iot.getServCatCode()));
                iot.setOIVOran(oivRates.get(iot.getServCatCode()));
            }

            iot.setBankaKodu(banks.get(iot.getBank()));
        }

        logger.debug("migrate data count: " + resultList.size());

        if(resultList != null && !resultList.isEmpty()){
            logger.debug("starting insert migration data!");

            /*https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/scheduling.html
            i2iDao.setDataSource(i2iDataSource);

            i2iDao.batchInsert(QueryConstants.iotInsert, resultList);*/

            this.iotDao.batchInsert(QueryConstants.iotInsert, resultList);

            logger.debug("insert migration data finished!");
        }

        return CompletableFuture.completedFuture(true);
    }

}
