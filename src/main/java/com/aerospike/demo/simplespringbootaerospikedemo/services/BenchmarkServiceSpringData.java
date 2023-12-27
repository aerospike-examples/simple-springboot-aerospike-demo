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
public class BenchmarkServiceSpringData implements BenchmarkService {

    UserServiceSpringData userServiceSpringData;

    @Override
    public void runBenchmark(int batchSize) {
        runCrudOpsBenchmark(batchSize);
        runFindByFieldsBenchmark(batchSize);
    }

    @Override
    public void runCrudOpsBenchmark(int batchSize) {
        LocalTime start;
        LocalTime end;
        long elapsedSeconds;
        userServiceSpringData.truncate();

        log.info("-------------------------- CRUD Benchmark - Spring Data Aerospike ---------------------------");
        log.info("----------------- Add users (Parallel) -----------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Parallel start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceSpringData.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("Parallel end time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Read users (Parallel) ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i -> userServiceSpringData.readUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Update users (Parallel) ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceSpringData.addOrUpdateUser(new User(i, "username" + i, "usernameNew" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);

        log.info("-------------------------- Delete users by id (Parallel) ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);
        IntStream.range(0, batchSize).parallel().forEach(i -> userServiceSpringData.removeUserById(i));
        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedSeconds = Duration.between(start, end).toSeconds();
        log.info("Total execution time in seconds: " + elapsedSeconds);
    }

    @Override
    public void runFindByFieldsBenchmark(int batchSize) {
        LocalTime start;
        LocalTime end;
        long elapsedMillis;
        userServiceSpringData.truncate();

        log.info("-------------------------- FindByFields Benchmark - Spring Data Aerospike ---------------------------");
        log.info("----------------- Adding " + batchSize + " users (In parallel) -----------------");
        IntStream.range(0, batchSize).parallel().forEach(i ->
                userServiceSpringData.addOrUpdateUser(new User(i, "username" + i, "username" + i + "@gmail.com",
                        (int) (Math.random() * 81 + 20), new byte[]{1, 2, 3, 4, 5, 6})));

        log.info("-------------------------- Find By Age LT ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);

        List<User> result = userServiceSpringData.findByAgeLessThan(50);
        log.info("Result size: " + result.size());

        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedMillis = Duration.between(start, end).toMillis();
        log.info("Total execution time in milliseconds: " + elapsedMillis);

        log.info("-------------------------- Find By Email Equals ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);

        result = userServiceSpringData.findByEmail("username111@gmail.com");
        log.info("Result size: " + result.size());

        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedMillis = Duration.between(start, end).toMillis();
        log.info("Total execution time in milliseconds: " + elapsedMillis);

        log.info("-------------------------- Find By Email Starts With ---------------------------");
        start = LocalDateTime.now().toLocalTime();
        log.info("Start time: " + start);

        // String secondary index does not support "starts with" queries, so it results in a scan
        result = userServiceSpringData.findByEmailStartsWith("username111");
        log.info("Result size: " + result.size());

        end = LocalDateTime.now().toLocalTime();
        log.info("End time: " + end);
        elapsedMillis = Duration.between(start, end).toMillis();
        log.info("Total execution time in milliseconds: " + elapsedMillis);
    }
}
