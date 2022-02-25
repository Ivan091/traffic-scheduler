package edu.model.intensity;

import edu.model.order.Path;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;


@Table("IntensitiesByIntervals")
@Value
@With
public class PathIntensity {

    @Id
    Integer id;

    @Embedded.Empty
    Path path;

    Integer observationInterval;

    Double intensity;
}
