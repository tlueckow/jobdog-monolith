package de.codekeepers.jobcentral.repositories;

import de.codekeepers.jobcentral.entities.Job;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {
    
    List<Job> findByTitle(String title);

    List<Job> findByPublishedTimestampIsNullAndPublishDateTimeBefore(LocalDateTime now);

}
