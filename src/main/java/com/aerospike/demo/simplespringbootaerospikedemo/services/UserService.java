package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> readUserById(int id);

    void addOrUpdateUser(User user);

    void removeUserById(int id);

    void truncate();

    List<User> findByAgeLessThan(int age);

    List<User> findByEmail(String email);

    List<User> findByEmailStartsWith(String emailPrefix);
}
