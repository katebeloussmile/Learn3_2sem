package org.cubain.analyzers;

import org.cubain.objects.TextToken;

import java.util.List;
import java.util.Map;

public interface ITextAnalyzer {
    Map<TextToken, List<String>> analyze(List<String> files);
    void setFilesCount(Integer nFiles);
    int getFilesProcessed();
}
