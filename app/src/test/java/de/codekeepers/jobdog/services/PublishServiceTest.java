package de.codekeepers.jobdog.services;

import de.codekeepers.jobdog.entities.Job;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PublishServiceTest {

    private Job job;
    private  String xml;

    @Test
    void shouldConvertToXml() {

        givenAJob("Chef", "Do it.", "restaurant");
        whenConvertToXml();
        thenXmlDataIs("<job>" +
                "<title>Chef</title>" +
                "<description>Do it.</description>" +
                "</job>");
    }

    private void thenXmlDataIs(String data) {

        Assert.assertEquals(data, xml);
    }

    private void whenConvertToXml() {

        xml = PublishService.convertToXml(job);
    }

    private void givenAJob(String title, String description, String tags) {

        job = new Job();
        job.setTitle(title);
        job.setTags(tags);
        job.setDescription(description);
    }
}
