
package test;

import nyproje.Corporation;
import nyproje.DateInfo;

import nyproje.Journal;
import nyproje.Subscription;

import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubscriptionTest {
    private Subscription subscription;
    private Journal journal;
    private Corporation subscriber;
    
    @Before
    public void setUp() {
        DateInfo dates = new DateInfo(3,2023); // Replace with your actual date range
        journal = new Journal("Bilim ve gelecek", 12, "3333", 15.99);
        subscriber = new Corporation("Tesla","United States",45,"Ziraat Bankasi",15,3,2023,979);
        subscription = new Subscription(dates, 2, journal, subscriber);
    }
    
    @Test
    public void testGetDates() {
        DateInfo dates = subscription.getDates();
        assertEquals(2023, dates.getStartYear());
        assertEquals(3, dates.getStartMonth());
        assertEquals(2, dates.getEndMonth());
    }
    
    @Test
    public void testAcceptPayment() {
        subscription.acceptPayment(50.0);
        assertEquals(50.0, subscription.getPaymentInfo().getReceivedPayment(), 0.001);
    }
    @Test
    public void testCanSend() {
        subscription.acceptPayment(400.0); //freuquency 12, kopya sayisi 2, ucret 16 tl, benim fiyatimda bunlarin hepsi carpiliyor, en az 384 tl odemeli.

        assertFalse(subscription.canSend(6, 2022));

        assertTrue(subscription.canSend(6, 2023));

        assertFalse(subscription.canSend(1, 2022));
        
        assertFalse(subscription.canSend(5, 2024));
        
        assertFalse(subscription.canSend(3, 2024));
        
        assertTrue(subscription.canSend(2, 2024));

        assertFalse(subscription.canSend(1, 2023));
        subscription.getPaymentInfo().setReceivedPayment(253.0); //parayi yetersiz bir degere atarsak.
        assertFalse(subscription.canSend(2, 2024));
        assertFalse(subscription.canSend(6, 2023));
    }
    @Test
    public void testGetJournal() {
        assertEquals(journal, subscription.getJournal());
    }
    @Test
    public void testGetSubscriber() {
        assertEquals(subscriber, subscription.getSubscriber());
    }
    @Test
    public void testSetCopies() {
        subscription.setCopies(2); //+2 eklemek
        assertEquals(4, subscription.getCopies());
    }
    @Test
    public void testGetPaymentInfo() {
        assertEquals(0.05, subscription.getPaymentInfo().getDiscountRatio(), 0.001);
    }
    @Test
    public void testToString() {
        
        String expected = "Subscription{journal:Bilim ve gelecek, Subscriber{name=Tesla, address=United States}, DateInfo{startMonth=3, startYear=2023, endMonth=2, endYear =2024}, PaymentInfo{discountRatio=0.05, receivedPayment=0.0}, copies=2}";
       
        assertEquals(expected, subscription.toString());
    }
    
    
}
