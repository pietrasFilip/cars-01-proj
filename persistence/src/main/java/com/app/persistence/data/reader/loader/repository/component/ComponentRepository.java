package com.app.persistence.data.reader.loader.repository.component;

import com.app.persistence.data.reader.model.ComponentData;

import java.util.List;
import java.util.Set;

public interface ComponentRepository {
    Set<ComponentData> findAll();
    Set<ComponentData> findAllById(List<Long> ids);
}
