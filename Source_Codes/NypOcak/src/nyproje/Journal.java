    package nyproje;

import java.io.Serializable;
import java.util.ArrayList;

public class Journal implements Serializable{
	private String name,issn;
	private int frequency;
	private double issuePrice;
        private ArrayList<Subscription> subscriptions;
	
	public Journal(String name, int frequency, String issn, double issuePrice) {
		this.name = name;
		this.issn = issn;
		this.frequency = frequency;
		this.issuePrice = issuePrice;
                this.subscriptions = new ArrayList<>();
	}
	public void addSubscription(Subscription subscription) {
		subscriptions.add(subscription);
		
	}
        
	public double getIssuePrice() {
		return issuePrice;
	}
	public String getIssn() {
		return issn;
	}
	public String getName() {
		return name;
	}
	public int getFrequency() {
		return frequency;
	}

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
        
    public ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }
	
    
	
	

}
