package xyz.faizal.tools.pdfcracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PdfCrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfCrackerApplication.class, args);
	}

}
