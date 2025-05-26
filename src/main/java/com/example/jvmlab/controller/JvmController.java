// src/main/java/com/example/jvmlab/controller/JvmController.java
package com.example.jvmlab.controller;

import com.sun.management.HotSpotDiagnosticMXBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@RestController
@RequestMapping("/jvm")
public class JvmController {

    private final HotSpotDiagnosticMXBean hotspotMBean;
    private final MemoryMXBean memoryMBean;

    public JvmController() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        hotspotMBean = ManagementFactory.newPlatformMXBeanProxy(
            server,
            "com.sun.management:type=HotSpotDiagnostic",
            HotSpotDiagnosticMXBean.class
        );
        memoryMBean = ManagementFactory.getMemoryMXBean();
    }

    @PostMapping("/setFlag")
    public ResponseEntity<String> setFlag(@RequestParam String name, @RequestParam String value) {
        hotspotMBean.setVMOption(name, value);
        return ResponseEntity.ok("Flag set: " + name + " = " + value);
    }

    @PostMapping("/gc")
    public ResponseEntity<String> runGc() {
        memoryMBean.gc();
        return ResponseEntity.ok("GC triggered");
    }
}
