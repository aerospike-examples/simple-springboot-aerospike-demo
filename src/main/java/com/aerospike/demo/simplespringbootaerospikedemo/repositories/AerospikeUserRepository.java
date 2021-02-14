package com.aerospike.demo.simplespringbootaerospikedemo.repositories;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import org.springframework.data.repository.CrudRepository;

public interface AerospikeUserRepository extends CrudRepository<User, Object> {
}
