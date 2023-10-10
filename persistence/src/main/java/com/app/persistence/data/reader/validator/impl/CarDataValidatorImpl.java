package com.app.persistence.data.reader.validator.impl;

import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.data.reader.validator.CarDataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.app.persistence.data.reader.validator.DataValidator.*;

@Component
@RequiredArgsConstructor
public class CarDataValidatorImpl implements CarDataValidator {
    @Value("${validator.regex.model.name}")
    private String modelNameRegex;
    @Value("${validator.regex.items.name}")
    private String itemsNameRegex;
    @Override
    public List<CarData> validate(List<CarData> carData) {
        return carData
                .stream()
                .map(this::validateSingleCar)
                .toList();
    }

    @Override
    public CarData validateSingleCar(CarData carData) {
        return CarData.of(
                carData.getId(),
                validateMatchesRegex(modelNameRegex, carData.getModel()),
                validatePositiveDecimal(carData.getPrice()),
                validateNull(carData.getColor()),
                validatePositiveInt(carData.getMileage()),
                validateItemsMatchesRegex(itemsNameRegex,carData.getComponents())
        );
    }
}
