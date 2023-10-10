package com.app.persistence.data.reader.loader.repository.db;


import com.app.persistence.data.reader.loader.repository.db.generic.CrudRepository;
import com.app.persistence.data.reader.model.db.CarDataDb;

import java.util.List;
public interface CarRepository extends CrudRepository<CarDataDb, Long> {
    List<CarDataDb> findAllCarsWithComponents();
    List<CarDataDb> findAllCarsWithoutComponents();
}
