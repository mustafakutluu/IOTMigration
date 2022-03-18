package com.migration.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import javax.sql.DataSource;
import java.util.List;

public interface BaseDao {

    void setDataSource(DataSource dataSource);

    /**
     * @param sql
     * @param params
     * @return List
     */
    List<?> queryForOList(String sql, SqlParameterSource params, RowMapper mapper);

    /**
     * @param sql
     * @return int
     */
    int count(String sql);

    /**
     * @param sql
     * @param params
     * @return Object
     */
    int insert(String sql, Object params);

    /**
     * @param sql
     * @param params
     * @return Object
     */
    int[] batchInsert(String sql, List<?> params);

    /**
     * 更新方法
     * @param sql
     * @param param
     * @return int
     */
    int update(String sql, Object[] param);

    /**
     * @return Class
     */
    Class<?> getClazz();
}
