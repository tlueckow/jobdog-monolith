package de.codekeepers.jobdog.repositories;

import de.codekeepers.jobdog.entities.Job;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long> {
    
    List<Job> findByTitle(String title);

    List<Job> findByPublishedTimestampIsNullAndPublishAtBeforeAndPublishTrialsLessThan(LocalDateTime now, int maxPublishTrials);

}
