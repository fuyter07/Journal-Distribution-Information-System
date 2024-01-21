package nyproje;

import java.io.Serializable;



public abstract class Subscriber implements Serializable{
	private String name;
	private String address;
	
	public Subscriber(String name, String address) {
		this.name = name;
		this.address = address;
	}
	public abstract String getBillingInformation();
	
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}

    @Override
    public String toString() {
        return "Subscriber{" + "name=" + name + ", address=" + address + '}';
    }
	
	

}
