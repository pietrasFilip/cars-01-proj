package com.app.service.cars.sort;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.car.Color;
import com.app.service.cars.CarsService;
import com.app.service.config.AppTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.app.persistence.model.car.CarComparator.byMileage;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class SortByMileageTest {

    @Autowired
    private CarsService carsService;

    @Test
    @DisplayName("When there is not ascending order")
    void test1() {
        var carListExpected = List.of(
                Car.of(1L, "BMW", BigDecimal.valueOf(20), Color.WHITE, 200, null),
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), Color.BLUE, 2000, null),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), Color.BLUE, 2000, null)
        );

        var carsSorted = carsService.sortBy(byMileage);
        Assertions
                .assertThat(carsSorted)
                .isEqualTo(carListExpected);
    }
    @Test
    @DisplayName("When there is not descending order")
    void test2() {
        var carListExpected = List.of(
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), Color.BLUE, 2000, null),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), Color.BLUE, 2000, null),
                Car.of(1L, "BMW", BigDecimal.valueOf(20), Color.WHITE, 200, null)
        );

        var carsSorted = carsService.sortBy(byMileage.reversed());
        Assertions
                .assertThat(carsSorted)
                .isEqualTo(carListExpected);
    }

    @Test
    @DisplayName("When there is not enough elements")
    void test3() {
        Assertions
                .assertThat(carsService.sortBy(byMileage))
                .hasSize(3);
    }
}
