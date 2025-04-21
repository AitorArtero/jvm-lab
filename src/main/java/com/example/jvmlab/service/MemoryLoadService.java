package com.example.jvmlab.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryLoadService {

    private List<byte[]> memoryLeakSimulator = new ArrayList<>();

    public long performMemoryIntensiveTask(int dataSize) {
        // Crear un array de bytes del tamaño especificado
        byte[] data = new byte[dataSize];

        // Simular una fuga de memoria controlada
        memoryLeakSimulator.add(data);

        // Calcular memoria usada
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();

        // Si la lista crece demasiado, limpiarla
        if (memoryLeakSimulator.size() > 10) {
            memoryLeakSimulator.clear();
            System.gc(); // Sugerir GC (solo para propósitos de demostración)
        }

        return usedMemory;
    }
}
