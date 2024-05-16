package com.prueba.capital.humano.empresa.mappers;

import com.prueba.capital.humano.empresa.dto.JobDto;
import com.prueba.capital.humano.empresa.entities.Job;

public class JobMapper {

    public static JobDto toJobDto(Job job) {
        if (job == null) {
            return null;
        }
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setName(job.getName());
        jobDto.setSalary(job.getSalary());
        return jobDto;
    }

    public static Job toJob(JobDto jobDto) {
        if (jobDto == null) {
            return null;
        }
        Job job = new Job();
        job.setId(jobDto.getId());
        job.setName(jobDto.getName());
        job.setSalary(jobDto.getSalary());
        return job;
    }
}
