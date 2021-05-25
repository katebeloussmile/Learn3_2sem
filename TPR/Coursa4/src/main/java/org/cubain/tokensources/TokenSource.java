package org.cubain.tokensources;

import org.cubain.analyzers.ITextAnalyzer;
import org.cubain.objects.TextToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TokenSource implements ITokenSource {
    private String directory;
    private int nThreads;
    private final ITextAnalyzer textAnalyzer;

    public TokenSource(String directory, ITextAnalyzer textAnalyzer) {
        this(directory, textAnalyzer, 1);
    }

    public TokenSource(String directory, ITextAnalyzer textAnalyzer, int nThreads) {
        this.directory = directory;
        this.textAnalyzer = textAnalyzer;
        this.nThreads = nThreads;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setThreads(int threads) {
        this.nThreads = threads;
    }

    @Override
    public Map<TextToken, List<String>> getTokens() {
        List<String> files = Collections.synchronizedList(new ArrayList<>());
        traverseDirectories(directory, files);

        int fileCount = files.size();
        long pageSize = fileCount / nThreads;
        long pageCount = fileCount / pageSize;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        CountDownLatch latch = new CountDownLatch((int) pageCount);
        ConcurrentHashMap<TextToken, List<String>> index = new ConcurrentHashMap<>();

        for (long i = 0; i < pageCount; i++) {
            List<String> paths = files.stream().skip(i * pageSize).limit(pageSize).toList();
            executorService.submit(() -> {
                Map<TextToken, List<String>> partialIndex = textAnalyzer.analyze(paths);
                partialIndex.forEach((token, locations) -> {
                    if (index.containsKey(token)) {
                        index.get(token).addAll(locations);
                    } else {
                        index.put(token, locations);
                    }
                });
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return index;
    }

    private void traverseDirectories(String root, List<String> filenames) {
        File directory = new File(root);
        List<Thread> threads = new ArrayList<>();

        if (directory.isFile()) {
            filenames.add(directory.getPath());
            return;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0)
            return;

        for (File file : files) {
            if (file.isFile()) {
                filenames.add(file.getPath());
            } else {
                Thread traverseThread = new Thread(() -> traverseDirectories(file.getPath(), filenames));
                threads.add(traverseThread);
                traverseThread.start();
            }
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
