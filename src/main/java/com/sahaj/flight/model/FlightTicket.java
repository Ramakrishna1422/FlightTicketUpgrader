package com.sahaj.flight.model;

import com.opencsv.bean.CsvBindByPosition;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightTicket {

    @CsvBindByPosition(position = 0)
    private String firstName;

    @CsvBindByPosition(position = 1)
    private String lastName;

    @CsvBindByPosition(position = 2)
    private String pnr;

    @CsvBindByPosition(position = 3)
    private String fareClass;

    @CsvBindByPosition(position = 4)
    private String travelDate;

    @CsvBindByPosition(position = 5)
    private String pax;

    @CsvBindByPosition(position = 6)
    private String ticketingDate;

    @CsvBindByPosition(position = 7)
    private String email;

    @CsvBindByPosition(position = 8)
    private String mobile;

    @CsvBindByPosition(position = 9)
    private String bookedCabin;
}
