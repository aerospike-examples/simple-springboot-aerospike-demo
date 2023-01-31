package com.aerospike.demo.simplespringbootaerospikedemo.repositories;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.data.repository.CrudRepository;

public interface AerospikeUserRepository extends AerospikeRepository<User, Integer>, CrudRepository<User, Integer> {
}
