package com.aerospike.demo.simplespringbootaerospikedemo.services;

public interface BenchmarkService {

    void runBenchmark(int batchSize);

    void runCrudOpsBenchmark(int batchSize);

    void runFindByFieldsBenchmark(int batchSize);
}
