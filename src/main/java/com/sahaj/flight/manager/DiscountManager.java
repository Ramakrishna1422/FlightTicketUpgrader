package com.sahaj.flight.manager;

import com.sahaj.flight.exceptions.InvalidFileException;
import com.sahaj.flight.exceptions.NoDataFoundException;
import com.sahaj.flight.model.DiscountFlightTicket;
import com.sahaj.flight.model.ErrorFlightTicket;
import com.sahaj.flight.model.FlightTicket;
import com.sahaj.flight.util.CSVUtil;
import com.sahaj.flight.util.Discounter;
import com.sahaj.flight.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DiscountManager {

    @Autowired
    CSVUtil csvUtil;

    @Autowired
    ResourceLoader resourceLoader;

    public boolean applyDiscount(MultipartFile file) throws Exception {
        List<FlightTicket> records = null;
        List<ErrorFlightTicket> errorFlightTicketList = null;
        List<DiscountFlightTicket> discountFlightTicketList = null;
        try {
            records = csvUtil.getRecords(file.getInputStream(), FlightTicket.class);

            if(records.size() <= 0) {
                throw new NoDataFoundException();
            }

            errorFlightTicketList = new ArrayList<>();
            discountFlightTicketList = new ArrayList<>();

            //Creating Header Row
            FlightTicket header = records.remove(0);
            DiscountFlightTicket header_discount = new DiscountFlightTicket();
            BeanUtils.copyProperties(header, header_discount);
            header_discount.setDiscountCode("Discount_code");
            discountFlightTicketList.add(header_discount);

            ErrorFlightTicket header_error = new ErrorFlightTicket();
            BeanUtils.copyProperties(header, header_error);
            header_error.setError("Error");
            errorFlightTicketList.add(header_error);

            for(FlightTicket flightTicket : records) {
                String error = Validator.isValidRecord(flightTicket);
                if(Validator.isNullOrEmpty(error)) {
                    DiscountFlightTicket result = new DiscountFlightTicket();
                    BeanUtils.copyProperties(flightTicket, result);
                    result.setDiscountCode(Discounter.getApplicableDiscountCode(flightTicket.getFareClass()));
                    discountFlightTicketList.add(result);
                } else {
                    ErrorFlightTicket result = new ErrorFlightTicket();
                    BeanUtils.copyProperties(flightTicket, result);
                    result.setError(error);
                    errorFlightTicketList.add(result);
                }
            };
            if(errorFlightTicketList.size() > 1) {
                File processedErrorFile = new File(resourceLoader.getResource("classpath:.").getURI().getPath() + "errors_tickets.csv");
                csvUtil.writeRecords(processedErrorFile, errorFlightTicketList);
            }
            File processedFile = new File(resourceLoader.getResource("classpath:.").getURI().getPath() + "processed_tickets.csv");
            csvUtil.writeRecords(processedFile, discountFlightTicketList);
            return true;
        } catch (IOException e) {
            throw new InvalidFileException();
        } catch (Exception e) {
            throw e;
        }
    }
}
