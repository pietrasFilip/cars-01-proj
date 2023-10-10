package com.app.persistence.data.reader.processor;

import com.app.persistence.config.AppTestConfig;
import com.app.persistence.model.car.Car;
import com.app.persistence.model.component.Component;
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
import java.util.Set;

import static com.app.persistence.data.reader.loader.Fetch.EAGER;
import static com.app.persistence.data.reader.loader.Fetch.LAZY;
import static com.app.persistence.model.car.Color.BLUE;
import static com.app.persistence.model.car.Color.WHITE;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:processor-test.properties")
class CarDataProcessorDbTest {
    @Autowired
    CarDataProcessor carDataProcessorDb;

    @Test
    @DisplayName("When data source is db and fetch is eager")
    void test1() {
        var expected = List.of(
                Car.of(
                        1L, "BMW", BigDecimal.valueOf(20), WHITE, 200,
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))
                ),
                Car.of(
                        2L, "BMW", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C"))
                ),
                Car.of(
                        3L, "KIA", BigDecimal.valueOf(2000), BLUE, 2000,
                        Set.of(Component.of(2L, "B"))
                )
        );

        Assertions
                .assertThat(carDataProcessorDb.process(EAGER))
                .hasSize(3)
                .isInstanceOf(List.class)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When data source is db and fetch is lazy")
    void test2() {
        var expected = List.of(
                Car.of(1L, "BMW", BigDecimal.valueOf(20), WHITE, 200, null),
                Car.of(2L, "BMW", BigDecimal.valueOf(2000), BLUE, 2000, null),
                Car.of(3L, "KIA", BigDecimal.valueOf(2000), BLUE, 2000, null)
        );

        Assertions
                .assertThat(carDataProcessorDb.process(LAZY))
                .hasSize(3)
                .isInstanceOf(List.class)
                .isEqualTo(expected);
    }
}
