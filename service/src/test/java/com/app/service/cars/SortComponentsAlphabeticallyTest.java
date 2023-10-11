package com.app.service.cars;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.component.Component;
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
import java.util.Set;
import java.util.stream.Stream;

import static com.app.persistence.model.car.Color.BLUE;
import static com.app.persistence.model.car.Color.WHITE;
import static com.app.persistence.model.component.ComponentComparator.byComponentName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class SortComponentsAlphabeticallyTest {
    @Autowired
    private CarsService carsService;

    @TestFactory
    @DisplayName("When components are not sorted alphabetically")
    Stream<DynamicNode> test1() {
        var expected = List.of(
                Car.of(1L, "BMW", BigDecimal.valueOf(20), WHITE, 200,
                        Set.of(Component.of(1L, "A"), Component.of(2L, "B"))),
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C"))),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(2L, "B")))
        );

        return Stream.of(carsService.sortComponentsAlphabetically(byComponentName))
                .map(n -> dynamicContainer("Container", Stream.of(
                        dynamicTest("Not empty",
                                () -> assertThat(n).isNotEmpty()),
                        dynamicTest("Is equal to expected",
                                () -> assertThat(n).isEqualTo(expected))
                )));
    }
}
