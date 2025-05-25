package com.example.jvmlab.metrics;

import com.sun.management.HotSpotDiagnosticMXBean;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.List;

@Component            // se detecta automáticamente
public class VmFlagBinder implements MeterBinder {

    private static final List<String> FLAGS = List.of(
            "MinHeapFreeRatio",
            "MaxHeapFreeRatio",
            "InitiatingHeapOccupancyPercent",
            "SoftMaxHeapSize"
    );

    @Override
    public void bindTo(MeterRegistry registry) {
        HotSpotDiagnosticMXBean mx =
                ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);

        FLAGS.forEach(flag ->
                Gauge.builder("jvm_flag_value",
                              () -> Double.parseDouble(mx.getVMOption(flag).getValue()))
                     .description("Current value of JVM flag " + flag)
                     .tag("flag", flag)          // → jvm_flag_value{flag="MinHeapFreeRatio"} …
                     .register(registry));
    }
}
