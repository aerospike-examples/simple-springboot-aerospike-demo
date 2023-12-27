package com.aerospike.demo.simplespringbootaerospikedemo.services;

import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class BenchmarkServiceImpl implements BenchmarkService {

    UserServiceImpl userServiceImpl;

    public void runBenchmark(int batchSize) {
        runCrudOpsBenchmark(batchSize);
        runFindByFieldsBenchmark(batchSize);
    }

    private void runCrudOpsBenchmark(int batchSize) {
        LocalTime start;
        LocalTime end;
        long elapsedSeconds;
        userServiceImpl.truncate();

        log.info("-------------------------- CRUD Benchmark - Spring Data Aerospike ---------------------------");
        log.info("-------------------------- Add user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userServiceImpl.addOrUpdateUser(new User(i, "Username" + i, "username" + i + "@gmail.com",
                    (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6}));
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        userServiceImpl.truncate();

        log.info("----------------- Parallel add user -----------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nParallel start time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceImpl.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("Parallel end time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userServiceImpl.readUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Parallel read user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i -> userServiceImpl.readUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Update user ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userServiceImpl.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
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
                userServiceImpl.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        log.info("-------------------------- Delete user by id ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
            userServiceImpl.removeUserById(i);
        }
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");

        // Insert again to test delete parallel
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceImpl.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        log.info("-------------------------- Parallel delete user by id  ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");
        IntStream.range(0, batchSize).parallel().forEach(i -> userServiceImpl.removeUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds:\n" + elapsedSeconds + "\n");
    }

    private void runFindByFieldsBenchmark(int batchSize) {
        LocalTime start;
        LocalTime end;
        long elapsedMillis;
        userServiceImpl.truncate();

        log.info("-------------------------- FindByFields Benchmark - Spring Data Aerospike ---------------------------");
        log.info("----------------- Adding " + batchSize + " users (In parallel) -----------------");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceImpl.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        log.info("-------------------------- Find By Age LT ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");

        List<User> result = userServiceImpl.findByAgeLessThan(50);
        log.info("Result size: " + result.size() + "\n");

        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedMillis = Duration.between(start, end).toMillis();
        log.info("Total execution time in milliseconds:\n" + elapsedMillis + "\n");

        log.info("-------------------------- Find By Email Equals ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");

        result = userServiceImpl.findByEmail("username111@gmail.com");
        log.info("Result size: " + result.size() + "\n");

        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedMillis = Duration.between(start, end).toMillis();
        log.info("Total execution time in milliseconds:\n" + elapsedMillis + "\n");

        log.info("-------------------------- Find By Email Starts With ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("\nStart time:\n" + start + "\n");

        // String secondary index does not support "starts with" queries, so it results in a scan
        result = userServiceImpl.findByEmailStartsWith("username111");
        log.info("Result size: " + result.size() + "\n");

        end = LocalDateTime.now().toLocalTime();
        log.info("End time:\n" + end + "\n");
        elapsedMillis = Duration.between(start, end).toMillis();
        log.info("Total execution time in milliseconds:\n" + elapsedMillis + "\n");
    }
}