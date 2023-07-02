package com.javarush.island.didenko;

import com.javarush.island.didenko.inhabitants.Livable;
import lombok.Getter;


import java.util.Map;


public class Statistics {

    Island island;

    public Statistics(Island island) {
        this.island = island;
    }

    private static final String SPACE = " ";
    private static final String DIVIDER = "|";


    void printFullStat() {
        MyHashMap<Livable, Integer> statistics = countStatistics();
        StringBuilder key = new StringBuilder();

        StringBuilder value = new StringBuilder();
        for (Map.Entry entry : statistics.entrySet()) {

            key.append(entry.getKey().getClass().getSimpleName());
            Integer i = statistics.getMaxLength();
            for (int j = entry.getKey().getClass().getSimpleName().length(); j < i; j++) {

                key.append(SPACE);
            }
            key.append(DIVIDER);


            value.append(entry.getValue());

            for (int j = entry.getValue().toString().length(); j < i; j++) {

                value.append(SPACE);
            }
            value.append(DIVIDER);

        }
        System.out.println(key);
        System.out.print(value);
    }

    void printShortStat() {

        MyHashMap<Livable, Integer> statistics = countStatistics();
        StringBuilder key = new StringBuilder();

        StringBuilder value = new StringBuilder();
        for (Map.Entry entry : statistics.entrySet()) {

            Integer i = statistics.getMaxLength();
            value.append(entry.getValue());

            for (int j = entry.getValue().toString().length(); j < i; j++) {
                value.append(SPACE);
            }
            value.append(DIVIDER);
        }
        System.out.print(value);
    }

    public class MyHashMap<K, V> extends java.util.HashMap<K, V> {

        @Getter
        private int maxLength = 0;

        @Override
        public V put(K key, V value) {
            maxLength = Math.max(maxLength, key.getClass().getSimpleName().length());
            maxLength = Math.max(maxLength, value.toString().length());
            super.put(key, value);
            return value;
        }
    }

    private MyHashMap<Livable, Integer> countStatistics() {
        Map<Livable, Integer> statistics = new MyHashMap<Livable, Integer>();

        for (int i = 0; i < island.getWidth(); i++) {
            for (int j = 0; j < island.getLength(); j++) {
                for (Livable typeLive : island.getCells()[i][j].keySet()) {
                    var list = island.getCells()[i][j].get(typeLive);
                    if (!statistics.containsKey(typeLive)) {
                        statistics.put(typeLive, list.size());
                    } else {
                        int newCount = statistics.get(typeLive) + list.size();
                        statistics.put(typeLive, newCount);
                    }
                }

            }
        }
        return (MyHashMap<Livable, Integer>) statistics;
    }
}
