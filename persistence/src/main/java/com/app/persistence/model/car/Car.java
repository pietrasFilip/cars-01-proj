package com.app.persistence.model.car;

import com.app.persistence.model.component.Component;
import com.app.persistence.model.exception.CarException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Car {
    final Long id;
    final String model;
    final BigDecimal price;
    final Color color;
    final int mileage;
    final Set<Component> components;

    private Car(Long id, String model, BigDecimal price, Color color, int mileage, Set<Component> components) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.color = color;
        this.mileage = mileage;
        this.components = components;
    }

    // Methods that give information about car

    /**
     *
     * @param component Component to check.
     * @return True when car contains given component or false when not.
     */
    public boolean hasComponent(Component component) {
        if (components == null) {
            throw new CarException("Car without components");
        }
        return components.contains(component);
    }

    /**
     *
     * @param referenceMileage Mileage to check.
     * @return True when given mileage is smaller than car mileage or false when not.
     */
    public boolean hasMileageGreaterThan(int referenceMileage) {
        if (referenceMileage < 0) {
            throw new CarException("Mileage less than zero");
        }
        return this.mileage > referenceMileage;
    }

    /**
     *
     * @param from Price lower limit.
     * @param to Price upper limit.
     * @return True when car price is between given limits or false when not.
     */
    public boolean hasPriceBetween(BigDecimal from, BigDecimal to) {
        return this.price.compareTo(from) >= 0 && this.price.compareTo(to) <= 0;
    }

    /**
     *
     * @param componentsComparator Comparator for components.
     * @return Car with components sorted by comparator.
     */
    public Car withSortedComponents(Comparator<Component> componentsComparator) {
        if (components == null) {
            throw new CarException("Car without components");
        }

        var sortedComponents = components.stream().sorted(componentsComparator).collect(Collectors.toCollection(LinkedHashSet::new));
        return Car.of(id, model, price, color, mileage, sortedComponents);
    }

    // Methods from Object class

    @Override
    public String toString() {
        return "CAR=[MODEL= %s, PRICE= %.2f, COLOR= %s, MILEAGE= %d, COMPONENTS= %s]%n".formatted(model, price, color, mileage, components);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car2 = (Car) o;
        return mileage == car2.mileage && Objects.equals(model, car2.model) && Objects.equals(price, car2.price) && color == car2.color && Objects.equals(components, car2.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, color, mileage, components);
    }

    // Static methods

    public static Car of(Long id, String model, BigDecimal price, Color color, int mileage, Set<Component> components) {
        return new Car(id, model, price, color, mileage, components);
    }
}
