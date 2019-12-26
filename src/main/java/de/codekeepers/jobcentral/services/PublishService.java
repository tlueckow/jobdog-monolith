package de.codekeepers.jobcentral.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.codekeepers.jobcentral.entities.Job;
import de.codekeepers.jobcentral.repositories.JobRepository;

/**
 * PublishService
 */
@Service
public class PublishService {

  private static final Logger logger = LoggerFactory.getLogger(PublishService.class);

  @Autowired
  private JobRepository jobRepository;
  
  @Transactional
  public void publishJobPosting() {

    logger.info("Start publishing... ");

    List<Job> jobsToBePublished = jobRepository
        .findByPublishedTimestampIsNullAndPublishDateTimeBefore(LocalDateTime.now());
    long count = StreamSupport.stream(jobsToBePublished.spliterator(), false).count();
    logger.info("Jobs count to be published: {}", count);

    jobsToBePublished.stream().forEach(j -> publish(j));

    logger.info("Finished publishing.");
  }

  private static String convertToXml(Job job) {

    return "<job><description>" + job.getDescription() + "</description></job>";
  }

  private void publish(Job job) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    HttpEntity<String> request = new HttpEntity<String>(convertToXml(job), headers);

    try {
      ResponseEntity<String> response = restTemplate
          .postForEntity("https://jkyszaoly5.execute-api.eu-central-1.amazonaws.com/dev/jobs", request, String.class);

      HttpStatus status = response.getStatusCode();
      job.setPublishedTimestamp(LocalDateTime.now());
      jobRepository.save(job);
      logger.info("Published job: {}, status={}", job.getId(), status);
    } catch (Exception ex) {
      logger.error("Error publishing job: #{}", ex, job.getId());
    }
  }

}