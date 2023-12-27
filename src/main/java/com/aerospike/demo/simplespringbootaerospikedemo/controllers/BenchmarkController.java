package com.aerospike.demo.simplespringbootaerospikedemo.controllers;

import com.aerospike.demo.simplespringbootaerospikedemo.services.BenchmarkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BenchmarkController {

    BenchmarkService benchmarkService;

    @PostMapping("/runBenchmark")
    public void runBenchmark() {
        benchmarkService.runBenchmark();
    }
}
