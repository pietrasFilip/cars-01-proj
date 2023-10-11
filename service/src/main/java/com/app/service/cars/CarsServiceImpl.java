package com.app.service.cars;

import com.app.persistence.model.car.Color;
import com.app.persistence.model.car.Car;
import com.app.persistence.model.component.Component;
import com.app.service.cars.exception.CarsServiceException;
import com.app.service.cars.provider.CarsProvider;
import com.app.service.cars_statistics.*;
import com.app.service.cars_statistics.collector.BigDecimalSummaryStatistics;
import com.app.service.sort.AbstractSortServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.persistence.data.reader.loader.Fetch.*;
import static com.app.persistence.model.car.CarComparator.*;
import static com.app.persistence.model.car.CarMapper.*;
import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
public final class CarsServiceImpl extends AbstractSortServiceImpl<Car> implements CarsService {

    public CarsServiceImpl(CarsProvider dataProcessor) {
        super(dataProcessor);
    }

    /**
     *
     * @param referenceMileage Min mileage from which cars will be selected.
     * @return                 List of cars with mileage greater than reference mileage.
     */
    @Override
    public List<Car> getCarsWithMileageGreaterThan(int referenceMileage) {
        if (referenceMileage < 0) {
            throw new CarsServiceException("Reference mileage is less than zero");
        }

        var cars = dataProcessor.process(LAZY);

        return cars
                .stream()
                .filter(car -> car.hasMileageGreaterThan(referenceMileage))
                .toList();
    }

    /**
     *
     * @return Map of color and number of cars that has specified color.
     */
    @Override
    public Map<Color, Integer> getNumberOfCarsWithColors() {
        return carsWithColors()
                .entrySet()
                .stream()
                .sorted(reverseOrder(comparingByValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (c1, c2) -> c1,
                        LinkedHashMap::new
                ));
    }

    private Map<Color, Integer> carsWithColors() {
        var cars = dataProcessor.process(LAZY);
        return cars
                .stream()
                .collect(groupingBy(convertToColor))
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, value -> value.getValue().size()));
    }

    /**
     *
     * @return Map of max priced car model name and list of these cars.
     */
    @Override
    public Map<String, List<Car>> getMaxPriceCars() {
        return maxPricedCarModels()
                .entrySet()
                .stream()
                .sorted(reverseOrder(comparingByKey()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (c1, c2) -> c1,
                        LinkedHashMap::new
                ));
    }

    private Map<String, List<Car>> maxPricedCarModels() {
        var cars = dataProcessor.process(LAZY);
        return cars
                .stream()
                .collect(groupingBy(convertToModel,
                        Collectors.collectingAndThen(
                                groupingBy(convertToPrice),
                                groupedByPrice -> groupedByPrice
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByKey())
                                        .map(Map.Entry::getValue)
                                        .orElseThrow()
                        )));
    }

    /**
     *
     * @param numbersStatisticsType Type of statistic to return.
     * @return                      Min, average, max, sum and count of NumberStatisticsType.
     */
    @Override
    public BigDecimalStatistics getCarNumberStatistics(NumbersStatisticsType numbersStatisticsType) {
        var cars = dataProcessor.process(LAZY);
        return switch (numbersStatisticsType) {
            case PRICE -> cars
                    .stream()
                    .map(convertToPrice)
                    .collect(new BigDecimalSummaryStatistics());
            case MILEAGE -> cars
                    .stream()
                    .map(convertToMileage)
                    .map(BigDecimal::new)
                    .collect(new BigDecimalSummaryStatistics());
        };
    }

    /**
     *
     * @return List of most expensive cars.
     */
    @Override
    public List<Car> getMostExpensiveCar() {
        var cars = dataProcessor.process(LAZY);
        return cars
                .stream()
                .collect(groupingBy(convertToPrice))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    /**
     *
     * @param componentsComparator Car components comparator.
     * @return                     List of cars with alphabetically sorted components.
     */
    @Override
    public List<Car> sortComponentsAlphabetically(Comparator<Component> componentsComparator) {
        var cars = dataProcessor.process(EAGER);
        return cars
                .stream()
                .map(car -> car.withSortedComponents(componentsComparator))
                .toList();
    }

    /**
     *
     * @return Map of components and cars containing these components.
     */
    @Override
    public Map<Component, List<Car>> getNumberOfCarsWithComponent() {
        var cars = dataProcessor.process(EAGER);
        return cars
                .stream()
                .flatMap(convertToComponentsAsStream)
                .distinct()
                .collect(toMap(Function.identity(), this::hasComponent))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x1, x2) -> x1, LinkedHashMap::new));
    }

    private List<Car> hasComponent(Component component) {
        var cars = dataProcessor.process(EAGER);
        return cars
                .stream()
                .filter(car -> car.hasComponent(component))
                .toList();
    }

    /**
     *
     * @param from Min cars price.
     * @param to   Max cars price.
     * @return     List of cars with prices between from and to.
     */
    @Override
    public List<Car> getCarsWithPriceBetween(BigDecimal from, BigDecimal to) {
        if (from.compareTo(to) > 0) {
            throw new CarsServiceException("Wrong price range");
        }

        var cars = dataProcessor.process(LAZY);

        var carsWithPrice = cars
                .stream()
                .filter(car -> car.hasPriceBetween(from, to))
                .sorted(byModel)
                .toList();

        if (carsWithPrice.isEmpty()) {
            throw new CarsServiceException("No cars with price between");
        }

        return carsWithPrice;
    }

}
