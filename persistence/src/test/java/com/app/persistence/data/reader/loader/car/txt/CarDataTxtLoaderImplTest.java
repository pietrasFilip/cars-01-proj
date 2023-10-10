package com.app.persistence.data.reader.loader.car.txt;

import com.app.persistence.data.reader.model.CarData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static com.app.persistence.data.reader.loader.Fetch.LAZY;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class CarDataTxtLoaderImplTest {

    @TestFactory
    @DisplayName("When there is data to load from .txt file and Fetch is Lazy")
    Stream<DynamicNode> test1() {
        var txtLoader = new CarDataTxtLoaderImpl();
        return Stream.of(txtLoader.load(LAZY))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of List",
                                        () -> Assertions.assertThat(n).isInstanceOf(List.class)),
                                dynamicTest("Is instance of CarData",
                                        () -> n.forEach(o -> Assertions.assertThat(o).isInstanceOf(CarData.class))),
                                dynamicTest("Has exactly size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }

    @TestFactory
    @DisplayName("When there is data to load from .txt file and Fetch is Eager")
    Stream<DynamicNode> test2() {
        var txtLoader = new CarDataTxtLoaderImpl();
        return Stream.of(txtLoader.load(LAZY))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of List",
                                        () -> Assertions.assertThat(n).isInstanceOf(List.class)),
                                dynamicTest("Is instance of CarData",
                                        () -> n.forEach(o -> Assertions.assertThat(o).isInstanceOf(CarData.class))),
                                dynamicTest("Has exactly size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }
}
