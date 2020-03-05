package xyz.faizal.tools.pdfcracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by faizal on 05/03/2020.
 */
@ShellComponent
public class CrackPassword {
    public static final Logger LOGGER = LoggerFactory.getLogger(CrackPassword.class);

    @Autowired
    private PasswordCracker passwordCracker;

    @ShellMethod("Crack password for this file")
    public void crack(final String path, final String pdfPath) {
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(file -> file.toString())
                    .filter(filename -> filename.endsWith(".txt"))
                    .forEach(file -> {
                        try {
                            LOGGER.info("Cracking with password: " + file);
                            passwordCracker.doCrack(file, pdfPath);
                        } catch (Exception e) {
                            LOGGER.error("Failed to crack.", e);
                        }
                    });
        } catch (Exception e) {
            LOGGER.error("Shit!", e);
        }
    }
}
