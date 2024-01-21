package test;

import nyproje.Corporation;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CorporationTest {

    private Corporation corporation;

    @Before
    public void setUp() {
        corporation = new Corporation("Ziraat Bankasi", "Istanbul", 9876, "Big Bank", 5, 2023, 2010, 123456789);
    }

    @Test
    public void testGetBillingInformation() {
        String expected = """
                          Name: Ziraat Bankasi, Address: Istanbul
                          Bank Code: 9876, Bank Name: Big Bank, Issue Day: 5, Issue Month: 2023, Issue Year: 2010, Account Number: 123456789""";
      
        assertEquals(expected, corporation.getBillingInformation());
    }

    @Test
    public void testGetName() {
        assertEquals("Ziraat Bankasi", corporation.getName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("Istanbul", corporation.getAddress());
    }


}