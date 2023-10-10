package com.app.persistence.data.writer.impl;

import com.app.persistence.data.reader.model.CarData;
import com.app.persistence.data.writer.CarDataToJsonConverter;

import java.util.List;

public class CarDataToJsonConverterImpl extends ToJsonConverterImpl<List<CarData>> implements CarDataToJsonConverter {
    public CarDataToJsonConverterImpl(String jsonFilename) {
        super(jsonFilename);
    }
}
