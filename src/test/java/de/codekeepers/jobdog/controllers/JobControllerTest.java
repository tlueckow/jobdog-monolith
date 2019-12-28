package de.codekeepers.jobdog.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import de.codekeepers.jobdog.controllers.JobController;
import de.codekeepers.jobdog.entities.Job;
import de.codekeepers.jobdog.repositories.JobRepository;

public class JobControllerTest {

    private static JobController jobController;
    private static JobRepository mockedJobRepository;
    private static BindingResult mockedBindingResult;
    private static Model mockedModel;

    @BeforeClass
    public static void setUpJobControllerInstance() {
        mockedJobRepository = mock(JobRepository.class);
        mockedBindingResult = mock(BindingResult.class);
        mockedModel = mock(Model.class);
        jobController = new JobController(mockedJobRepository);
    }

    @Test
    public void whenCalledshowSignUpForm_thenCorrect() {
        Job job = new Job("John", "john@domain.com");

        assertThat(jobController.showSignUpForm(job)).isEqualTo("post-job");
    }
    
    @Test
    public void whenCalledaddJobAndValidJob_thenCorrect() {
        Job job = new Job("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(false);

        assertThat(jobController.addJob(job, mockedBindingResult, mockedModel)).isEqualTo("index");
    }

    @Test
    public void whenCalledaddJobAndInValidJob_thenCorrect() {
        Job job = new Job("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(jobController.addJob(job, mockedBindingResult, mockedModel)).isEqualTo("post-job");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCalledshowUpdateForm_thenIllegalArgumentException() {
        assertThat(jobController.showUpdateForm(0, mockedModel)).isEqualTo("update-job");
    }
    
    @Test
    public void whenCalledupdateJobAndValidJob_thenCorrect() {
        Job job = new Job("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(false);

        assertThat(jobController.updateJob(1l, job, mockedBindingResult, mockedModel)).isEqualTo("index");
    }

    @Test
    public void whenCalledupdateJobAndInValidJob_thenCorrect() {
        Job job = new Job("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(jobController.updateJob(1l, job, mockedBindingResult, mockedModel)).isEqualTo("update-job");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void whenCalleddeleteJob_thenIllegalArgumentException() {
        assertThat(jobController.deleteJob(1l, mockedModel)).isEqualTo("index");
    }
}
