package com.app.persistence.data.reader.loader.car.db;

import com.app.persistence.data.reader.loader.Fetch;
import com.app.persistence.data.reader.loader.repository.db.CarRepository;
import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.data.reader.model.db.CarDataDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
public class CarDataDbLoaderImpl implements CarDataDbLoader{
    private final CarRepository carRepository;

    @Override
    public List<CarData> load(Fetch fetch) {
        return switch (fetch) {
            case EAGER -> fetchEager();
            case LAZY -> fetchLazy();
        };
    }

    private List<CarData> fetchEager() {
        return carRepository
                .findAllCarsWithComponents()
                .stream()
                .map(CarDataDb::toCarDataWithComponents)
                .collect(toMap(CarData::getId, val -> val, (c1, c2) -> {
                    c1.getComponents().addAll(c2.getComponents());
                    return c1;
                }))
                .values()
                .stream()
                .toList();
    }

    private List<CarData> fetchLazy() {
        return carRepository
                .findAll()
                .stream()
                .map(CarDataDb::toCarDataWithoutComponents)
                .toList();
    }
}
