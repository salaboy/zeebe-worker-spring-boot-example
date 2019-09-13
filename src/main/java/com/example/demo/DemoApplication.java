package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.response.ActivatedJob;

import java.time.Instant;

@SpringBootApplication
@EnableZeebeClient
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@ZeebeWorker(type = "classify")
	public void classifyEmergency(final JobClient client, final ActivatedJob job) {
		logJob(job);
		client.newCompleteCommand(job.getKey()).variables("{\"foo\": 1}").send().join();
	}


	@ZeebeWorker(type = "hospital")
	public void handleHospitalCoordination(final JobClient client, final ActivatedJob job) {
		logJob(job);
		client.newCompleteCommand(job.getKey()).variables("{\"foo\": 1}").send().join();
	}

	@ZeebeWorker(type = "firefighter")
	public void handleFirefighterCoordination(final JobClient client, final ActivatedJob job) {
		logJob(job);
		client.newCompleteCommand(job.getKey()).variables("{\"foo\": 1}").send().join();
	}


	private static void logJob(final ActivatedJob job) {
		log.info(
				"complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
				job.getType(),
				job.getKey(),
				job.getElementId(),
				job.getWorkflowInstanceKey(),
				Instant.ofEpochMilli(job.getDeadline()),
				job.getCustomHeaders(),
				job.getVariables());
	}

}
