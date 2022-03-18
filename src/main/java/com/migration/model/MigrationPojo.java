package com.migration.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class MigrationPojo {

    private Long hesapNo;
    private String faturaNo;
    private Long faturaDonemi;
    private String MSISDN;
    private BigDecimal tutar;
    private BigDecimal KDVOran;
    private BigDecimal OIVOran;
    private String bayiKodu;
    private Long kampanyaId;
    private String IMEI;
    private Long bankaKodu;
    private String tarih;
    private Integer aktarimDurumu;
    private String bank;
    private String taxCode;

}
