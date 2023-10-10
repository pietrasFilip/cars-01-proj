package com.app.persistence.data.reader.loader.car.json;

import com.app.persistence.data.reader.model.CarData;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static com.app.persistence.data.reader.loader.Fetch.EAGER;
import static com.app.persistence.data.reader.loader.Fetch.LAZY;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class CarDataJsonLoaderImplTest {

    @TestFactory
    @DisplayName("When there is data to load from .json file and Fetch is Lazy")
    Stream<DynamicNode> test1() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var jsonLoader = new CarDataJsonLoaderImpl(gson);
        return Stream.of(jsonLoader.load(LAZY))
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
    @DisplayName("When there is data to load from .json file and Fetch is Eager")
    Stream<DynamicNode> test2() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var jsonLoader = new CarDataJsonLoaderImpl(gson);
        return Stream.of(jsonLoader.load(EAGER))
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
