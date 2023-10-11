package com.app.service.cars;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.car.Color;
import com.app.service.config.AppTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class GetMostExpensiveCarTest {
    @Autowired
    private CarsService carsService;

    @TestFactory
    @DisplayName("When there is more than one most expensive car")
    Stream<DynamicNode> test1() {
        var expected = List.of(
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), Color.BLUE, 2000, null),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), Color.BLUE, 2000, null)
        );

        return Stream.of(carsService.getMostExpensiveCar())
                .map(n -> dynamicContainer("Container", Stream.of(
                        dynamicTest("Not empty",
                                () -> assertThat(n).isNotEmpty()),
                        dynamicTest("Is equal to expected",
                                () -> assertThat(n).isEqualTo(expected))
                )));
    }
}
