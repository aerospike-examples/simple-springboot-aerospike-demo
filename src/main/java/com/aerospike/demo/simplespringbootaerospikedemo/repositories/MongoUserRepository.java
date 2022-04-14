package com.aerospike.demo.simplespringbootaerospikedemo.repositories;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<UserMongo, Object> {
}
