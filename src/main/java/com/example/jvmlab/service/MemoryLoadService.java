package com.example.jvmlab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryLoadService {
    private static final Logger logger = LoggerFactory.getLogger(MemoryLoadService.class);

    public long performMemoryIntensiveTask(int dataSize) {
        List<byte[]> buffers = new ArrayList<>();
        int chunkSize = 1024 * 1024; // 1MB por chunk
        int allocated = 0;
        while (allocated < dataSize) {
            int size = Math.min(chunkSize, dataSize - allocated);
            buffers.add(new byte[size]);
            allocated += size;
        }
        try {
            // simulamos retención breve
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return allocated;
    }

    public void logExecution(int dataSize, long durationMs) {
        logger.info("Memory Task executed with dataSize={} in {} ms", dataSize, durationMs);
    }
}
