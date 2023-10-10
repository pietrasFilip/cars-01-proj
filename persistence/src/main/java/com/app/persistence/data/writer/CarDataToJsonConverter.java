package com.app.persistence.data.writer;

import com.app.persistence.data.reader.model.CarData;

import java.util.List;

public interface CarDataToJsonConverter extends ToJsonConverter<List<CarData>> {
}
