package org.cubain.objects;

public class NumericalSetting {
    public final int min;
    public final int max;
    public final int step;

    public NumericalSetting(int min, int max, int step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }
}
