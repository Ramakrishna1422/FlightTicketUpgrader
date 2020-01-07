package com.sahaj.flight;

import com.sahaj.flight.model.FlightTicket;
import com.sahaj.flight.util.CSVUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class )
@SpringBootTest
public class CSVUtilTest {

    @Autowired
    CSVUtil csvUtil;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    public void recordsTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("input.csv");
        List<FlightTicket> records = csvUtil.getRecords(stream, FlightTicket.class);
        Assert.assertEquals(6, records.size());
    }

    @Test
    public void writeFileTest() throws Exception {
        File processedFile = new File(resourceLoader.getResource("classpath:.").getURI().getPath() + "test_tickets.csv");
        String filePath = csvUtil.writeRecords(processedFile, new ArrayList<FlightTicket>());
        File outFile = new File(filePath);
        assertThat(outFile.exists());
    }
}
