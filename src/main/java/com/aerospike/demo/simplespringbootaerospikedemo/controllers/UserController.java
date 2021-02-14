package com.aerospike.demo.simplespringbootaerospikedemo.controllers;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    UserService userService;

    @PostMapping("/add")
    public void addUserTest(@RequestBody User user) {
        userService.addUser(user);
    }
}
