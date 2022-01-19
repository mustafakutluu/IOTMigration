package com.iot.migration.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class IOTPojo {

    private Long hesapNo;
    private String faturaNo;
    private String maliyeFaturaNo;
    private String faturaGonderimTipi;
    private Long faturaDonemi;
    private String MSISDN;
    private BigDecimal tutar;
    private BigDecimal KDVOran;
    private BigDecimal OIVOran;
    private String bayiKodu;
    private Long kampanyaId;
    private String IMEI;
    private String sapGlCode;
    private String distributorKodu;
    private Long bankaKodu;
    private String tarih;
    private Integer aktarimDurumu;

}
