package com.aerospike.demo.simplespringbootaerospikedemo;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.demo.simplespringbootaerospikedemo.objects.User;
import com.aerospike.demo.simplespringbootaerospikedemo.objects.UserMongo;
import com.aerospike.demo.simplespringbootaerospikedemo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;

@SpringBootTest
class SimpleSpringbootAerospikeDemoApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	AerospikeClient client;

    @Test
    void contextLoads() {
    	int batchSize = 20000;

    	String jsonDataExample = "{\n" +
				"  \"authentication\": {\n" +
				"    \"login\": [\n" +
				"      {\n" +
				"        \"id\": 21,\n" +
				"        \"name\": \"Simba Lion\",\n" +
				"        \"location\": \"Italy\",\n" +
				"        \"date\": \"2.7.2021\",\n" +
				"        \"device\": \"Mobile\",\n" +
				"        \"os\": \"iOS\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"id\": 22,\n" +
				"        \"name\": \"Sean Cahill\",\n" +
				"        \"location\": \"US\",\n" +
				"        \"date\": \"2.7.2021\",\n" +
				"        \"device\": \"Mobile\",\n" +
				"        \"os\": \"Android\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"id\": 23,\n" +
				"        \"name\": \"Forrest Gump\",\n" +
				"        \"location\": \"Spain\",\n" +
				"        \"date\": \"2.7.2021\",\n" +
				"        \"device\": \"Computer\",\n" +
				"        \"os\": \"Windows\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"id\": 24,\n" +
				"        \"name\": \"Patrick St. Claire\",\n" +
				"        \"location\": \"France\",\n" +
				"        \"date\": \"2.7.2021\",\n" +
				"        \"device\": \"Mobile\",\n" +
				"        \"os\": \"iOS\"\n" +
				"      }\n" +
				"    ],\n" +
				"    \"logout\": {\n" +
				"      \"name\": \"John Snow\",\n" +
				"      \"datetime\": \"2.7.2021\",\n" +
				"      \"device\": \"Mobile\",\n" +
				"      \"ref\": [1,2,3]\n" +
				"    }\n" +
				"  },\n" +
				"  \"like\": 20\n" +
				"}";

		userService.truncateMongo();
		System.out.println("\n-------------------------- Mongo Spring Test ---------------------------");
		LocalTime start = LocalDateTime.now().toLocalTime();
		System.out.println("\nStart time:\n" + start + "\n");
		for (int i = 0; i < batchSize; i++) {
			userService.addUserMongo(new UserMongo(i, "username" + i, "test@mail.com", 50, jsonDataExample.getBytes(StandardCharsets.UTF_8)));
			userService.readUserMongoById(i);
		}
		LocalTime end = LocalDateTime.now().toLocalTime();
		System.out.println("End time:\n" + end + "\n");
		long elapsedMinutes = Duration.between(start, end).toSeconds();
		System.out.println("Total execution time:\n" + elapsedMinutes + "\n");

		userService.truncateMongo();
		System.out.println("----------------- Parallel Test -----------------");

		start = LocalDateTime.now().toLocalTime();
		System.out.println("\nParallel start time:\n" + start + "\n");
		IntStream.range(0, batchSize).parallel().forEach(i -> {
			userService.addUserMongo(new UserMongo(i, "username" + i, "test@mail.com", 50, jsonDataExample.getBytes(StandardCharsets.UTF_8)));
			userService.readUserMongoById(i);
		});
		end = LocalDateTime.now().toLocalTime();
		System.out.println("Parallel end time:\n" + end + "\n");
		elapsedMinutes = Duration.between(start, end).toSeconds();
		System.out.println("Total execution time:\n" + elapsedMinutes + "\n");

		client.truncate(null, "test", null, null);
    	System.out.println("-------------------------- Aerospike Spring Test ---------------------------");
    	start = LocalDateTime.now().toLocalTime();
    	System.out.println("\nStart time:\n" + start + "\n");
        for (int i = 0; i < batchSize; i++) {
			userService.addUser(new User(i, "username" + i, "test@mail.com", 50, jsonDataExample.getBytes(StandardCharsets.UTF_8)));
			userService.readUserById(i);
        }
		end = LocalDateTime.now().toLocalTime();
		System.out.println("End time:\n" + end + "\n");
		elapsedMinutes = Duration.between(start, end).toSeconds();
		System.out.println("Total execution time:\n" + elapsedMinutes + "\n");

		client.truncate(null, "test", null, null);
		System.out.println("----------------- Parallel Test -----------------");

		start = LocalDateTime.now().toLocalTime();
		System.out.println("\nParallel start time:\n" + start + "\n");
		IntStream.range(0, batchSize).parallel().forEach(i -> {
			userService.addUser(new User(i, "username" + i, "test@mail.com", 50, jsonDataExample.getBytes(StandardCharsets.UTF_8)));
			userService.readUserById(i);
		});
		end = LocalDateTime.now().toLocalTime();
		System.out.println("Parallel end time:\n" + end + "\n");
		elapsedMinutes = Duration.between(start, end).toSeconds();
		System.out.println("Total execution time:\n" + elapsedMinutes + "\n");

		client.truncate(null, "test", null, null);
		System.out.println("--------------------- Aerospike Java Client Test ---------------------");

		start = LocalDateTime.now().toLocalTime();
		System.out.println("\nStart time:\n" + start + "\n");
		for (int i = 0; i < batchSize; i++) {
			client.put(null, new Key("test", null, i), new Bin("email", "test@mail.com"), new Bin("age", 50), new Bin("data", jsonDataExample.getBytes(StandardCharsets.UTF_8)));
			client.get(null, new Key("test", null, i));
		}
		end = LocalDateTime.now().toLocalTime();
		System.out.println("End time:\n" + end + "\n");
		elapsedMinutes = Duration.between(start, end).toSeconds();
		System.out.println("Total execution time:\n" + elapsedMinutes + "\n");

		client.truncate(null, "test", null, null);
		System.out.println("----------------- Parallel Test -----------------");

		start = LocalDateTime.now().toLocalTime();
		System.out.println("\nParallel start time:\n" + start + "\n");
		IntStream.range(0, batchSize).parallel().forEach(i -> {
			client.put(null, new Key("test", null, i), new Bin("email", "test@mail.com"), new Bin("age", 50), new Bin("data", jsonDataExample.getBytes(StandardCharsets.UTF_8)));
			client.get(null, new Key("test", null, i));
		});
		end = LocalDateTime.now().toLocalTime();
		System.out.println("Parallel end time:\n" + end + "\n");
		elapsedMinutes = Duration.between(start, end).toSeconds();
		System.out.println("Total execution time:\n" + elapsedMinutes + "\n");
    }
}
