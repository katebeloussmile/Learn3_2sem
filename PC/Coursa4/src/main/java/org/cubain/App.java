package org.cubain;

import org.cubain.analyzers.TextAnalyzer;
import org.cubain.objects.IndexEntry;
import org.cubain.readers.FileTextReader;
import org.cubain.tokensources.ITokenSource;
import org.cubain.tokensources.TokenSource;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ITokenSource source = new TokenSource("src/main/resources", new TextAnalyzer(new FileTextReader()), 3, 2000);
        source.getTokens();
        System.out.println("Index is ready");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("enter a term to search for: ");
            String term = scanner.nextLine();
            try {
                IndexEntry entry = source.getEntry(term);
                System.out.printf("Found a match: %s%n", entry.toString());
            } catch (Exception e) {
                System.out.println("No entries found, please try another term");
            }
        }
    }
}
