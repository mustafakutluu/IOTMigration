package com.iot.migration.mapper;

import com.iot.migration.model.Bank;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankMapper implements RowMapper<Bank> {
    @Override
    public Bank mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bank bankModel = new Bank();

        bankModel.setBank(rs.getString("BANK"));
        bankModel.setBankCode(rs.getLong("BANK_CODE"));
        bankModel.setBankName(rs.getString("BANK_NAME"));

        return bankModel;
    }
}
