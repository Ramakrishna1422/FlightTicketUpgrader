package com.sahaj.flight.util;

import com.sahaj.flight.constants.Cabin;
import com.sahaj.flight.model.FlightTicket;

import java.util.regex.Pattern;

public class Validator {
    private final static String EMAIL_REGIX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isValidEmail(String email) {
        Pattern pat = Pattern.compile(EMAIL_REGIX);
        return !isNullOrEmpty(email) && pat.matcher(email).matches();
    }

    public static boolean isValidMobile(String mobile) {
        return !isNullOrEmpty(mobile) && mobile.trim().length() == 10;
    }

    public static boolean isValidBookingDates(String bookingDate, String travelDates) {
        return !isNullOrEmpty(bookingDate) && !isNullOrEmpty(travelDates) && DateUtil.isBefore(bookingDate, travelDates);
    }

    public static boolean isValidPNR(String PNR) {
        return !isNullOrEmpty(PNR) && PNR.length() == 6 && PNR.matches("^[a-zA-Z0-9]*$");
    }

    public static boolean isValidCabin(String cabin) {
        return !isNullOrEmpty(cabin) && Cabin.cache.contains(cabin);
    }

    public static boolean isNullOrEmpty(String any) {
        return any == null || any.isEmpty();
    }

    public static String isValidRecord(FlightTicket flightTicket) {
        if(!isValidEmail(flightTicket.getEmail())) return "Email Invalid";
        if(!isValidMobile(flightTicket.getMobile())) return "Mobile Invalid";
        if(!isValidPNR(flightTicket.getPnr())) return "PNR Invalid";
        if(!isValidCabin(flightTicket.getBookedCabin())) return "Booking Cabin Invalid";
        if(!isValidBookingDates(flightTicket.getTicketingDate(), flightTicket.getTravelDate())) return "TravelDate Invalid";
        return null;
    }
}
