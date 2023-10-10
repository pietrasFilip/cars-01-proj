package com.app.persistence.data.reader.factory;

import com.app.persistence.data.reader.converter.Converter;
import com.app.persistence.data.reader.loader.DataLoader;
import com.app.persistence.data.reader.validator.DataValidator;

public interface DataFactory <T, U>{
    DataLoader<T> createDataLoader();
    DataValidator<T> createValidator();
    Converter<T, U> createConverter();
}
