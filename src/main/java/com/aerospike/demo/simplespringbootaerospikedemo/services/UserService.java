package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.repositories.AerospikeUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    AerospikeUserRepository aerospikeUserRepository;

    public Optional<User> readUserById(int id) {
        return aerospikeUserRepository.findById(id);
    }

    public void addOrUpdateUser(User user) {
        aerospikeUserRepository.save(user);
    }

    public void removeUserById(int id) {
        aerospikeUserRepository.deleteById(id);
    }

    public void truncate() {
        aerospikeUserRepository.deleteAll();
    }
}
