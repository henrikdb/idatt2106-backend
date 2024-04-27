package no.ntnu.idi.stud.savingsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Sparesti application. This class bootstraps the Spring
 * application and configures it for execution.
 */
@SpringBootApplication
public class SparestiApplication {

	/**
	 * The main method that starts the Sparesti application.
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SparestiApplication.class, args);
	}

}
