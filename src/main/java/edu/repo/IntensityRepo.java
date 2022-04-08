package edu.repo;

import edu.model.intensity.PathIntensity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface IntensityRepo extends CrudRepository<PathIntensity, Integer> {

    @Query("""
            SELECT "ObservationInterval", "Origin", "Destination", "Intensity", "ID"
            FROM "IntensitiesByIntervals" ib
            WHERE "ObservationInterval" = :observationInterval AND "Origin"  = :origin
            """)
    List<PathIntensity> findByObservationIntervalAndPathOrigin(@Param("observationInterval") Integer observationInterval, @Param("origin") Integer origin);

    @Query("""
            SELECT DISTINCT "Origin" FROM "IntensitiesByIntervals"
            """)
    List<Integer> getOrigins();
}
