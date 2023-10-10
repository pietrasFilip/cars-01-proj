package com.app.persistence.data.reader.processor;

import com.app.persistence.data.reader.loader.Fetch;

import java.util.List;

public interface DataProcessor <T>{
    List<T> process(Fetch fetch);
}
