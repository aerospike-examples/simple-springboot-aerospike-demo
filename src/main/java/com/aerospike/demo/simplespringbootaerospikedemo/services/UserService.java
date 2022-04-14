package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.objects.UserMongo;
import com.aerospike.demo.simplespringbootaerospikedemo.repositories.AerospikeUserRepository;
import com.aerospike.demo.simplespringbootaerospikedemo.repositories.MongoUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    AerospikeUserRepository aerospikeUserRepository;
    MongoUserRepository mongoUserRepository;

    public Optional<User> readUserById(int id) {
        return aerospikeUserRepository.findById(id);
    }

    public void addUser(User user) {
        aerospikeUserRepository.save(user);
    }

    public void removeUserById(int id) {
        aerospikeUserRepository.deleteById(id);
    }

    public Optional<UserMongo> readUserMongoById(int id) {
        return mongoUserRepository.findById(id);
    }

    public void addUserMongo(UserMongo userMongo) {
        mongoUserRepository.save(userMongo);
    }

    public void truncateMongo() {
        mongoUserRepository.deleteAll();
    }
}
