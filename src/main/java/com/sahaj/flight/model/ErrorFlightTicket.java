package com.sahaj.flight.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorFlightTicket extends FlightTicket {
    @CsvBindByPosition(position = 10, required = false)
    private String error;
}
