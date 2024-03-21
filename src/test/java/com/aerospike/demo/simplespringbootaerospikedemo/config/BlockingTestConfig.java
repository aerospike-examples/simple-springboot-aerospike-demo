package com.aerospike.demo.simplespringbootaerospikedemo.config;

import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.demo.simplespringbootaerospikedemo.configuration.AerospikeConfigurationProperties;
import com.aerospike.demo.simplespringbootaerospikedemo.sample.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration;
import org.springframework.data.aerospike.config.AerospikeDataSettings;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Peter Milne
 * @author Jean Mercier
 */
@Configuration
@EnableAerospikeRepositories(basePackageClasses = {PersonRepository.class})
@EnableConfigurationProperties(AerospikeConfigurationProperties.class)
public class BlockingTestConfig extends AbstractAerospikeDataConfiguration {

    @Autowired
    Environment env;

//    @Override
//    protected List<?> customConverters() {
//        return Arrays.asList(
//            SampleClasses.CompositeKey.CompositeKeyToStringConverter.INSTANCE,
//            SampleClasses.CompositeKey.StringToCompositeKeyConverter.INSTANCE
//        );
//    }

    @Override
    protected ClientPolicy getClientPolicy() {
        ClientPolicy clientPolicy = super.getClientPolicy(); // applying default values first
        clientPolicy.readPolicyDefault.maxRetries = 3;
        clientPolicy.writePolicyDefault.totalTimeout = 1000;
        clientPolicy.infoPolicyDefault.timeout = 1000;
        return clientPolicy;
    }
//
//    @Bean
//    public AdditionalAerospikeTestOperations aerospikeOperations(AerospikeTemplate template, IAerospikeClient client,
//                                                                 GenericContainer<?> aerospike,
//                                                                 ServerVersionSupport serverVersionSupport) {
//        return new BlockingAerospikeTestOperations(new IndexInfoParser(), template, client, aerospike,
//            serverVersionSupport);
//    }
//
//    @Override
//    @Bean(name = "aerospikeClient", destroyMethod = "close")
//    public IAerospikeClient aerospikeClient(AerospikeSettings settings) {
//        return new AerospikeClient(getClientPolicy(), settings.getConnectionSettings().getHostsArray());
//    }
    @Autowired
    private AerospikeConfigurationProperties aerospikeConfigurationProperties;

    @Override
    protected Collection<Host> getHosts() {
        return Collections.singleton(new Host(aerospikeConfigurationProperties.getHost(), aerospikeConfigurationProperties.getPort()));
    }

    @Override
    protected String nameSpace() {
        return aerospikeConfigurationProperties.getNamespace();
    }

    @Override
    public void configureDataSettings(AerospikeDataSettings aerospikeDataSettings) {
        aerospikeDataSettings.setScansEnabled(true);
    }
}
