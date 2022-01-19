package com.iot.migration.dao;

import com.iot.migration.constants.QueryConstants;
import com.iot.migration.model.IOTPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class IOTDaoImpl extends BaseDaoImpl implements IOTDao {

    private static Logger logger = LoggerFactory.getLogger(IOTDaoImpl.class);

    @Override
    public List<IOTPojo> getMigrationData(Long hesapNo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("hesapNo", hesapNo);

        return (List<IOTPojo>) queryForOList(QueryConstants.iotData, params);
    }

    @Override
    public IOTPojo insert(IOTPojo param) {
        int queryResult = insert(QueryConstants.iotInsert, param);
        return queryResult == 1 ? param : null;
    }

    @Override
    public List<IOTPojo> batchInsert(List<IOTPojo> params) {

        int[] resultArray = batchInsert(QueryConstants.iotInsert, params);

        return resultArray[0] == 1 ? params : null;
    }

}
