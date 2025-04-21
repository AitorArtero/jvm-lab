package com.example.jvmlab.service;

import org.springframework.stereotype.Service;

@Service
public class CPULoadService {

    public long performCPUIntensiveTask(int complexity) {
        // Cálculo de números primos hasta el límite especificado
        long count = 0;
        for (int i = 2; i <= complexity; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
}
