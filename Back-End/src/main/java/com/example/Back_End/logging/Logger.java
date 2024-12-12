package com.example.Back_End.logging;

import com.example.Back_End.models.Log;
import com.example.Back_End.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Logger {
    private static final String LOG_FILE = "resources/simulation.log";

    @Autowired
    private LogRepository logRepository;

    /**
     * Initializes the log file and clears logs in MongoDB.
     */
    public synchronized void logInitialize() {
        // Clear the file logs
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            writer.write(""); // Clear the file by writing an empty string
        } catch (IOException e) {
            System.err.println("Failed to initialize log file: " + e.getMessage());
        }

        // Clear MongoDB logs
        try {
            if (logRepository != null) {
                logRepository.deleteAll();
                System.out.println("Cleared logs from MongoDB.");
            }
        } catch (Exception e) {
            System.err.println();
        }
    }

    /**
     * Logs a message to the console, file, and MongoDB.
     *
     * @param message The message to log.
     */
    public synchronized void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = timestamp + " - " + formatMessage(message);
        System.out.println(logMessage);

        // Save log to MongoDB
        saveToDatabase(formatMessage(message));

        // Save log to file
        saveToFile(logMessage);
    }

    private void saveToDatabase(String message) {
        try {
            if (logRepository != null) {
                Log log = new Log();
                log.setTimestamp(LocalDateTime.now());
                log.setMessage(message);
                logRepository.save(log);
            } else {
                System.err.println("LogRepository is not initialized. MongoDB logging skipped.");
            }
        } catch (Exception e) {
            System.err.println("Failed to save log to MongoDB: " + e.getMessage());
        }
    }

    private void saveToFile(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    private String formatMessage(String message) {
        return message.replace("\\n", System.lineSeparator());
    }
}
