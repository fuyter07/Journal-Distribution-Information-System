package test;


import nyproje.Individual;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;


public class IndividualTest {
    private Individual individual;

    @Before
    public void setUp() {
         individual = new Individual("Huseyin Emre Seyrek","Antalya","5276",5,2028,123);
    }
    
    
    
    @Test
    public void testGetBillingInformation(){
        Individual individual = new Individual("Huseyin Emre Seyrek","Antalya","5276",5,2028,123);
        
        String beklenen = "Name: Huseyin Emre Seyrek, Address: Antalya\n" +
                            "Credit Card Number: 5276, Expire Month: 5, Expire Year: 2028, CCV: 123";
        
        assertEquals(beklenen,individual.getBillingInformation());
    }
    @Test
    public void testGetName() {
        assertEquals("Huseyin Emre Seyrek", individual.getName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("Antalya", individual.getAddress());
    }
}
