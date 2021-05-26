package org.cubain.measurements;

import org.cubain.objects.NumericalSetting;

import java.util.Map;

public abstract class MeasurementSettings {
    public static final NumericalSetting fileSetting = new NumericalSetting(5000, 15000, 2000);
    public static final NumericalSetting threadSetting = new NumericalSetting(1,15,1);
    public static final String Directory = "src/main/resources";
}
