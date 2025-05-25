package com.example.jvmlab.controller;

import com.example.jvmlab.service.CPULoadService;
import com.example.jvmlab.service.MemoryLoadService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class LoadTestController {

    private final CPULoadService cpuLoadService;
    private final MemoryLoadService memoryLoadService;
    private final Executor taskExecutor;

    public LoadTestController(CPULoadService cpuLoadService,
                              MemoryLoadService memoryLoadService,
                              @Qualifier("taskExecutor") Executor taskExecutor) {
        this.cpuLoadService = cpuLoadService;
        this.memoryLoadService = memoryLoadService;
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/cpu-intensive")
    public ResponseEntity<String> cpuIntensiveTask(
            @RequestParam(required = false) Integer complexity) {
        int comp = (complexity != null)
                ? complexity
                : ThreadLocalRandom.current().nextInt(10000, 30001);
        long start = System.currentTimeMillis();
        long result = cpuLoadService.performCPUIntensiveTask(comp);
        long duration = System.currentTimeMillis() - start;
        cpuLoadService.logExecution(comp, duration);
        return ResponseEntity.ok(
                "CPU task completed. Complexity: " + comp +
                ". Result: " + result +
                ". Duration: " + duration + " ms");
    }

    @GetMapping("/memory-intensive")
    public ResponseEntity<String> memoryIntensiveTask(
            @RequestParam(required = false) Integer dataSize) {
        int size = (dataSize != null)
                ? dataSize
                : ThreadLocalRandom.current().nextInt(5 * 1024 * 1024, 25 * 1024 * 1024 + 1);
        long start = System.currentTimeMillis();
        long memoryUsed = memoryLoadService.performMemoryIntensiveTask(size);
        long duration = System.currentTimeMillis() - start;
        memoryLoadService.logExecution(size, duration);
        return ResponseEntity.ok(
                "Memory task completed. DataSize: " + size + " bytes." +
                " Memory used: " + memoryUsed + " bytes." +
                " Duration: " + duration + " ms");
    }

    @GetMapping("/async-task")
    public CompletableFuture<String> asyncTask(
            @RequestParam(defaultValue = "1.0") double delaySeconds) {
        long delayMs = (long) (delaySeconds * 1000);
        long start = System.currentTimeMillis();
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delayMs);
                long duration = System.currentTimeMillis() - start;
                return "Async task completed after " + delaySeconds + " s. Duration: " + duration + " ms";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Task interrupted";
            }
        }, taskExecutor);
    }
}