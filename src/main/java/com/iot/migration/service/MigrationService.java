package com.iot.migration.service;

import com.iot.migration.constants.QueryConstants;
import com.iot.migration.dao.I2IDaoImpl;
import com.iot.migration.dao.IOTDaoImpl;
import com.iot.migration.model.IOTPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class MigrationService{

    private static Logger logger = LoggerFactory.getLogger(MigrationService.class);

    @Autowired
    private IOTDaoImpl iotDao;

    @Autowired
    private DataSource iotDataSource;

    @Autowired
    private I2IDaoImpl i2iDao;

    @Autowired
    private DataSource i2iDataSource;

    public void migrate(){

        iotDao.setDataSource(iotDataSource);

        List<IOTPojo> resultList = iotDao.getMigrationData(12195262l);

        logger.debug("migrate data count: " + resultList.size());

        if(resultList != null && !resultList.isEmpty()){
            logger.debug("starting insert migration data!");

            i2iDao.setDataSource(i2iDataSource);

            i2iDao.batchInsert(QueryConstants.iotInsert, resultList);

            logger.debug("insert migration data finished!");
        }

    }

}
