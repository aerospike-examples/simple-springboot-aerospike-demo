package com.aerospike.demo.simplespringbootaerospikedemo.repositories;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import org.springframework.data.aerospike.repository.AerospikeRepository;

public interface AerospikeUserRepository extends AerospikeRepository<User, Integer> {
}
