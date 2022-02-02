package com.iot.migration.mapper;

import com.iot.migration.model.IOTPojo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IOTMapper implements RowMapper<IOTPojo> {

    @Override
    public IOTPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
        IOTPojo iotModel = new IOTPojo();

        iotModel.setAktarimDurumu(rs.getInt("aktarim_durumu"));
        iotModel.setBayiKodu(rs.getString("bayi_kodu"));
        iotModel.setDistributorKodu(rs.getString("distributor_kodu"));
        iotModel.setFaturaDonemi(rs.getLong("fatura_donem"));
        iotModel.setFaturaNo(rs.getString("fatura_no"));
        iotModel.setFaturaGonderimTipi(rs.getString("fatura_gonderim_tipi"));
        iotModel.setHesapNo(rs.getLong("hesap_no"));
        iotModel.setIMEI(rs.getString("IMEI"));
        iotModel.setKampanyaId(rs.getLong("kampanya_id"));
        iotModel.setMSISDN(rs.getString("MSISDN"));
        iotModel.setMaliyeFaturaNo(rs.getString("maliye_fatura_no"));
        iotModel.setSapGlCode(rs.getString("sap_GLCode"));
        iotModel.setTarih(rs.getString("tarih"));
        iotModel.setTutar(rs.getBigDecimal("tutar"));
        iotModel.setBank(rs.getString("bank"));
        iotModel.setServCatCode(rs.getString("servcat_code"));
        iotModel.setOtiSeqNo(rs.getLong("otseq"));
        iotModel.setOtxAct(rs.getLong("otxact"));

        return iotModel;
    }
}
