package com.company;

import java.util.ArrayList;

class Stats<T> {
    public ArrayList<Item<T>> Modes;
    public Item<T> Median;

    public Stats(T initialMedian, Integer initialMedianIndex) {
        Modes = new ArrayList<>();
        Median = new Item<>(initialMedian, initialMedianIndex);
    }

    @Override
    public String toString() {
        String s = "";
        s += "Median: " + Median.toString() + "\nModes: " + Modes.toString();
        return s;
    }
}