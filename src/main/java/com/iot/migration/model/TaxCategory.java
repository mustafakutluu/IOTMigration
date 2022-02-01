package com.iot.migration.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TaxCategory {

    private String servCatCode;

    private String taxCatName;

    private long taxCatId;

    private BigDecimal taxRate;

}
