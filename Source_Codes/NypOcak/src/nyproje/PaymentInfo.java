package nyproje;

import java.io.Serializable;

public class PaymentInfo implements Serializable{
	private final double discountRatio;
	private double receivedPayment;
	
	public PaymentInfo(double discountRatio) {
		this.discountRatio = discountRatio;
		this.receivedPayment = 0;
	}
	public void increasePayment(double amount) {
		this.receivedPayment += amount;
	}
	public double getReceivedPayment() {
		return receivedPayment;
	}
	public double getDiscountRatio() {
		return discountRatio;
	}
	public void setReceivedPayment(double receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

    @Override
    public String toString() {
        return "PaymentInfo{" + "discountRatio=" + discountRatio + ", receivedPayment=" + receivedPayment + '}';
    }
	
	

}
