package org.cubain;

import org.cubain.analyzers.ITextAnalyzer;
import org.cubain.analyzers.TextAnalyzer;
import org.cubain.objects.TextToken;
import org.cubain.readers.FileTextReader;
import org.cubain.readers.ITextReader;
import org.cubain.tokensources.ITokenSource;
import org.cubain.tokensources.TokenSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Index Tests")
public class IndexTests {
    private final String testDirectory = "src/test/resources";
    private final Map<TextToken, List<String>> validIndex =
            Map.of(
                    new TextToken("a"), List.of("1.txt", "2.txt"),
                    new TextToken("b"), Collections.singletonList("2.txt"),
                    new TextToken("c"), Collections.singletonList("3.txt"),
                    new TextToken("d"), Collections.singletonList("4.txt")
            );
    private ITokenSource tokenSource;

    private ITokenSource getTokenSource(int numThreads) {
        ITextReader reader = new FileTextReader();
        ITextAnalyzer analyzer = new TextAnalyzer(reader);
        return new TokenSource(testDirectory, analyzer, numThreads, null);
    }

    @Test
    @DisplayName("Single threaded test")
    public void singleThreaded() {
        tokenSource = getTokenSource(1);
        Map<TextToken, List<String>> index = tokenSource.getTokens();
        index.entrySet().forEach((pair) -> {
            List<String> expected = validIndex.get(pair.getKey());
            List<String> actual = pair.getValue();
            expected.forEach(s -> {
                assertTrue(actual.contains(s));
            });
        });
    }

    @Test
    @DisplayName("Multi threaded test")
    public void multiThreaded(){
        tokenSource = getTokenSource(3);
        Map<TextToken, List<String>> index = tokenSource.getTokens();
        index.entrySet().forEach((pair) -> {
            List<String> expected = validIndex.get(pair.getKey());
            List<String> actual = pair.getValue();
            expected.forEach(s -> {
                assertTrue(actual.contains(s));
            });
        });
    }

}
