package org.cubain;

import org.cubain.analyzers.TextAnalyzer;
import org.cubain.readers.FileTextReader;
import org.cubain.tokensources.TokenSource;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        var source = new TokenSource("src/main/resources", new TextAnalyzer(new FileTextReader()), 1, null);
        for (int i = 5000; i < 10000; i += 2000) {
            for (int j = 1; j < 10; j++) {
                source.setFilesCount(i);
                source.setThreadsCount(j);
                var start = System.nanoTime();
                source.getTokens();
                var stop = System.nanoTime();
                var runtime = (stop - start) / 1000000;
                System.out.println(" threads: " + j + " files: " + i + " time: " + runtime);
            }
        }
    }
}
