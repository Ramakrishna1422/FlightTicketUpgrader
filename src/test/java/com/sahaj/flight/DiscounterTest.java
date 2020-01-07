package com.sahaj.flight;

import com.sahaj.flight.util.Discounter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class )
@SpringBootTest
public class DiscounterTest {

    @Test
    public void getInvalidMessageTest() throws Exception {


        Assert.assertEquals(false, Discounter.getApplicableDiscountCode("T").equals("OFFER_25"));
        Assert.assertEquals(false, Discounter.getApplicableDiscountCode("E").equals("OFFER_25"));
        Assert.assertEquals(true, Discounter.getApplicableDiscountCode("S").equals(""));
        Assert.assertEquals(true, Discounter.getApplicableDiscountCode("T").equals(""));

        Assert.assertEquals(false, Discounter.getApplicableDiscountCode("I").equals("OFFER_25"));
        Assert.assertEquals(true, Discounter.getApplicableDiscountCode("K").equals("OFFER_30"));
        Assert.assertEquals(true, Discounter.getApplicableDiscountCode("M").equals("OFFER_25"));
        Assert.assertEquals(true, Discounter.getApplicableDiscountCode("A").equals("OFFER_20"));
        Assert.assertEquals(true, Discounter.getApplicableDiscountCode("R").equals("OFFER_25"));

    }
}
