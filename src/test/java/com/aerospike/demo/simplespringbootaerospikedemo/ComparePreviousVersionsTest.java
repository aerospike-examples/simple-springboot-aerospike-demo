package com.aerospike.demo.simplespringbootaerospikedemo;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ComparePreviousVersionsTest {

    @Autowired
    UserService userService;

    @Test
    void crudOps() {
        int batchSize = 200000;
        LocalTime start;
        LocalTime end;
        long elapsedSeconds;
        userService.truncate();

        System.out.println("-------------------------- Aerospike Spring CRUD Test ---------------------------");
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

    @Test
    void findByAgeLT() {
        int batchSize = 200000;
        LocalTime start;
        LocalTime end;
        long elapsedMillis;
        userService.truncate();

        System.out.println("-------------------------- Aerospike Spring FindByField Test ---------------------------");
        System.out.println("----------------- Adding " + batchSize + " users (In parallel)... -----------------");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        System.out.println("-------------------------- Find By Age LT user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        System.out.println("\nStart time:\n" + start + "\n");

        List<User> result = userService.findByAgeLessThan(50);
        System.out.println("Result size: " + result.size() + "\n");

        end = LocalDateTime.now().toLocalTime();
        System.out.println("End time:\n" + end + "\n");
        elapsedMillis = Duration.between(start, end).toMillis();
        System.out.println("Total execution time in milliseconds:\n" + elapsedMillis + "\n");
    }
}
