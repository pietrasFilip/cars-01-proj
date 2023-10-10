package com.app.persistence.data.reader.factory;

import com.app.persistence.data.reader.converter.Converter;
import com.app.persistence.data.reader.converter.ToCarConverterImpl;
import com.app.persistence.data.reader.loader.DataLoader;
import com.app.persistence.data.reader.loader.car.json.CarDataJsonLoaderImpl;
import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.data.reader.validator.CarDataValidator;
import com.app.persistence.data.reader.validator.DataValidator;
import com.app.persistence.model.car.Car;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FromJsonToCarWithValidator implements DataFactory<List<CarData>, List<Car>> {
    private final Gson gson;
    private final CarDataValidator carDataValidator;
    @Override
    public DataLoader<List<CarData>> createDataLoader() {
        return new CarDataJsonLoaderImpl(gson);
    }

    @Override
    public DataValidator<List<CarData>> createValidator() {
        return carDataValidator;
    }

    @Override
    public Converter<List<CarData>, List<Car>> createConverter() {
        return new ToCarConverterImpl();
    }
}
