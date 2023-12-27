package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class BenchmarkService {

    UserService userService;

    public void runBenchmark() {
        runCrudOpsBenchmark();
    }

    private void runCrudOpsBenchmark() {
        int batchSize = 200000;
        LocalTime start;
        LocalTime end;
        long elapsedSeconds;
        userService.truncate();

        log.info("-------------------------- Aerospike Spring CRUD Test ---------------------------");
        log.info("-------------------------- Add user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.addOrUpdateUser(new User(i, "Username" + i, "username" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        userService.truncate();

        log.info("----------------- Parallel add user -----------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nParallel start time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("Parallel end time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.readUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Parallel read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i -> userService.readUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Parallel update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Delete user by id ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userService.removeUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        // Insert again to test delete parallel
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userService.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        log.info("-------------------------- Parallel delete user by id  ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i -> userService.removeUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");
    }
}
