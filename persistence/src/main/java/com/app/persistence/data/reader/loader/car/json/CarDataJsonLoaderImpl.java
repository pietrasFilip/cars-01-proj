package com.app.persistence.data.reader.loader.car.json;

import com.app.persistence.data.reader.loader.Fetch;
import com.app.persistence.data.reader.loader.exception.JsonLoaderException;
import com.app.persistence.data.reader.loader.repository.component.json.JsonComponentRepositoryImpl;
import com.app.persistence.data.reader.model.CarData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.gson.JsonParser.parseReader;

@Component
public class CarDataJsonLoaderImpl extends FromJsonToObjectLoader<List<CarData>> implements CarDataJsonLoader {
    public CarDataJsonLoaderImpl(Gson gson) {
        super(gson);
    }
    private static final String CARS_PATH =
            new File("data/cars.json").getAbsoluteFile().getPath();
    private static final String COMPONENTS_PATH =
            new File("data/components.json").getAbsoluteFile().getPath();

    @Override
    public List<CarData> load(Fetch fetch) {
        return switch (fetch) {
            case EAGER -> fetchEager();
            case LAZY -> fetchLazy();
        };
    }

    private List<CarData> fetchEager() {
        var componentRepository = new JsonComponentRepositoryImpl(gson, COMPONENTS_PATH);

        try (var fileReader = new FileReader(CARS_PATH)){
            var carsObject = parseReader(fileReader).getAsJsonArray();
            var carsList = new ArrayList<JsonObject>();
            carsObject.forEach(c -> carsList.add(c.getAsJsonObject()));
            return carsList
                    .stream()
                    .map(car -> {
                        var components = componentRepository.findAllById(
                                Arrays
                                        .stream(car.getAsJsonArray("components").toString().split(","))
                                        .map(num -> num.replace("[", "").replace("]", ""))
                                        .map(Long::valueOf)
                                        .toList()
                        );
                        return CarData.of(
                                car.get("id").getAsLong(),
                                car.get("model").getAsString(),
                                car.get("price").getAsBigDecimal(),
                                car.get("color").getAsString(),
                                car.get("mileage").getAsInt(),
                                components
                        );
                    }).toList();
        } catch (Exception e) {
            throw new JsonLoaderException(e.getMessage());
        }
    }

    private List<CarData> fetchLazy() {
        try (var fileReader = new FileReader(CARS_PATH)){
            var carsObject = parseReader(fileReader).getAsJsonArray();
            var carsList = new ArrayList<JsonObject>();
            carsObject.forEach(c -> carsList.add(c.getAsJsonObject()));
            return carsList
                    .stream()
                    .map(car -> CarData.of(
                            car.get("id").getAsLong(),
                            car.get("model").getAsString(),
                            car.get("price").getAsBigDecimal(),
                            car.get("color").getAsString(),
                            car.get("mileage").getAsInt(),
                            null
                    ))
                    .toList();
        } catch (Exception e) {
            throw new JsonLoaderException(e.getMessage());
        }
    }
}
