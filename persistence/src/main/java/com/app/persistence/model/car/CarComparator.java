package com.app.persistence.model.car;

import java.util.Comparator;

public interface CarComparator {
    Comparator<Car> byModel = Comparator.comparing(car -> car.model);
    Comparator<Car> byPrice = Comparator.comparing(car -> car.price);
    Comparator<Car> byColor = Comparator.comparing(car -> car.color);
    Comparator<Car> byMileage = Comparator.comparing(car -> car.mileage);
}
