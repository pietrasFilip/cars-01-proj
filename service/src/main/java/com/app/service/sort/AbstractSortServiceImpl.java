package com.app.service.sort;

import com.app.persistence.data.reader.loader.Fetch;
import com.app.persistence.data.reader.processor.DataProcessor;
import com.app.service.cars.provider.generic.DataProvider;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public abstract class AbstractSortServiceImpl <T> implements SortService<T>{
    protected final DataProcessor<T> dataProcessor;

    protected AbstractSortServiceImpl(DataProvider<T> dataProvider) {
        this.dataProcessor = dataProvider.provide();
    }

    @Override
    public List<T> sortBy(Comparator<T> comparator) {
        var cars = dataProcessor.process(Fetch.LAZY);
        return cars
                .stream()
                .sorted(comparator)
                .toList();
    }
}