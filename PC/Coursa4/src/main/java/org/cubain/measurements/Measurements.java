package org.cubain.measurements;

import org.cubain.analyzers.TextAnalyzer;
import org.cubain.measurements.csv.CsvWriter;
import org.cubain.measurements.csv.ICsvWriter;
import org.cubain.objects.MeasurementResult;
import org.cubain.objects.NumericalSetting;
import org.cubain.readers.FileTextReader;
import org.cubain.tokensources.TokenSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Measurements {
    private static final Object lock = new Object();
    public static void main(String[] args) {
        NumericalSetting files = MeasurementSettings.fileSetting;
        NumericalSetting threads = MeasurementSettings.threadSetting;
        List<Thread> testThreads = new ArrayList<>();
        ICsvWriter csvWriter = new CsvWriter("src/main/resources/measurements");
        try {
            csvWriter.truncateAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = files.min; i <= files.max; i += files.step) {
            int fileCount = i;
            Thread testThread = new Thread(() -> {
                for (int j = threads.min; j <= threads.max; j += threads.step) {
                    TokenSource source = getTokenSource();
                    MeasurementResult testResult = getMeasurement(fileCount, j, source);
                    synchronized (lock) {
                        try {
                            csvWriter.persistToCsv(testResult);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            testThreads.add(testThread);
            testThread.start();
        }
        for(var testThread: testThreads){
            try {
                testThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Time measuring is finished");
    }

    private static MeasurementResult getMeasurement(int filesCount, int threadsCount, TokenSource tokenSource) {
        tokenSource.setFilesCount(filesCount);
        tokenSource.setThreadsCount(threadsCount);
        long start = System.currentTimeMillis();
        tokenSource.getTokens();
        long stop = System.currentTimeMillis();
        long runtime = stop - start;
        return new MeasurementResult(filesCount, threadsCount, runtime);
    }

    private static TokenSource getTokenSource(){
        return new TokenSource(MeasurementSettings.Directory, new TextAnalyzer(new FileTextReader()));
    }

}