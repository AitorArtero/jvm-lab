package com.example.jvmlab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CPULoadService {
    private static final Logger logger = LoggerFactory.getLogger(CPULoadService.class);

    public long performCPUIntensiveTask(int complexity) {
        long result = 0;
        for (int i = 0; i < complexity; i++) {
            result += Math.sqrt(i);
        }
        return result;
    }

    public void logExecution(int complexity, long durationMs) {
        logger.info("CPU Task executed with complexity={} in {} ms", complexity, durationMs);
    }
}