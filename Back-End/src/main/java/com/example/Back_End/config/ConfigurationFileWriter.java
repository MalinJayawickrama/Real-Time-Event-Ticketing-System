package com.example.Back_End.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for saving and loading Configuration objects to and from JSON files.
 */
public class ConfigurationFileWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // For converting objects to/from JSON

    /**
     * Saves the Configuration object to a file as JSON.
     *
     * @param config   The Configuration object to save.
     * @param filePath The path of the file where the JSON should be saved.
     * @throws IOException If there is an issue writing to the file.
     */
    public static void saveConfiguration(Configuration config, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), config); // Converts Configuration to JSON and saves it
    }

    /**
     * Loads the Configuration object from a JSON file.
     *
     * @param filePath The path of the file containing the JSON configuration.
     * @return A Configuration object.
     * @throws IOException If there is an issue reading from the file.
     */
    public static Configuration loadConfiguration(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), Configuration.class); // Reads JSON and converts it to Configuration
    }
}
