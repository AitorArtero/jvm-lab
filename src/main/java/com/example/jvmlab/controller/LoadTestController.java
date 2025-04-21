package com.example.jvmlab.controller;

import com.example.jvmlab.service.CPULoadService;
import com.example.jvmlab.service.MemoryLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoadTestController {

    private final CPULoadService cpuLoadService;
    private final MemoryLoadService memoryLoadService;

    @GetMapping("/cpu-intensive")
    public ResponseEntity<String> cpuIntensiveTask(
            @RequestParam(defaultValue = "1000000") int complexity) {
        long result = cpuLoadService.performCPUIntensiveTask(complexity);
        return ResponseEntity.ok("CPU task completed. Result: " + result);
    }

    @GetMapping("/memory-intensive")
    public ResponseEntity<String> memoryIntensiveTask(
            @RequestParam(defaultValue = "1000000") int dataSize) {
        long memoryUsed = memoryLoadService.performMemoryIntensiveTask(dataSize);
        return ResponseEntity.ok("Memory task completed. Memory used: " + memoryUsed + " bytes");
    }

    @GetMapping("/async-task")
    public CompletableFuture<String> asyncTask(
            @RequestParam(defaultValue = "5") int delaySeconds) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delaySeconds * 1000);
                return "Async task completed after " + delaySeconds + " seconds";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Task interrupted";
            }
        });
    }
}
