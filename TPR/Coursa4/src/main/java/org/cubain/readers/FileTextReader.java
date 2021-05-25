package org.cubain.readers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileTextReader implements ITextReader {
    @Override
    public List<String> readAllLines(String path) {
        File file = new File(path);
        List<String> lines = new ArrayList<>();
        if (!file.exists())
            return new ArrayList<>();
        try {
            return Files.readAllLines(Path.of(file.getAbsolutePath()), Charset.defaultCharset());
        } catch (IOException e) {
            return lines;
        }
    }
}
