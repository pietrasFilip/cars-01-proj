package com.app.persistence.data.reader.loader.car.txt;

import com.app.persistence.data.reader.loader.Fetch;
import com.app.persistence.data.reader.loader.exception.TxtLoaderException;
import com.app.persistence.data.reader.loader.repository.component.txt.TxtComponentRepositoryImpl;
import com.app.persistence.data.reader.model.CarData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
@RequiredArgsConstructor
public class CarDataTxtLoaderImpl implements CarDataTtxLoader{
    private static final String CARS_PATH =
            new File("data/cars.txt").getAbsoluteFile().getPath();
    private static final String COMPONENTS_PATH =
            new File("data/components.txt").getAbsoluteFile().getPath();
    @Override
    public List<CarData> load(Fetch fetch) {
        return switch (fetch) {
            case LAZY -> fetchLazy();
            case EAGER -> fetchEager();
        };
    }

    private List<CarData> fetchEager() {
        var componentRepository = new TxtComponentRepositoryImpl(COMPONENTS_PATH);

        try (var lines = Files.lines(Paths.get(CARS_PATH))){
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        var components = componentRepository.findAllById(
                                Arrays
                                        .stream(items[5].split(","))
                                        .map(Long::valueOf)
                                        .toList()
                        );
                        return CarData.of(
                                Long.valueOf(items[0]), items[1], new BigDecimal(items[2]), items[3],
                                parseInt(items[4]), components);
                    }).toList();
        } catch (Exception e) {
            throw new TxtLoaderException(e.getMessage());
        }
    }

    private List<CarData> fetchLazy() {
        try (var lines = Files.lines(Paths.get(CARS_PATH))){
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        return CarData.of(
                                Long.valueOf(items[0]), items[1], new BigDecimal(items[2]), items[3],
                                parseInt(items[4]), null);
                    }).toList();
        } catch (Exception e) {
            throw new TxtLoaderException(e.getMessage());
        }
    }
}
