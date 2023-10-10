package com.app.persistence.data.reader.converter;

import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.model.car.Car;

import java.util.List;

public class ToCarConverterImpl implements ToCarConverter {
    @Override
    public List<Car> convert(List<CarData> data) {
        return data
                .stream()
                .map(CarData::toCar)
                .toList();
    }
}
