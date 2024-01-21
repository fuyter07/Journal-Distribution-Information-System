package nyproje;

import java.io.Serializable;

public class Corporation extends Subscriber implements Serializable{
	private int bankCode;
	private String bankName;
	private int issueDay,issueMonth,issueYear;
	private int accountNumber;
	

	public Corporation(String name, String address,int bankCode, String bankName, int issueDay, int issueMonth, int issueYear, int accountNumber) {
		super(name, address);
		this.bankCode = bankCode;
	    this.bankName = bankName;
	    this.issueDay = issueDay;
	    this.issueMonth = issueMonth;
	    this.issueYear = issueYear;
	    this.accountNumber = accountNumber;
	}

	@Override
	public String getBillingInformation() {
        String subscriberInfo = "Name: " + super.getName() +
                                ", Address: " + super.getAddress();
        
        String billingInfo = "Bank Code: " + bankCode +
                             ", Bank Name: " + bankName +
                             ", Issue Day: " + issueDay +
                             ", Issue Month: " + issueMonth +
                             ", Issue Year: " + issueYear +
                             ", Account Number: " + accountNumber;

        return subscriberInfo + "\n" + billingInfo;
	}

}
