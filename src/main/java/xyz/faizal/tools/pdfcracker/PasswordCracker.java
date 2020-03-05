package xyz.faizal.tools.pdfcracker;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by faizal on 05/03/2020.
 */
@Component
public class PasswordCracker {
    public static final Logger LOGGER = LoggerFactory.getLogger(PasswordCracker.class);

    @Bean(name = "threadPoolExecutor")
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setMaxPoolSize(200);
        taskExecutor.setCorePoolSize(150);
        return taskExecutor;
    }

    @Async("threadPoolExecutor")
    public void doCrack(final String passwordList, final String pdfPath) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(passwordList))) {
            stream.forEach(password -> {
                try {
                    PDDocument.load(new File(pdfPath), password.trim());
                    LOGGER.info("Success with " + password.trim());
                    System.exit(0);
                } catch (InvalidPasswordException e) {
                    LOGGER.error("Meh wrong password: " + password.trim());
                } catch (Exception e) {
                    LOGGER.error("Failed to load file", e);
                }
            });
        }
    }
}
