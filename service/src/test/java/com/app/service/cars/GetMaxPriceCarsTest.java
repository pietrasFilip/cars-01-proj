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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import static com.app.persistence.model.car.Color.BLUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class GetMaxPriceCarsTest {
    @Autowired
    private CarsService carsService;

    @TestFactory
    @DisplayName("When cars are not sorted descending")
    Stream<DynamicNode> test1() {
        var expectedCars = new LinkedHashMap<String, List<Car>>();
        expectedCars.put("KIA", List.of(Car.of(3L, "KIA", BigDecimal.valueOf(2000),
                BLUE, 2000, null)));
        expectedCars.put("BMW" ,List.of(Car.of(2L, "BMW", BigDecimal.valueOf(2000),
                Color.BLUE, 2000, null)));

        return Stream.of(carsService.getMaxPriceCars())
                .map(n -> dynamicContainer("Container", Stream.of(
                        dynamicTest("Not empty",
                                () -> assertThat(n).isNotEmpty()),
                        dynamicTest("Equals to expected",
                                () -> assertThat(n).containsExactlyEntriesOf(expectedCars))
                )));
    }
}
