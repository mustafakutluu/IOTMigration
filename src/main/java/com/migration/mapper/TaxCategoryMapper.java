package com.migration.mapper;

import com.migration.model.TaxCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxCategoryMapper implements RowMapper<TaxCategory> {
    @Override
    public TaxCategory mapRow(ResultSet rs, int rowNum) throws SQLException {

        TaxCategory taxCategoryModel = new TaxCategory();

        taxCategoryModel.setTaxCode(rs.getString("TAX_CODE"));
        taxCategoryModel.setTaxId(rs.getLong("TAX_ID"));
        taxCategoryModel.setTaxName(rs.getString("TAX_NAME"));
        taxCategoryModel.setTaxRate(rs.getBigDecimal("TAXRATE"));

        return taxCategoryModel;
    }
}
