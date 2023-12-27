package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.client.IAerospikeClient;
import com.aerospike.demo.simplespringbootaerospikedemo.configuration.AerospikeConfigurationProperties;
import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceJavaClient implements UserService {

    IAerospikeClient aerospikeClient;
    AerospikeConfigurationProperties configurationProperties;

    @Override
    public Optional<User> readUserById(int id) {
        return Optional.empty();
    }

    @Override
    public void addOrUpdateUser(User user) {

    }

    @Override
    public void removeUserById(int id) {

    }

    @Override
    public void truncate() {

    }

    @Override
    public List<User> findByAgeLessThan(int age) {
        return null;
    }

    @Override
    public List<User> findByEmail(String email) {
        return null;
    }

    @Override
    public List<User> findByEmailStartsWith(String emailPrefix) {
        return null;
    }
}
