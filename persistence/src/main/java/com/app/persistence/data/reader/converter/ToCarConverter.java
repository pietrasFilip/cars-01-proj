package com.app.persistence.data.reader.converter;

import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.model.car.Car;

import java.util.List;

public interface ToCarConverter extends Converter<List<CarData>, List<Car>> {
}
