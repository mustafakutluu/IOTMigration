package com.iot.migration.dao;

import com.iot.migration.model.Bank;
import com.iot.migration.model.IOTPojo;
import com.iot.migration.model.TaxCategory;

import java.util.List;

public interface IOTDao{

    List<Bank> getBankData();

    List<TaxCategory> getTaxCategoryData();

    List<IOTPojo> getMigrationData(int start, int finish);

    IOTPojo insert(IOTPojo param);

    List<IOTPojo> batchInsert(List<IOTPojo> params);
}
