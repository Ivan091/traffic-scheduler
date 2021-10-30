package edu.repo;

import edu.model.intensity.Intensity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface IntensityRepo extends CrudRepository<Intensity, Integer> {

    List<Intensity> findByObservationInterval(Integer observationInterval);
}
