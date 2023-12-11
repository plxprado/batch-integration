package br.com.plx.integration.batchintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class BatchIntegrationApplication {


	public static void main(String[] args)
	{
		int coresAvailable = Runtime.getRuntime().availableProcessors();
		System.out.println("CORES ======> [" + coresAvailable + "] AVAILABLE");
		SpringApplication.run(BatchIntegrationApplication.class, args);
	}

}
