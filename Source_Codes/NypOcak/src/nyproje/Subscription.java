package nyproje;

import java.io.Serializable;

public class Subscription implements Serializable{
	private final DateInfo dates;
	private PaymentInfo payment;
	private int copies;
	private final Journal journal;  
	private final Subscriber subscriber;
	public Subscription(DateInfo dates, int copies, Journal journal, Subscriber subscriber) {
		this.dates = dates;
		this.copies = copies;
		this.journal = journal;
		this.subscriber = subscriber;
		this.payment = new PaymentInfo(0.05);
	}

    public DateInfo getDates() {
        return dates;
    }
	
	public void acceptPayment(double amount) {
		payment.increasePayment(amount);
                System.out.println("Para eklendi: " + amount);
	}
	public boolean canSend(int issueMonth, int year) {
            //BU NOT ONEMLİ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
           //ben olayi soyle kurguladim. Bu abonelikler yillik oldugu icin kullanici yil icinde cikacak tum dergi sayisinin parasini odemeden 1 dergi bile yollanmayacak.
           //yani 1 kopyaya abonelik baslatan abonemize yilda 6 defa(6 frequency) basımı yapılan yani 2 ayda bir yeni sayısı cikan bir dergiyi yollamaya baslamamiz icin bize 6sinin da ucretini vermesi lazim
           //vermiyosa 1 tane bile yollamayiz 6sinin da ucretini verdiyse aboneligi gecerli hale gelmis olur, parayi zamana gore parca parca verebilir tam istenilen paraya ulastiginda da dergi gercek anlamda yollanmaya baslar-
           //eger derginin onceki basilan sayilarini kacirdiysa onlar yollanir ve artik dergi basilan yeni aylarda yeni basimlari da yollamaya baslariz
           //bundan sonrakilerde dergiyi bastigimiz ayda yollariz yani aboneligi gecerli hale gelmis olur.
		double suanaKadarAlinan = payment.getReceivedPayment();
               
                if(dates.getStartYear() == year){
                    if(dates.getStartMonth() <= issueMonth){
                        double odenmesiGereken = this.copies*journal.getFrequency() * ((1-payment.getDiscountRatio())*journal.getIssuePrice());
                        if(suanaKadarAlinan >= odenmesiGereken){
                            return true;
                        }
                    }
                }
             
                else if(dates.getEndYear() == year){
                    if(dates.getEndMonth() >= issueMonth){
                        double odenmesiGereken = this.copies*journal.getFrequency() * ((1-payment.getDiscountRatio())*journal.getIssuePrice());
                        if(suanaKadarAlinan >= odenmesiGereken){
                            return true;
                        }
                    }
                }
		
		return false;
		
	}
	
	public Journal getJournal() {
		return journal;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setCopies(int value) {
		this.copies += value;
	}
        public PaymentInfo getPaymentInfo(){
            return payment;
        }

    @Override
    public String toString() {
        return "Subscription{journal:" + journal.getName() + ", " + subscriber + ", " + dates + ", " + payment + ", copies=" + copies + '}';
    }

    public int getCopies() {
        return copies;
    }
	
	
	

}
