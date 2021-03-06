package de.codekeepers.jobdog.services;

import de.codekeepers.jobdog.entities.Job;
import de.codekeepers.jobdog.repositories.JobRepository;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
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
                textNode("title", job.getTitle()) +
                        textNode("description", job.getDescription()) +
                        (StringUtils.isEmpty(job.getTags()) ? "" : tag("categories", Arrays.stream(job.getTags().split(",")).map(s -> textNode("category", s)).collect(Collectors.joining())))
        );
    }

    private static String tag(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    private static String textNode(String tag, String text) {
        return tag(tag, StringEscapeUtils.escapeXml11(text));
    }

    @Transactional
    public void publishJobPosting() {

        logger.info("Start publishing... ");

        List<Job> jobsToBePublished = jobRepository
                .findByPublishedTimestampIsNullAndPublishAtBeforeAndPublishTrialsLessThan(LocalDateTime.now(), 1);
        long count = StreamSupport.stream(jobsToBePublished.spliterator(), false).count();
        logger.info("Jobs count to be published: {}", count);

        jobsToBePublished.stream().forEach(j -> publish(j));

        logger.info("Finished publishing.");
    }

    private void publish(Job job) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        String xml = convertToXml(job);
        HttpEntity<String> request = new HttpEntity<>(xml, headers);

        try {
            ResponseEntity<String> response = restTemplate
                    .postForEntity(url, request, String.class);

            HttpStatus status = response.getStatusCode();
            job.setPublishedTimestamp(LocalDateTime.now());
            logger.info("Published job: {}, status={}", job.getId(), status);
        } catch (HttpClientErrorException ex) {
            HttpStatus status = ex.getStatusCode();
            logger.error("Client error publishing job: #{}, status={}, body={}", job.getId(), status, xml);
        } catch (Exception ex) {
            logger.error("Error publishing job: #" + job.getId(), ex);
        } finally {
            job.incPublishTrials();
            jobRepository.save(job);
        }
    }

}