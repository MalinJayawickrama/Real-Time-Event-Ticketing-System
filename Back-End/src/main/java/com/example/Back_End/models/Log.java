package com.example.Back_End.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs") // Specify the MongoDB collection
public class Log {
    @Id
    private String id; // MongoDB's unique identifier
    private LocalDateTime timestamp; // Timestamp for the log entry
    private String message; // The log message

    // Default constructor for MongoDB
    public Log() {
    }

    // Constructor with parameters
    public Log(String id, LocalDateTime timestamp, String message) {
        this.id = id;
        this.timestamp = timestamp;
        this.message = message;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return timestamp + " - " + message;
    }
}
