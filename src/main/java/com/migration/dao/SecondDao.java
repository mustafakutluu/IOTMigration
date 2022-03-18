package com.migration.dao;

import com.migration.model.Bank;
import com.migration.model.MigrationPojo;
import com.migration.model.TaxCategory;

import java.util.List;

public interface SecondDao {

    List<Bank> getBankData();

    List<TaxCategory> getTaxCategoryData();

    List<MigrationPojo> getMigrationData(int start, int finish);

    MigrationPojo insert(MigrationPojo param);

    List<MigrationPojo> batchInsert(List<MigrationPojo> params);
}
