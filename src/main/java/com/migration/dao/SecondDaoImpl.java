package com.migration.dao;

import com.migration.configuration.LogExecutionTime;
import com.migration.constants.QueryConstants;
import com.migration.mapper.BankMapper;
import com.migration.mapper.MigrationMapper;
import com.migration.mapper.TaxCategoryMapper;
import com.migration.model.Bank;
import com.migration.model.MigrationPojo;
import com.migration.model.TaxCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SecondDaoImpl extends BaseDaoImpl implements SecondDao {

    private static final Logger logger = LoggerFactory.getLogger(SecondDaoImpl.class);

    @Override
    @LogExecutionTime
    public List<Bank> getBankData() {

        return (List<Bank>) queryForOList(QueryConstants.bank, new MapSqlParameterSource(), new BankMapper());
    }

    @Override
    @LogExecutionTime
    public List<TaxCategory> getTaxCategoryData() {

        return (List<TaxCategory>) queryForOList(QueryConstants.taxCategories, new MapSqlParameterSource(), new TaxCategoryMapper());
    }

    @Override
    @LogExecutionTime
    public List<MigrationPojo> getMigrationData(int start, int finish) {

        StringBuilder queryStringBuilder = new StringBuilder(QueryConstants.limitOffsetPrefix).append(QueryConstants.migrationData).append(QueryConstants.limitOffsetSuffix);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("finish", finish);

        return (List<MigrationPojo>) queryForOList(queryStringBuilder.toString(), params, new MigrationMapper());
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
