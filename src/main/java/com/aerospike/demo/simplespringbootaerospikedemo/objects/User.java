package com.aerospike.demo.simplespringbootaerospikedemo.objects;

import com.aerospike.client.query.IndexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.aerospike.annotation.Indexed;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Document(collection = "users")
@AllArgsConstructor
public class User {
    @Id
    private int id;
    private String name;
    @Indexed(type = IndexType.STRING)
    private String email;
    @Indexed(type = IndexType.NUMERIC)
    private int age;
    private byte[] content;
}
