package org.cubain.measurements.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.cubain.objects.MeasurementResult;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

@SuppressWarnings("ALL")
public class CsvWriter implements ICsvWriter {
    private String basePath;
    private final String[] headers = {"threads", "runtime"};

    public CsvWriter(String basePath) {
        this.basePath = basePath;
    }

    public void setBasePath(String path) {
        this.basePath = path;
    }
    public void truncateAll() throws IOException {
        File baseDir = new File(basePath);
        File files[] = baseDir.listFiles();
        if(files == null){
            return;
        }
        for(File file: files){
            if(!file.exists())
                file.createNewFile();
            else{
                try(FileChannel channel = new FileOutputStream(file, true).getChannel()){
                    channel.truncate(0);
                }
            }
        }
    }
    @Override
    public void persistToCsv(MeasurementResult result) throws IOException {
        File baseDir = new File(basePath);
        if(!baseDir.exists()){
            Files.createDirectories(baseDir.toPath());
        }
        File logFile = new File(baseDir, String.format("%d.csv", result.filesCount));
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        FileWriter writer = new FileWriter(logFile, true);
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        CSVPrinter printer = null;
        String line = reader.readLine();
        if(line != null && line.startsWith(headers[0])){
            printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
        }else{
            printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
        }
        printer.printRecord(result.threadsCount, result.millis);
        printer.close();
    }
}
