package com.app.persistence.data.reader.loader.repository.component.txt;

import com.app.persistence.data.reader.loader.repository.component.ComponentRepository;
import com.app.persistence.data.reader.model.ComponentData;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TxtComponentRepositoryImpl implements ComponentRepository {
    private final Set<ComponentData> components;

    public TxtComponentRepositoryImpl(String filename) {
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

    private static Set<ComponentData> init(String filename) {
        try (var lines = Files.lines(Paths.get(filename))) {
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        return ComponentData.of(
                                Long.valueOf(items[0]),
                                items[1]);
                    })
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
