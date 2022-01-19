package com.iot.migration.dao;

import com.iot.migration.mapper.IOTMapper;
import com.iot.migration.model.IOTPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import javax.sql.DataSource;
import java.util.List;

public class BaseDaoImpl implements BaseDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Override
    public void setDataSource(DataSource dataSource) {
        try {
            jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        } catch (Exception e) {
            logger.error("Datasource setting process failed!");
            e.printStackTrace();
        }
    }

    @Override
    public List<?> queryForOList(String sql, SqlParameterSource params) {
        logger.debug("running queryForOList for sql: " + sql);

        List<IOTPojo> result = null;

        try {
            result = jdbcTemplate.query(sql, params, new IOTMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        logger.debug("result count: " + result.size());

        return result;
    }

    @Override
    public int count(String sql, Object[] params) {
        return 0;
    }

    @Override
    public int insert(String sql, Object params) {

        int result = 0;

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(params);

        try {
            result = jdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException e) {
            logger.error("Insert process failed!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int[] batchInsert(String sql, List<?> params) {

        int result[] = null;

        SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(params.toArray());

        try {
            result = jdbcTemplate.batchUpdate(sql, parameters);
        } catch (DataAccessException e) {
            logger.error("Batch insert process failed!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(String sql, Object[] param) {
        return 0;
    }

    @Override
    public Class<?> getClazz() {
        return null;
    }
}
