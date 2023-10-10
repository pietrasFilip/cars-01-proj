package com.app.persistence.data.reader.model;


import com.app.persistence.model.component.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComponentData {
    private Long id;
    private String name;

    public Component toComponent() {
        return Component.of(this.id, this.name);
    }

    public static ComponentData of(Long id, String name) {
        return new ComponentData(id, name);
    }
}
