package com.iot.migration.dao;

import com.iot.migration.constants.QueryConstants;
import com.iot.migration.mapper.BankMapper;
import com.iot.migration.mapper.IOTMapper;
import com.iot.migration.mapper.TaxCategoryMapper;
import com.iot.migration.model.Bank;
import com.iot.migration.model.IOTPojo;
import com.iot.migration.model.TaxCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class IOTDaoImpl extends BaseDaoImpl implements IOTDao {

    private static final Logger logger = LoggerFactory.getLogger(IOTDaoImpl.class);

    @Override
    public List<Bank> getBankData() {

        return (List<Bank>) queryForOList(QueryConstants.iotBank, new MapSqlParameterSource(), new BankMapper());
    }

    @Override
    public List<TaxCategory> getTaxCategoryData() {

        return (List<TaxCategory>) queryForOList(QueryConstants.iotTaxCategories, new MapSqlParameterSource(), new TaxCategoryMapper());
    }

    @Override
    public List<IOTPojo> getMigrationData(int start, int finish) {

        StringBuilder queryStringBuilder = new StringBuilder(QueryConstants.limitOffsetPrefix).append(QueryConstants.migrationData).append(QueryConstants.limitOffsetSuffix);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("finish", finish);

        return (List<IOTPojo>) queryForOList(queryStringBuilder.toString(), params, new IOTMapper());
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
