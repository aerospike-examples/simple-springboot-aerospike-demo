package com.aerospike.demo.simplespringbootaerospikedemo.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class UserMongo {

    @Id
    private int id;
    private String name;
    private String email;
    private int age;
    private byte[] data;
}
