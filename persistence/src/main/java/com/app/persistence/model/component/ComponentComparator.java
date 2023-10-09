package com.app.persistence.model.component;

import java.util.Comparator;

public interface ComponentComparator {
    Comparator<Component> byComponentName = Comparator.comparing(component -> component.name);
}
