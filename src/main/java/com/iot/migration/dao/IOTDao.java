package com.iot.migration.dao;

import com.iot.migration.model.IOTPojo;
import java.util.List;

public interface IOTDao{

    List<IOTPojo> getMigrationData(Long hesapNo);

    IOTPojo insert(IOTPojo param);

    List<IOTPojo> batchInsert(List<IOTPojo> params);
}
