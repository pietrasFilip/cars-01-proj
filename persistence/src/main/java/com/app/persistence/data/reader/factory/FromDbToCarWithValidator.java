package com.app.persistence.data.reader.factory;

import com.app.persistence.data.reader.converter.Converter;
import com.app.persistence.data.reader.converter.ToCarConverterImpl;
import com.app.persistence.data.reader.loader.DataLoader;
import com.app.persistence.data.reader.loader.car.db.CarDataDbLoaderImpl;
import com.app.persistence.data.reader.loader.repository.db.CarRepository;
import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.data.reader.validator.CarDataValidator;
import com.app.persistence.data.reader.validator.DataValidator;
import com.app.persistence.model.car.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FromDbToCarWithValidator implements DataFactory<List<CarData>, List<Car>>{
    private final CarRepository carRepository;
    private final CarDataValidator carDataValidator;
    @Override
    public DataLoader<List<CarData>> createDataLoader() {
        return new CarDataDbLoaderImpl(carRepository);
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
