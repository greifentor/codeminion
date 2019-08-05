/*
 * ApplicationCloser.java
 *
 * (c) by Ollie
 *
 * 05.08.2019
 */
package de.ollie.codeminion;

import java.awt.EventQueue;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A class which is able to close the application correctly.
 * 
 * @author ollie
 *
 */
public class ApplicationCloser {

	/**
	 * Closes the application and aborting the VM.
	 * 
	 * @param context An application context.
	 */
	public void close(ConfigurableApplicationContext context) {
		EventQueue.invokeLater(() -> {
			int exitCode = SpringApplication.exit(context, new ExitCodeGenerator() {
				@Override
				public int getExitCode() {
					return 0;
				}
			});
			System.exit(exitCode);
		});
	}

}