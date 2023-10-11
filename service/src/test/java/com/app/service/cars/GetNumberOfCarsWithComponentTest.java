package com.app.service.cars;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.component.Component;
import com.app.service.config.AppTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.app.persistence.model.car.Color.BLUE;
import static com.app.persistence.model.car.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class GetNumberOfCarsWithComponentTest {
    @Autowired
    private CarsService carsService;

    @Test
    @DisplayName("When is not sorted descending by number of cars with specified component")
    void test1() {
        var carsWithComponent = carsService.getNumberOfCarsWithComponent();
        var expected = new LinkedHashMap<Component, List<Car>>();
        expected.put(Component.of(1L, "A"), List.of(
                Car.of(1L, "BMW", BigDecimal.valueOf(20), WHITE, 200,
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))),
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C")))
        ));
        expected.put(Component.of(2L, "B"), List.of(
                Car.of(1L, "BMW", BigDecimal.valueOf(20), WHITE, 200,
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(2L, "B")))
        ));
        expected.put(Component.of(3L, "C"), List.of(
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C")))
        ));

        assertThat(carsWithComponent)
                .isNotEmpty()
                .containsExactlyEntriesOf(expected);
    }
}
