package edu.model.intensity;

import edu.model.order.Path;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;


@Value
@Table("IntensitiesByIntervals")
public class Intensity {

    @Id
    @With
    Integer id;

    @Embedded.Empty
    Path path;

    Integer observationInterval;

    Double intensity;

    public Integer getOrigin() {
        return path.getOrigin();
    }

    public Integer getDestination() {
        return path.getDestination();
    }
}
