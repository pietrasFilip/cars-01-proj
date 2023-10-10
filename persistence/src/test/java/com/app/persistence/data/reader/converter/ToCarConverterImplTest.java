package com.app.persistence.data.reader.converter;

import com.app.persistence.data.reader.loader.car.txt.CarDataTxtLoaderImpl;
import com.app.persistence.model.car.Car;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static com.app.persistence.data.reader.loader.Fetch.EAGER;
import static com.app.persistence.data.reader.loader.Fetch.LAZY;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ToCarConverterImplTest {

    @TestFactory
    @DisplayName("When there is conversion from CarData to Car and Fetch is Lazy")
    Stream<DynamicNode> test1() {
        var txtLoader = new CarDataTxtLoaderImpl();
        var data = txtLoader.load(LAZY);

        var converter = new ToCarConverterImpl();
        return Stream.of(converter.convert(data))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of Car",
                                        () -> n.forEach(o -> Assertions.assertThat(o).isInstanceOf(Car.class))),
                                dynamicTest("Has size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }

    @TestFactory
    @DisplayName("When there is conversion from CarData to Car and Fetch is Eager")
    Stream<DynamicNode> test2() {
        var txtLoader = new CarDataTxtLoaderImpl();
        var data = txtLoader.load(EAGER);

        var converter = new ToCarConverterImpl();
        return Stream.of(converter.convert(data))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of Car",
                                        () -> n.forEach(o -> Assertions.assertThat(o).isInstanceOf(Car.class))),
                                dynamicTest("Has size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }
}
