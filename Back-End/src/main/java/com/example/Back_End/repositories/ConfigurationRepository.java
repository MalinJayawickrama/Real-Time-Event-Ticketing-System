package com.example.Back_End.repositories;

import com.example.Back_End.config.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
}
