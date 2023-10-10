package com.app.persistence.data.reader.validator;

import com.app.persistence.data.reader.model.CarData;

import java.util.List;

public interface CarDataValidator extends DataValidator<List<CarData>> {
    CarData validateSingleCar(CarData carData);
}
