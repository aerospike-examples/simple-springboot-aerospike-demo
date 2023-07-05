package com.aerospike.demo.simplespringbootaerospikedemo;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.repositories.AerospikeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTests extends SimpleSpringbootAerospikeDemoApplicationTests {

    int id, age;
    User user;

    @Autowired
    AerospikeUserRepository repository;

    @BeforeEach
    void setUp() {
        id = new Random().nextInt();
        age = new Random().nextInt(1, 50);
        user = new User(id, "Michael", "m@abc.com", age);
    }

    @Test
    public void saveUser() {
        repository.save(user);
        assertThat(repository.findById(id)).hasValue(user);
    }

    @Test
    public void deleteUser() {
        repository.delete(user);
        assertThat(repository.existsById(id)).isFalse();
    }
}
