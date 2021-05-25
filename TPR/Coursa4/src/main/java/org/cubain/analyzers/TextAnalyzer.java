package org.cubain.analyzers;

import org.cubain.objects.TextToken;
import org.cubain.readers.ITextReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalyzer implements ITextAnalyzer {
    private final ITextReader textReader;

    public TextAnalyzer(ITextReader textReader) {
        this.textReader = textReader;
    }

    @Override
    public Map<TextToken, List<String>> analyze(List<String> files) {
        Map<TextToken, List<String>> occurrences = new HashMap<>();
        for (String file : files) {
            String text = String.join("", textReader.readAllLines(file));
            List<TextToken> words = tokenize(text);
            for (TextToken word : words) {
                if (occurrences.containsKey(word)) {
                    occurrences.get(word).add(file);
                } else {
                    occurrences.put(word, new ArrayList<>(Collections.singletonList(file)));
                }
            }
        }
        return occurrences;
    }

    private List<TextToken> tokenize(String text) {
        String regex = "(?=\\S*['-]*)([a-zA-Z'-]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        Map<TextToken, Object> tokens = new HashMap<>();
        while (matcher.find()) {
            TextToken token = new TextToken(matcher.group());
            if (tokens.containsKey(token))
                continue;
            tokens.put(token, null);
        }
        return tokens.keySet().stream().toList();
    }
}
