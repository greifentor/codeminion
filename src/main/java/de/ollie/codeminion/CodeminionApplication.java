package de.ollie.codeminion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CodeminionApplication extends JFrame {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(CodeminionApplication.class).headless(false)
				.run(args);
		EventQueue.invokeLater(() -> {
			CodeminionApplication ex = ctx.getBean(CodeminionApplication.class);
			JOptionPane.showMessageDialog(ex, "CodeMinion!");
			int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
				@Override
				public int getExitCode() {
					// no errors
					return 0;
				}
			});
			System.exit(exitCode);
		});
	}

}