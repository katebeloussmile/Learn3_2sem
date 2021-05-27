package org.cubain.objects;

import java.util.List;

public class IndexEntry {
    private final TextToken token;
    private final List<String> entries;

    public IndexEntry(TextToken token, List<String> entries) {
        this.token = token;
        this.entries = entries;
    }

    public TextToken getToken() {
        return token;
    }

    public List<String> getEntries() {
        return entries;
    }

    @Override
    public String toString(){
        return String.format("%s: %s", token.getValue(), String.join(",", entries));
    }
}
