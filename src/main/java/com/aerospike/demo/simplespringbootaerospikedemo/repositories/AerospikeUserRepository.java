package com.aerospike.demo.simplespringbootaerospikedemo.repositories;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AerospikeUserRepository extends AerospikeRepository<User, Integer>, CrudRepository<User, Integer> {

    List<User> findByAgeLessThan(int age);
}
