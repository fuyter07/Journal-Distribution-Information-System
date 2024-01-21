
package test;

import nyproje.PaymentInfo;

import org.junit.Test;
import static org.junit.Assert.*;

public class PaymentInfoTest {
    @Test
    public void testPaymentInfo() {
        
        double discountRatio = 0.1; 
        PaymentInfo paymentInfo = new PaymentInfo(discountRatio);

        paymentInfo.increasePayment(100); 

        assertEquals(100, paymentInfo.getReceivedPayment(), 0.0001); //0.001 tolerans değeri
        assertEquals(discountRatio, paymentInfo.getDiscountRatio(), 0.0001); 

        paymentInfo.setReceivedPayment(150);
        assertEquals(150, paymentInfo.getReceivedPayment(), 0.0001); // Check if received payment is now 150

    }
    @Test
    public void testToString(){
        double discountRatio = 0.1;
        PaymentInfo paymentInfo = new PaymentInfo(discountRatio);
        assertEquals("PaymentInfo{discountRatio=0.1, receivedPayment=0.0}", paymentInfo.toString());
    }

}
