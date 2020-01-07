package com.sahaj.flight.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class DiscountController {

    @Autowired
    CSVUtil csvUtil;

    @Autowired
    ResourceLoader resourceLoader;

    @PostMapping("discounts")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        List<FlightTicket> records = null;
        List<ErrorFlightTicket> errorFlightTicketList = null;
        List<DiscountFlightTicket> discountFlightTicketList = null;
        try {
            records = csvUtil.getRecords(file.getInputStream(), FlightTicket.class);

            if(records.size() <= 0) {
                return ResponseEntity.ok("No records");
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
                String error = Validator.getInvalidMessage(flightTicket);
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
            return ResponseEntity.ok("Successfully processed records: " + (discountFlightTicketList.size() - 1) + " Errors : " + (errorFlightTicketList.size()- 1));
        } catch (IOException e) {
            log.error("Error @ IO", e);
            return ResponseEntity.ok("Invalid file");
        } catch (Exception e) {
            return ResponseEntity.ok("Unable to process request");
        }
    }



}
