package com.app.persistence.data.reader.model.db;

import com.app.persistence.data.reader.model.CarData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarDataDb {
    Long id;
    String model;
    BigDecimal price;
    String color;
    int mileage;
    ComponentDataDb component;

    public CarData toCarDataWithoutComponents() {
        return CarData.of(this.id, this.model, this.price, this.color, this.mileage, null);
    }

    public CarData toCarDataWithComponents() {
        return CarData.of(this.id, this.model, this.price, this.color, this.mileage,
                new HashSet<>(List.of(component.toComponentData())));
    }
}
