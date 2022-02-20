package edu.model.intensity;

import edu.model.order.Path;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;


@Table("IntensitiesByIntervals")
public record Intensity(@Id Integer id,
                        @Embedded.Empty Path path,
                        Integer observationInterval,
                        Double intensity
) {

}
