package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.repositories.AerospikeUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> findByAgeLessThan(int age) {
        return aerospikeUserRepository.findByAgeLessThan(age);
    }

    public List<User> findByEmail(String email) {
        return aerospikeUserRepository.findByEmail(email);
    }

    public List<User> findByEmailStartsWith(String emailPrefix) {
        return aerospikeUserRepository.findByEmailStartsWith(emailPrefix);
    }
}
