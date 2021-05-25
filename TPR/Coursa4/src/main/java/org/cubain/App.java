package org.cubain;

import org.cubain.analyzers.TextAnalyzer;
import org.cubain.readers.FileTextReader;
import org.cubain.tokensources.TokenSource;

import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        var source = new TokenSource("src/main/resources", new TextAnalyzer(new FileTextReader()), 1);
        var map = source.getTokens();
    }
}
