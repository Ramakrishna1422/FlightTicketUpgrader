package com.sahaj.flight;

import com.sahaj.flight.controller.DiscountController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class )
@SpringBootTest
public class DiscountControllerTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    DiscountController discountController;


    @Test
    public void upload() throws Exception {
        InputStream stream = resourceLoader.getResource("classpath:input.csv").getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", stream);
        String response = discountController.upload(file).getBody();
        assertThat(response).isNotNull();
        assertThat(response).contains("Successfully processed");

        File processedFile = resourceLoader.getResource("/tmp/processed_tickets.csv").getFile();
        File errorFile = resourceLoader.getResource("/tmp/errors_tickets.csv").getFile();

        assertThat(processedFile.exists());
        assertThat(errorFile.exists());
    }


    @Test
    public void uploadNoDataFile() throws Exception {
        InputStream stream = resourceLoader.getResource("classpath:nodatafile.csv").getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", stream);
        String response = discountController.upload(file).getBody();
        assertThat(response).isNotNull();
        assertThat(response).contains("No records");
    }

}
