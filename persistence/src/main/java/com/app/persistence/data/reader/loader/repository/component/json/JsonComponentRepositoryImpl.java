package com.app.persistence.data.reader.loader.repository.component.json;

import com.app.persistence.data.reader.loader.car.json.FromJsonToObjectLoader;
import com.app.persistence.data.reader.loader.repository.component.ComponentRepository;
import com.app.persistence.data.reader.model.ComponentData;
import com.google.gson.Gson;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonComponentRepositoryImpl extends FromJsonToObjectLoader<Set<ComponentData>> implements ComponentRepository {
    private final Set<ComponentData> components;

    public JsonComponentRepositoryImpl(Gson gson, String filename) {
        super(gson);
        this.components = init(filename);
    }

    @Override
    public Set<ComponentData> findAll() {
        return components;
    }

    @Override
    public Set<ComponentData> findAllById(List<Long> ids) {
        return components
                .stream()
                .filter(component -> ids.contains(component.getId()))
                .collect(Collectors.toSet());
    }

    private Set<ComponentData> init(String filename) {
        return loadObject(filename);
    }
}
