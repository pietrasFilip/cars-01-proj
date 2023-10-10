package com.app.persistence.data.reader.model;

import com.app.persistence.model.car.Car;
import com.app.persistence.model.car.Color;
import com.app.persistence.model.component.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarData {
    private Long id;
    private String model;
    private BigDecimal price;
    private String color;
    private int mileage;
    private Set<ComponentData> components;

    public static CarData of(Long id, String model, BigDecimal price, String color, int mileage, Set<ComponentData> components){
        return new CarData(id, model, price, color, mileage, components);
    }
    public Car toCar() {
        return Car.of(id, model, price, Color.valueOf(color), mileage, toComponents());
    }

    private Set<Component> toComponents() {
        return this.components != null ? this.components
                .stream()
                .map(ComponentData::toComponent)
                .collect(Collectors.toSet()) : null;
    }
}
