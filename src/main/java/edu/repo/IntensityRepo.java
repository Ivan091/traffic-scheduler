package edu.repo;

import edu.model.intensity.PathIntensity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface IntensityRepo extends CrudRepository<PathIntensity, Integer> {

    List<PathIntensity> findByObservationInterval(Integer observationInterval);
}
