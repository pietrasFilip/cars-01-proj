package com.app.service.cars;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.car.Color;
import com.app.service.cars.exception.CarsServiceException;
import com.app.service.config.AppTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
public class GetCarsWithMileageGreaterThanTest {
    @Autowired
    private CarsService carsService;

    public static Stream<Arguments> outOfRangeArgSource() {
        return Stream.of(
                Arguments.of(2100), Arguments.of(2050), Arguments.of(3000),
                Arguments.of(2001)
        );
    }

    public static Stream<Arguments> lessThanZeroArgSource() {
        return Stream.of(
                Arguments.of(-1), Arguments.of(-200), Arguments.of(-15),
                Arguments.of(-49)
        );
    }
    @Test
    @DisplayName("When is filtered incorrectly")
    void test1() {
        var referenceMileage = 1000;
        var expectedCarList = List.of(
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), Color.BLUE, 2000, null),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), Color.BLUE, 2000, null)
        );

        assertThat(carsService.getCarsWithMileageGreaterThan(referenceMileage))
                .isEqualTo(expectedCarList);
    }

    @ParameterizedTest
    @MethodSource("outOfRangeArgSource")
    @DisplayName("When reference mileage is out of range")
    void test2(int referenceMileage) {
        assertThat(carsService.getCarsWithMileageGreaterThan(referenceMileage))
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("lessThanZeroArgSource")
    @DisplayName("When mileage is less than zero")
    void test3(int referenceMileage) {
        assertThatThrownBy(() -> carsService.getCarsWithMileageGreaterThan(referenceMileage))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Reference mileage is less than zero");
    }
}
