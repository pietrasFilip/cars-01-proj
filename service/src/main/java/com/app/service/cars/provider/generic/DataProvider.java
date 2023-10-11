package com.app.service.cars.provider.generic;

import com.app.persistence.data.reader.processor.DataProcessor;


public interface DataProvider <T> {
    DataProcessor<T> provide();
}
