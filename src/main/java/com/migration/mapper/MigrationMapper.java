package com.migration.mapper;

import com.migration.model.MigrationPojo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MigrationMapper implements RowMapper<MigrationPojo> {

    @Override
    public MigrationPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
        MigrationPojo iotModel = new MigrationPojo();

        iotModel.setAktarimDurumu(rs.getInt("aktarim_durumu"));
        iotModel.setBayiKodu(rs.getString("bayi_kodu"));
        iotModel.setFaturaDonemi(rs.getLong("fatura_donem"));
        iotModel.setFaturaNo(rs.getString("fatura_no"));
        iotModel.setHesapNo(rs.getLong("hesap_no"));
        iotModel.setIMEI(rs.getString("IMEI"));
        iotModel.setKampanyaId(rs.getLong("kampanya_id"));
        iotModel.setMSISDN(rs.getString("MSISDN"));
        iotModel.setTarih(rs.getString("tarih"));
        iotModel.setTutar(rs.getBigDecimal("tutar"));
        iotModel.setBank(rs.getString("bank"));

        return iotModel;
    }
}
