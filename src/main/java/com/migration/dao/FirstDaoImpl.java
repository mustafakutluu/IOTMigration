package com.migration.dao;

import com.migration.configuration.LogExecutionTime;
import com.migration.constants.QueryConstants;
import com.migration.mapper.MigrationMapper;
import com.migration.model.MigrationPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FirstDaoImpl extends BaseDaoImpl implements FirstDao {

    private static Logger logger = LoggerFactory.getLogger(FirstDaoImpl.class);

    @Override
    @LogExecutionTime
    public List<MigrationPojo> getMigrationData(Long hesapNo) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("hesapNo", hesapNo);

        return (List<MigrationPojo>) queryForOList(QueryConstants.data, params, new MigrationMapper());

    }

    @Override
    @LogExecutionTime
    public MigrationPojo insert(MigrationPojo param) {

        int queryResult = insert(QueryConstants.insert, param);
        return queryResult == 1 ? param : null;
    }

    @Override
    @LogExecutionTime
    public List<MigrationPojo> batchInsert(List<MigrationPojo> params) {

        int[] resultArray = batchInsert(QueryConstants.insert, params);

        return resultArray[0] == 1 ? params : null;
    }
}
