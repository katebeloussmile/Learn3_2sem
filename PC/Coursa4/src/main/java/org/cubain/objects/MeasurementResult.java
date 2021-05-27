package org.cubain.objects;

public class MeasurementResult {
    public final int filesCount;
    public final int threadsCount;
    public final long millis;

    public MeasurementResult(int filesCount, int threadsCount, long millis) {
        this.filesCount = filesCount;
        this.threadsCount = threadsCount;
        this.millis = millis;
    }
}
