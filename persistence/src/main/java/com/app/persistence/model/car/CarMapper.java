package com.app.persistence.model.car;

import com.app.persistence.model.component.Component;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

public interface CarMapper {
    Function<Car, Color> convertToColor = car -> car.color;
    Function<Car, String> convertToModel = car -> car.model;
    Function<Car, BigDecimal> convertToPrice = car -> car.price;
    ToDoubleFunction<Car> convertToDblPrice = car -> car.price.doubleValue();
    Function<Car, Integer> convertToMileage = car -> car.mileage;
    Function<Car, Stream<Component>> convertToComponentsAsStream = car -> car.components.stream();
}
