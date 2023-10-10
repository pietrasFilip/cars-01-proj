package com.app.persistence.data.reader.loader.repository.db.impl;

import com.app.persistence.data.reader.loader.repository.db.CarRepository;
import com.app.persistence.data.reader.loader.repository.db.generic.AbstractCrudRepository;
import com.app.persistence.data.reader.loader.repository.db.impl.exception.CarRepositoryException;
import com.app.persistence.data.reader.model.db.CarDataDb;
import com.app.persistence.data.reader.model.db.ComponentDataDb;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepositoryImpl extends AbstractCrudRepository<CarDataDb, Long> implements CarRepository {
    public CarRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<CarDataDb> findAllCarsWithComponents() {
        var sql = """
                SELECT cars.id as car_id, cars.model, cars.price, cars.color, cars.mileage,
                components_data.id as component_id, components_data.name component_name
                FROM cars
                JOIN cars_data ON cars.id = cars_data.car_id
                JOIN components_data ON components_data.id = cars_data.component_id
                """;

        try {
            return jdbi.withHandle(handle -> handle
                    .createQuery(sql)
                    .map((rs, ctx) -> CarDataDb
                            .builder()
                            .id(rs.getLong("car_id"))
                            .model(rs.getString("model"))
                            .price(rs.getBigDecimal("price"))
                            .color(rs.getString("color"))
                            .mileage(rs.getInt("mileage"))
                            .component(ComponentDataDb
                                    .builder()
                                    .id(rs.getLong("component_id"))
                                    .name(rs.getString("component_name"))
                                    .build())
                            .build())
                    .list());
        } catch (Exception e) {
            throw new CarRepositoryException(e.getMessage());
        }
    }

    @Override
    public List<CarDataDb> findAllCarsWithoutComponents() {
        try {
            return jdbi.withHandle(handle -> handle
                    .createQuery("SELECT * from cars")
                    .mapToBean(CarDataDb.class)
                    .list());
        } catch (Exception e) {
            throw new CarRepositoryException(e.getMessage());
        }
    }
}
