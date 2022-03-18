package com.migration.dao;

import com.migration.model.MigrationPojo;

import java.util.List;

public interface FirstDao {

    List<MigrationPojo> getMigrationData(Long hesapNo);

    MigrationPojo insert(MigrationPojo param);

    List<MigrationPojo> batchInsert(List<MigrationPojo> params);
}
