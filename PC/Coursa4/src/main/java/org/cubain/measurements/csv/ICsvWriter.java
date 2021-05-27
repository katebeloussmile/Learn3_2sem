package org.cubain.measurements.csv;

import org.cubain.objects.MeasurementResult;

import java.io.IOException;

public interface ICsvWriter {
    public void setBasePath(String path);
    public void persistToCsv(MeasurementResult result) throws IOException;
    public void truncateAll() throws IOException;
}
