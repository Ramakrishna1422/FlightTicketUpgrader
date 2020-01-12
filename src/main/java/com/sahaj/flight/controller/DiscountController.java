package com.sahaj.flight.controller;

import com.sahaj.flight.manager.DiscountManager;
import com.sahaj.flight.model.DiscountFlightTicket;
import com.sahaj.flight.model.ErrorFlightTicket;
import com.sahaj.flight.model.FlightTicket;
import com.sahaj.flight.util.CSVUtil;
import com.sahaj.flight.util.Discounter;
import com.sahaj.flight.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
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
    DiscountManager discountManager;

    @PostMapping("discounts")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            boolean status = discountManager.applyDiscount(file);
            if(status) {
                return ResponseEntity.status(HttpStatus.OK).body("Sucessfully processed");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to process the request");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }



}
