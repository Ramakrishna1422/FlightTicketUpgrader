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
        Assert.assertEquals("OFFER_20", Discounter.getApplicableDiscountCode("E"));
        Assert.assertEquals("", Discounter.getApplicableDiscountCode("S"));
        Assert.assertEquals("", Discounter.getApplicableDiscountCode("T"));
        Assert.assertEquals("OFFER_30", Discounter.getApplicableDiscountCode("I"));
        Assert.assertEquals("OFFER_30", Discounter.getApplicableDiscountCode("K"));
        Assert.assertEquals("OFFER_25", Discounter.getApplicableDiscountCode("M"));
        Assert.assertEquals("OFFER_20", Discounter.getApplicableDiscountCode("A"));
        Assert.assertEquals("OFFER_25", Discounter.getApplicableDiscountCode("R"));
    }
}
