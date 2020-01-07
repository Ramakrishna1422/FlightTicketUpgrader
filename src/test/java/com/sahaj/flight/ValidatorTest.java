package com.sahaj.flight;


import com.sahaj.flight.model.FlightTicket;
import com.sahaj.flight.util.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class )
@SpringBootTest
public class ValidatorTest {

    @Test
    public void getInvalidMessageTest() throws Exception {
        Assert.assertEquals(true, Validator.isValidEmail("abc@gmail.com"));
        Assert.assertEquals(false, Validator.isValidEmail("abcaxyz"));

        Assert.assertEquals(false, Validator.isValidMobile("90909090"));
        Assert.assertEquals(true, Validator.isValidMobile("9743016797"));

        Assert.assertEquals(true, Validator.isValidBookingDates("2019-12-01", "2020-02-02"));
        Assert.assertEquals(false, Validator.isValidBookingDates("2020-12-01", "2020-02-02"));
        Assert.assertEquals(false, Validator.isValidBookingDates("0000", "2020-02-02"));

        Assert.assertEquals(false, Validator.isValidPNR("9ABC"));
        Assert.assertEquals(true, Validator.isValidPNR("A1B2CB"));

        FlightTicket flightTicket = new FlightTicket();
        Assert.assertEquals("Email Invalid", Validator.getInvalidMessage(flightTicket));

        flightTicket.setEmail("ramakrishna.koalapti@gmail.com");
        Assert.assertEquals("Mobile Invalid", Validator.getInvalidMessage(flightTicket));

        flightTicket.setMobile("9743016797");
        Assert.assertEquals("PNR Invalid", Validator.getInvalidMessage(flightTicket));
    }
}
