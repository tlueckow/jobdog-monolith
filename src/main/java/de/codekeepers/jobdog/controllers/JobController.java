package de.codekeepers.jobdog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import de.codekeepers.jobdog.entities.Job;
import de.codekeepers.jobdog.repositories.JobRepository;

@Controller
public class JobController {
    
    private final JobRepository jobRepository;

    @Autowired
    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }

    @GetMapping("/newjob")
    public String showSignUpForm(Job job) {
        return "post-job";
    }
    
    @PostMapping("/postjob")
    public String addJob(@Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "post-job";
        }
        
        jobRepository.save(job);
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("job", job);
        return "update-job";
    }
    
    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, @Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            job.setId(id);
            return "update-job";
        }
        
        jobRepository.save(job);
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id, Model model) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        jobRepository.delete(job);
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }
}
