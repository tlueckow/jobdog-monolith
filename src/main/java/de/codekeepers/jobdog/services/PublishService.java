package de.codekeepers.jobdog.services;

import de.codekeepers.jobdog.entities.Job;
import de.codekeepers.jobdog.repositories.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * PublishService
 */
@Service
public class PublishService {

    private static final Logger logger = LoggerFactory.getLogger(PublishService.class);

    @Autowired
    private JobRepository jobRepository;

    @Value("${app.publishService.url}")
    private String url;

    static String convertToXml(Job job) {

        return tag("job",
                tag("title", job.getTitle()) +
                        tag("description", job.getDescription()) +
                        (StringUtils.isEmpty(job.getTags()) ? "" : tag("categories", Arrays.stream(job.getTags().split(",")).map(s -> tag("category", s)).collect(Collectors.joining())))
        );
    }

    private static String tag(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    @Transactional
    public void publishJobPosting() {

        logger.info("Start publishing... ");

        List<Job> jobsToBePublished = jobRepository
                .findByPublishedTimestampIsNullAndPublishAtBefore(LocalDateTime.now());
        long count = StreamSupport.stream(jobsToBePublished.spliterator(), false).count();
        logger.info("Jobs count to be published: {}", count);

        jobsToBePublished.stream().forEach(j -> publish(j));

        logger.info("Finished publishing.");
    }

    private void publish(Job job) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> request = new HttpEntity<>(convertToXml(job), headers);

        try {
            ResponseEntity<String> response = restTemplate
                    .postForEntity(url, request, String.class);

            HttpStatus status = response.getStatusCode();
            job.setPublishedTimestamp(LocalDateTime.now());
            jobRepository.save(job);
            logger.info("Published job: {}, status={}", job.getId(), status);
        } catch (Exception ex) {
            logger.error("Error publishing job: #{}", ex, job.getId());
        }
    }

}