package com.iot.migration.constants;

public class QueryConstants {

    public static final String iotData = "select * from iot_mgr where hesap_no = :hesapNo";
    public static final String iotInsert = "INSERT INTO IOT_MGR " +
            "(HESAP_NO, FATURA_NO, MALIYE_FATURA_NO, FATURA_GONDERIM_TIPI, FATURA_DONEM, MSISDN, TUTAR, KDV_ORAN, OIV_ORAN, BAYI_KODU, KAMPANYA_ID, IMEI, SAP_GLCODE, DISTRIBUTOR_KODU, BANKA_KODU, TARIH, AKTARIM_DURUMU) " +
            "VALUES(:hesapNo, :faturaNo, :maliyeFaturaNo, :faturaGonderimTipi, :faturaDonemi, :MSISDN, :tutar, :KDVOran, :OIVOran, :bayiKodu, :kampanyaId, :IMEI, :sapGlCode, :distributorKodu, :bankaKodu, :tarih, :aktarimDurumu)";

}
