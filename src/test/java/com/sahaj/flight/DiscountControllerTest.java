package com.sahaj.flight;

import com.sahaj.flight.controller.DiscountController;
import com.sahaj.flight.model.DiscountFlightTicket;
import com.sahaj.flight.model.ErrorFlightTicket;
import com.sahaj.flight.model.FlightTicket;
import com.sahaj.flight.util.CSVUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class )
@SpringBootTest
public class DiscountControllerTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    DiscountController discountController;

    @Autowired
    CSVUtil csvUtil;

    @Test
    public void upload() throws Exception {
        InputStream stream = resourceLoader.getResource("classpath:input.csv").getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", stream);
        String response = discountController.upload(file).getBody();
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("Sucessfully processed");

        //Output files check
        File processedFile = resourceLoader.getResource("classpath:processed_tickets.csv").getFile();
        File errorFile = resourceLoader.getResource("classpath:errors_tickets.csv").getFile();

        assertThat(processedFile.exists());
        assertThat(errorFile.exists());

        List<DiscountFlightTicket> records =  csvUtil.getRecords(new FileInputStream(processedFile), DiscountFlightTicket.class);
        List<ErrorFlightTicket> error_records = csvUtil.getRecords(new FileInputStream(errorFile), ErrorFlightTicket.class);

        Assert.assertEquals(6, records.size());
        Assert.assertEquals(4, error_records.size());
    }


    @Test
    public void uploadNoDataFile() throws Exception {
        InputStream stream = resourceLoader.getResource("classpath:nodatafile.csv").getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", stream);
        String response = discountController.upload(file).getBody();
        assertThat(response).isNotNull();
        assertThat(response).contains("No data found");
    }

}
