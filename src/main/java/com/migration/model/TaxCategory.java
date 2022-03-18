package com.migration.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TaxCategory {

    private String taxCode;

    private String taxName;

    private long taxId;

    private BigDecimal taxRate;

}
