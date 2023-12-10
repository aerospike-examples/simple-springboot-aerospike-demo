package com.aerospike.demo.simplespringbootaerospikedemo;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;

@SpringBootTest
public class ComparePreviousVersionsTest {

    @Autowired
    UserService userService;

    @Test
    void crudOpsMeasureTimes() {
        int batchSize = 200000;
        LocalTime start;
        LocalTime end;
        long elapsedSeconds;
        userService.truncate();

        System.out.println("-------------------------- Aerospike Spring Test ---------------------------");
        System.out.println("-------------------------- Add user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.addOrUpdateUser(new User(i, "Username" + i, "username" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        userService.truncate();

        System.out.println("----------------- Parallel add user -----------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nParallel start time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        System.out.println("Parallel end time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        System.out.println("-------------------------- Read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.readUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        System.out.println("-------------------------- Parallel read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i -> userService.readUserById(i));
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        System.out.println("-------------------------- Update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        System.out.println("-------------------------- Parallel update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        System.out.println("-------------------------- Delete user by id ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.removeUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        // Insert again to test delete parallel
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        System.out.println("-------------------------- Parallel delete user by id  ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i -> userService.removeUserById(i));
        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        System.out.println("Total execution time in seconds:\n" + elapsedSeconds + "\n");
    }
}
