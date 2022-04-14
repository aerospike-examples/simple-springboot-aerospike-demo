package com.aerospike.demo.simplespringbootaerospikedemo.configuration;

import com.aerospike.demo.simplespringbootaerospikedemo.repositories.MongoUserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = MongoUserRepository.class)
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }
}
