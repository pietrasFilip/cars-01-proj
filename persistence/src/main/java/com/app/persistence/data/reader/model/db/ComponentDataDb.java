package com.app.persistence.data.reader.model.db;

import com.app.persistence.data.reader.model.ComponentData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComponentDataDb {
    private Long id;
    private String name;

    public ComponentData toComponentData() {
        return new ComponentData(this.id, this.name);
    }
}
