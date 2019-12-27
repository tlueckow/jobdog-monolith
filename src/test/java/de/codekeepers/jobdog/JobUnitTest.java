package de.codekeepers.jobdog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.codekeepers.jobdog.entities.Job;

public class JobUnitTest {
    
    @Test
    public void whenCalledGetTitle_thenCorrect() {
        Job job = new Job("Sous Chef", "Hands on cooking. ");
        
        assertThat(job.getTitle()).isEqualTo("Sous Chef");
    }
    
    @Test
    public void whenCalledGetDescription_thenCorrect() {
        Job job = new Job("Sous Chef", "Hands on cooking. ");
        
        assertThat(job.getDescription()).isEqualTo("Hands on cooking. ");
    }
    
    @Test
    public void whenCalledSetTitle_thenCorrect() {
        Job job = new Job("Sous Chef", "Hands on cooking. ");
        
        job.setTitle("Chef");
        
        assertThat(job.getTitle()).isEqualTo("Chef");
    }
    
    @Test
    public void whenCalledSetDescription_thenCorrect() {
        Job job = new Job("Sous Chef", "Hands on cooking. ");
        
        job.setDescription("Maintaining labor and food cost at all times.");
        
        assertThat(job.getDescription()).isEqualTo("Maintaining labor and food cost at all times.");
    }
    
    @Test
    public void whenCalledtoString_thenCorrect() {
        Job job = new Job("Sous Chef", "Hands on cooking. ");
        assertThat(job.toString()).isEqualTo("Job{id=0, title=Sous Chef}");
    }
}
