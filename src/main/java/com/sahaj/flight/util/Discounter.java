package com.sahaj.flight.util;

public class Discounter {
    public static String getApplicableDiscountCode(String fareClass) {
        if(fareClass.matches("[a-eA-E]")) return "OFFER_20";
        if(fareClass.matches("[f-kF-K]")) return "OFFER_30";
        if(fareClass.matches("[l-rL-R]")) return "OFFER_25";
        return "";
    }
}
