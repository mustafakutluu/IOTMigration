package com.iot.migration.constants;

public class QueryConstants {

    public static final String iotData = "select * from iot_mgr where hesap_no = :hesapNo";
    public static final String iotInsert = "INSERT INTO IOT.IOT_MGR " +
            "(HESAP_NO, FATURA_NO, MALIYE_FATURA_NO, FATURA_GONDERIM_TIPI, FATURA_DONEM, MSISDN, TUTAR, KDV_ORAN, OIV_ORAN, BAYI_KODU, KAMPANYA_ID, IMEI, SAP_GLCODE, DISTRIBUTOR_KODU, BANKA_KODU, TARIH, AKTARIM_DURUMU) " +
            "VALUES(:hesapNo, :faturaNo, :maliyeFaturaNo, :faturaGonderimTipi, :faturaDonemi, :MSISDN, :tutar, :KDVOran, :OIVOran, :bayiKodu, :kampanyaId, :IMEI, :sapGlCode, :distributorKodu, :bankaKodu, :tarih, :aktarimDurumu)";
    public static final String iotBank = "SELECT * FROM BANK_CODE";
    public static final String iotTaxCategories = "SELECT distinct TGPV.SERVCAT_CODE, TAXCAT_NAME , TRV.TAXCAT_ID, TRV.TAXRATE "
            + "FROM TAX_GROUP_PACKAGE_VIEW TGPV, TAX_REFERENCE_VIEW TRV "
            + "WHERE TRV.INVALID_FROM > SYSDATE "
            + "AND TRV.TAX_GROUP_ID = TGPV.TAX_GROUP_ID "
            + "AND TGPV.customercat_code = 1";

    public static final String countQueryPrefix = "select count(1) from ( ";
    public static final String countQuerySuffix = " )";

    public static final String limitOffsetPrefix = "SELECT * from( " +
            "SELECT /*+ FIRST_ROWS(n) */ T.*, rownum rn from( ";
    public static final String limitOffsetSuffix = " ) T WHERE rownum <= :finish) " +
            "WHERE rn >= :start ";

    public static final String migrationData = "SELECT /*+ PARALLEL(10)*/ " +
            "O.OTSEQ, " +
            "O.OTXACT, " +
            "OA.CUSTOMER_ID HESAP_NO, " +
            "OA.OHREFNUM FATURA_NO, " +
            "MCH.FATURA_ID MALIYE_FATURA_NO, " +
            "SYSADM.FN_IOT_INVOICE_SUBMISSION_TYPE (MCH.FATURA_ID , CAL.CUSTOMER_ID, CAL.CSTYPE, CAL.CUSTCODE) FATURA_GONDERIM_TIPI, " +
            "TO_CHAR(OHREFDATE,'YYYYMM') FATURA_DONEM, " +
            "SYSADM.FN_IOT_MSISDN(CAL.CUSTOMER_ID,CAL.CUSTCODE,CAL.PRGCODE) MSISDN, " +
            "OTMERCH_GL TUTAR, " +
            "DEALER BAYI_KODU, " +
            "S.SNCODE KAMPANYA_ID , " +
            "IMEI IMEI, " +
            "O.OTGLSALE SAP_GLCODE, " +
            "S.DISTRIBUTOR DISTRIBUTOR_KODU, " +
            "TO_CHAR(SYSDATE,'DD.MM.YYYY') TARIH, " +
            "0 AKTARIM_DURUMU, " +
            "S.BANK, " +
            "O.SERVCAT_CODE " +
            "FROM ORDERTRAILER O " +
            "JOIN ORDERHDR_ALL OA  ON OA.OHXACT = O.OTXACT " +
            "LEFT JOIN CONTRACT_ALL CA ON CA.CUSTOMER_ID=OA.CUSTOMER_ID  " +
            "LEFT OUTER JOIN SUBSCRIBER_DEVICE S ON CA.CO_ID=S.CO_ID AND O.SNCODE =S.OCC_SNCODE AND OTSHIPDATE BETWEEN DEVICE_ACTIVATION_DATE AND ACTUAL_END_DATE AND S.PRICE =O.OTAMT_REVENUE_GL " +
            "LEFT OUTER JOIN CUSTOMER_ALL CAL ON CAL.CUSTOMER_ID = CA.CUSTOMER_ID  " +
            "LEFT OUTER JOIN EU_BILL.MALIYE_COUNTER_HISTORY MCH ON MCH.CUSTCODE = CAL.CUSTCODE  AND MCH.CUTOFF =OA.OHENTDATE " +
            "WHERE OTMERCH_GL <> 0 " +
            "UNION ALL " +
            "SELECT /*+ PARALLEL(10)*/ " +
            "OI.OTI_SEQNO OTSEQ ," +
            "OI.OTXACT ," +
            "OA.CUSTOMER_ID HESAP_NO, " +
            "OA.OHREFNUM FATURA_NO, " +
            "MCH.FATURA_ID MALIYE_FATURA_NO, " +
            "SYSADM.FN_IOT_INVOICE_SUBMISSION_TYPE (MCH.FATURA_ID , CAL.CUSTOMER_ID, CAL.CSTYPE, CAL.CUSTCODE) FATURA_GONDERIM_TIPI, " +
            "TO_CHAR(OHREFDATE,'YYYYMM') FATURA_DONEM, " +
            "SYSADM.FN_IOT_MSISDN(CAL.CUSTOMER_ID,CAL.CUSTCODE,CAL.PRGCODE) MSISDN, " +
            "OI.TAXAMT_GL TUTAR, " +
            "NULL BAYI_KODU, " +
            "NULL KAMPANYA_ID , " +
            "NULL IMEI, " +
            "OI.GLACODE SAP_GLCODE, " +
            "NULL DISTRIBUTOR_KODU, " +
            "TO_CHAR(SYSDATE,'DD.MM.YYYY') TARIH, " +
            "0 AKTARIM_DURUMU, " +
            "NULL SERVCAT_CODE, " +
            "NULL BANK " +
            "FROM ORDERTAX_ITEMS OI " +
            "JOIN ORDERHDR_ALL OA  ON OA.OHXACT =OI.OTXACT " +
            "LEFT OUTER JOIN CUSTOMER_ALL CAL ON CAL.CUSTOMER_ID = OA.CUSTOMER_ID  " +
            "LEFT OUTER JOIN EU_BILL.MALIYE_COUNTER_HISTORY MCH ON MCH.CUSTCODE = CAL.CUSTCODE AND MCH.CUTOFF =OA.OHENTDATE " +
            "WHERE TAXAMT_GL <> 0 ORDER BY OTSEQ, OTXACT, SAP_GLCODE ASC ";

}
