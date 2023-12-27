package com.aerospike.demo.simplespringbootaerospikedemo.controllers;

import com.aerospike.demo.simplespringbootaerospikedemo.services.BenchmarkServiceSpringData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BenchmarkController {

    BenchmarkServiceSpringData benchmarkServiceSpringData;

    @PostMapping("/runBenchmark")
    public void runBenchmark(@RequestParam int batchSize) {
        benchmarkServiceSpringData.runBenchmark(batchSize);
    }
}
