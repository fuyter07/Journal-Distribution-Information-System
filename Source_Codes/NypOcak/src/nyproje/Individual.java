package nyproje;

import java.io.Serializable;

public class Individual extends Subscriber implements Serializable{
	private String creditCardNr;
	private int expireMonth,expireYear;
	private int CCV;
	
	
	public Individual(String name, String address,String creditCardNr, int expireMonth,int expireYear,int CCV) {
		super(name, address);
		this.creditCardNr = creditCardNr;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.CCV = CCV;
	}

	@Override
	public String getBillingInformation() {
            String subscriberInfo = "Name: " + super.getName() +
                                    ", Address: " + super.getAddress();

            String billingInfo = "Credit Card Number: " + creditCardNr +
                                 ", Expire Month: " + expireMonth +
                                 ", Expire Year: " + expireYear +
                                 ", CCV: " + CCV;

            return subscriberInfo + "\n" + billingInfo;
        }
        

}
