package com.sahaj.flight.constants;

import java.util.ArrayList;
import java.util.List;

public enum Cabin {
    FIRST("First"),
    BUSINESS("Business"),
    ECONOMY("Economy"),
    PREMIUM_ECONOMY("Premium Economy");

    private String name;

    private Cabin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> cache = new ArrayList<>();

    static {
        for (Cabin entity : Cabin.values()) {
            cache.add(entity.getName());
        }
    }
}
