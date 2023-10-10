package com.app.persistence.data.reader.loader.car.db;

import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.data.reader.model.ComponentData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.app.persistence.data.reader.loader.Fetch.EAGER;
import static com.app.persistence.data.reader.loader.Fetch.LAZY;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarDataDbLoaderImplTest {
    @Mock
    CarDataDbLoaderImpl carDataDbLoader;

    @TestFactory
    @DisplayName("When there is data to load from db and Fetch is Lazy")
    Stream<DynamicNode> test1() {
        when(carDataDbLoader.load(LAZY))
                .thenAnswer(invocationOnMock -> List.of(
                        CarData.of(1L, "BMW", BigDecimal.valueOf(20), "WHITE", 200,null),
                        CarData.of(2L, "BMW", BigDecimal.valueOf(2000), "BLUE", 2000,null),
                        CarData.of(3L, "KIA", BigDecimal.valueOf(2000), "BLUE", 2000,null)
                ));

        return Stream.of(carDataDbLoader.load(LAZY))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Components are null",
                                        () -> n.forEach(car -> Assertions.assertThat(car.getComponents()).isNull())),
                                dynamicTest("Is instance of List",
                                        () -> Assertions.assertThat(n).isInstanceOf(List.class)),
                                dynamicTest("Is instance of CarData",
                                        () -> n.forEach(car -> Assertions.assertThat(car).isInstanceOf(CarData.class))),
                                dynamicTest("Has Exactly size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }

    @TestFactory
    @DisplayName("When there is data to load from db and Fetch is Eager")
    Stream<DynamicNode> test2() {
        when(carDataDbLoader.load(EAGER))
                .thenAnswer(invocationOnMock -> List.of(
                        CarData.of(1L, "BMW", BigDecimal.valueOf(20), "WHITE", 200, Set.of(
                                ComponentData.of(2L, "B"), ComponentData.of(1L, "A")
                        )),
                        CarData.of(2L, "BMW", BigDecimal.valueOf(2000), "BLUE", 2000, Set.of(
                                ComponentData.of(1L, "A"), ComponentData.of(3L, "C")
                        )),
                        CarData.of(3L, "KIA", BigDecimal.valueOf(2000), "BLUE", 2000,Set.of(
                                ComponentData.of(2L, "B")
                        ))
                ));

        return Stream.of(carDataDbLoader.load(EAGER))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Components are not null",
                                        () -> n.forEach(car -> Assertions.assertThat(car.getComponents()).isNotNull())),
                                dynamicTest("Is instance of List",
                                        () -> Assertions.assertThat(n).isInstanceOf(List.class)),
                                dynamicTest("Is instance of CarData",
                                        () -> n.forEach(car -> Assertions.assertThat(car).isInstanceOf(CarData.class))),
                                dynamicTest("Has Exactly size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }
}
