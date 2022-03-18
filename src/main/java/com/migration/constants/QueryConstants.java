package com.migration.constants;

public class QueryConstants {

    public static final String data = "select * from mgr where hesap_no = :hesapNo";
    public static final String insert = "INSERT INTO MGR " +
            "(HESAP_NO, FATURA_NO, FATURA_DONEM, MSISDN, TUTAR, KDV_ORAN, OIV_ORAN, BAYI_KODU, KAMPANYA_ID, IMEI, BANKA_KODU, TARIH, AKTARIM_DURUMU) " +
            "VALUES(:hesapNo, :faturaNo, :faturaDonemi, :MSISDN, :tutar, :KDVOran, :OIVOran, :bayiKodu, :kampanyaId, :IMEI, :bankaKodu, :tarih, :aktarimDurumu)";
    public static final String bank = "SELECT * FROM BANK";
    public static final String taxCategories = "SELECT distinct TGPV.TAX_CODE, TAX_NAME , TRV.TAX_ID, TRV.TAXRATE "
            + "FROM TAX, REFERENCE TRV ";

    public static final String countQueryPrefix = "select count(1) from ( ";
    public static final String countQuerySuffix = " )";

    public static final String limitOffsetPrefix = "SELECT * from( " +
            "SELECT /*+ FIRST_ROWS(n) */ T.*, rownum rn from( ";
    public static final String limitOffsetSuffix = " ) T WHERE rownum <= :finish) " +
            "WHERE rn >= :start ";

    public static final String migrationData = "SELECT /*+ PARALLEL(10)*/ " +
            "OA.CUSTOMER_ID HESAP_NO, " +
            "OA.REF FATURA_NO, " +
            "TO_CHAR(BILL_DATE,'YYYYMM') FATURA_DONEM, " +
            "MSISDN, " +
            "PRICE TUTAR, " +
            "BRANCH_CODE BAYI_KODU, " +
            "S.KAMPANYA_CODE KAMPANYA_ID , " +
            "IMEI IMEI, " +
            "TO_CHAR(SYSDATE,'DD.MM.YYYY') TARIH, " +
            "0 AKTARIM_DURUMU, " +
            "S.BANK, " +
            "FROM ALLORDERS O " +
            "JOIN ORDERDETAILS OA ON OA.ORDER_ID = O.ID " +
            "LEFT JOIN ALL_CONTACTS CA ON CA.CUSTOMER_ID=OA.CUSTOMER_ID  " +
            "LEFT OUTER JOIN DEVICES S ON CA.DEVICE_ID=S.ID " +
            "LEFT OUTER JOIN ALLCUSTOMERS CAL ON CAL.CUSTOMER_ID = CA.CUSTOMER_ID  " +
            "LEFT OUTER JOIN HISTORY MCH ON MCH.CUSTOMER_CODE = CAL.CUSTOMER_CODE ";

}
