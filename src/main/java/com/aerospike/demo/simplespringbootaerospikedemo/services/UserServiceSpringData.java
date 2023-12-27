package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.repositories.AerospikeUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceSpringData implements UserService {

    AerospikeUserRepository aerospikeUserRepository;

    @Override
    public Optional<User> readUserById(int id) {
        return aerospikeUserRepository.findById(id);
    }

    @Override
    public void addOrUpdateUser(User user) {
        aerospikeUserRepository.save(user);
    }

    @Override
    public void removeUserById(int id) {
        aerospikeUserRepository.deleteById(id);
    }

    @Override
    public void truncate() {
        aerospikeUserRepository.deleteAll();
    }

    @Override
    public List<User> findByAgeLessThan(int age) {
        return aerospikeUserRepository.findByAgeLessThan(age);
    }

    @Override
    public List<User> findByEmail(String email) {
        return aerospikeUserRepository.findByEmail(email);
    }

    @Override
    public List<User> findByEmailStartsWith(String emailPrefix) {
        return aerospikeUserRepository.findByEmailStartsWith(emailPrefix);
    }
}
