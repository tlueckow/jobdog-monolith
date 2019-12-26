package de.codekeepers.jobcentral.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.codekeepers.jobcentral.services.PublishService;

/**
 * PublishTask
 */
@Component
public class PublishJob implements Job {

  @Autowired
  private PublishService publishService;

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    
    publishService.publishJobPosting();

  }

  
}