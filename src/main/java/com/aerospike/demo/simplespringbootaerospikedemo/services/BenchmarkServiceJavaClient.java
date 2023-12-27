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
public class BenchmarkServiceJavaClient implements BenchmarkService {

    UserServiceJavaClient userServiceJavaClient;

    @Override
    public void runBenchmark(int batchSize) {

    }

    @Override
    public void runCrudOpsBenchmark(int batchSize) {
        LocalTime start;
        LocalTime end;
        long elapsedSeconds;
        userServiceJavaClient.truncate();

        log.info("-------------------------- CRUD Benchmark - Spring Data Aerospike ---------------------------");
        log.info("-------------------------- Add user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        for (int i = 0; i < batchSize; i++) {
            userServiceJavaClient.addOrUpdateUser(new User(i, "Username" + i, "username" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        userServiceJavaClient.truncate();

        log.info("----------------- Parallel add user -----------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Parallel start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceJavaClient.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("Parallel end time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        for (int i = 0; i < batchSize; i++) {
            userServiceJavaClient.readUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Parallel read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i -> userServiceJavaClient.readUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        for (int i = 0; i < batchSize; i++) {
            userServiceJavaClient.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Parallel update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceJavaClient.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Delete user by id ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        for (int i = 0; i < batchSize; i++) {
            userServiceJavaClient.removeUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        // Insert again to test delete parallel
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceJavaClient.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        log.info("-------------------------- Parallel delete user by id  ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i -> userServiceJavaClient.removeUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);
    }

    @Override
    public void runFindByFieldsBenchmark(int batchSize) {

    }
}