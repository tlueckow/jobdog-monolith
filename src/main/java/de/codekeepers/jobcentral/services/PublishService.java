package de.codekeepers.jobcentral.services;

import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void publishJobPosting() {

    logger.info("Start publishing... ");

    Iterable<Job> jobsToBePublished = jobRepository.findAll();

    logger.info("Published {} jobs.", StreamSupport.stream(jobsToBePublished.spliterator(), false).count());
  }

}