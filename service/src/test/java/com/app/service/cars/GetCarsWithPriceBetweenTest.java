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
public class GetCarsWithPriceBetweenTest {

    @Autowired
    private CarsService carsService;

    public static Stream<Arguments> argSource() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(500), BigDecimal.valueOf(300)),
                Arguments.of(BigDecimal.valueOf(20), BigDecimal.valueOf(10))
        );
    }

    public static Stream<Arguments> argSourceNoCarsBetween() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(3100), BigDecimal.valueOf(3200)),
                Arguments.of(BigDecimal.valueOf(10), BigDecimal.valueOf(19))
        );
    }

    @ParameterizedTest
    @MethodSource("argSource")
    @DisplayName("When price range is not correct")
    void test1(BigDecimal from, BigDecimal to) {
        assertThatThrownBy(() -> carsService.getCarsWithPriceBetween(from, to))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Wrong price range");
    }

    @ParameterizedTest
    @MethodSource("argSourceNoCarsBetween")
    @DisplayName("When price is out of range")
    void test2(BigDecimal from, BigDecimal to) {
        assertThatThrownBy(() -> carsService.getCarsWithPriceBetween(from, to))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("No cars with price between");
    }

    @Test
    @DisplayName("When cars are not sorted correctly")
    void test3() {
        var carListExpected = List.of(
                Car.of(1L, "BMW", BigDecimal.valueOf(20), Color.WHITE, 200, null),
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), Color.BLUE, 2000, null),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), Color.BLUE, 2000, null)
        );
        final var FROM = BigDecimal.valueOf(20);
        final var TO = BigDecimal.valueOf(2900);
        var carListSorted = carsService.getCarsWithPriceBetween(FROM, TO);
        assertThat(carListSorted)
                .isNotNull()
                .isEqualTo(carListExpected);
    }
}
