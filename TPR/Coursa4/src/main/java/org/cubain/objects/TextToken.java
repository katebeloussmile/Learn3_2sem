package org.cubain.objects;

import java.util.Objects;

public class TextToken {
    private final String value;

    public TextToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextToken textToken = (TextToken) o;
        return Objects.equals(value, textToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString(){
        return this.value;
    }
}
