package de.codekeepers.jobdog;// Generated by Selenium IDE

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PostJobSimulation {

    private static final Logger logger = LoggerFactory.getLogger(PostJobSimulation.class);

    public static void main(String[] args) {

        String homeDir = System.getProperty("user.home");
        System.setProperty("webdriver.chrome.driver", homeDir + "/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        try {
            URI uri = PostJobSimulation.class.getResource("jobs.txt").toURI();
            List<String> jobCorpus = Files.lines(Paths.get(uri)).collect(Collectors.toList());
            driver.get("http://localhost:8080/");
            driver.manage().window().setSize(new Dimension(1536, 960));

            postJob(driver, jobCorpus, 5);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    private static void postJob(WebDriver driver, List<String> jobCorpus, int count) throws Exception {

        for (int i = 0; i < count; i++) {

            int pos = ThreadLocalRandom.current().nextInt(0, jobCorpus.size());
            playPostJob(driver, jobCorpus.get(pos));
        }
    }

    private static void playPostJob(WebDriver driver, String title) {

        logger.info("Post job: {}", title);

        driver.findElement(By.linkText("New Job")).click();
        driver.findElement(By.id("title")).click();
        driver.findElement(By.id("title")).sendKeys(title);
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("We are hiring.");
        driver.findElement(By.id("tags")).click();
        driver.findElement(By.id("tags")).sendKeys("job");
        driver.findElement(By.id("btnPostJob")).click();
        driver.findElement(By.linkText("Refresh")).click();
    }
}
