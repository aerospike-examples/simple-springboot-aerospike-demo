package com.aerospike.demo.simplespringbootaerospikedemo.controllers;

import com.aerospike.demo.simplespringbootaerospikedemo.services.BenchmarkServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BenchmarkController {

    BenchmarkServiceImpl benchmarkServiceImpl;

    @PostMapping("/runBenchmark")
    public void runBenchmark() {
        benchmarkServiceImpl.runBenchmark();
    }
}
