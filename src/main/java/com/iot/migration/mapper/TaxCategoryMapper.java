package com.iot.migration.mapper;

import com.iot.migration.model.TaxCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxCategoryMapper implements RowMapper<TaxCategory> {
    @Override
    public TaxCategory mapRow(ResultSet rs, int rowNum) throws SQLException {

        TaxCategory taxCategoryModel = new TaxCategory();

        taxCategoryModel.setServCatCode(rs.getString("SERVCAT_CODE"));
        taxCategoryModel.setTaxCatId(rs.getLong("TAXCAT_ID"));
        taxCategoryModel.setTaxCatName(rs.getString("TAXCAT_NAME"));
        taxCategoryModel.setTaxRate(rs.getBigDecimal("TAXRATE"));

        return taxCategoryModel;
    }
}
