package com.app.persistence.model.component;

import java.util.Objects;

public class Component {
    final Long id;
    final String name;

    public Component(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Methods from Object class

    @Override
    public String toString() {
        return "COMPONENT: %s".formatted(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(name, component.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Static methods

    public static Component of(Long id, String name) {
        return new Component(id, name);
    }
}
