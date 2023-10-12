package com.app.service.cars;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.car.Color;
import com.app.persistence.model.component.Component;
import com.app.service.cars_statistics.BigDecimalStatistics;
import com.app.service.cars_statistics.NumbersStatisticsType;
import com.app.service.sort.SortService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public sealed interface CarsService extends SortService<Car> permits CarsServiceImpl{
    List<Car> getCarsWithMileageGreaterThan(int referenceMileage);
    Map<Color, Integer> getNumberOfCarsWithColors();
    Map<String, List<Car>> getMaxPriceCars();
    BigDecimalStatistics getCarNumberStatistics(NumbersStatisticsType numbersStatisticsType);
    List<Car> getMostExpensiveCar();
    List<Car> sortComponentsAlphabetically(Comparator<Component> componentsComparator);
    Map<Component, List<Car>> getNumberOfCarsWithComponent();
    List<Car> getCarsWithPriceBetween(BigDecimal from, BigDecimal to);
}
