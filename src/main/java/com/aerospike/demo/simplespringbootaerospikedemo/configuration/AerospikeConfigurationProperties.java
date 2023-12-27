package com.aerospike.demo.simplespringbootaerospikedemo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Primary
@Component
@ConfigurationProperties(prefix = "aerospike")
public class AerospikeConfigurationProperties {
    private String host;
    private int port;
    private String namespace;
}
