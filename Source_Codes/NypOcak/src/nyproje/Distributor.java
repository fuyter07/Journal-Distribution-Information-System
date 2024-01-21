package nyproje;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Distributor implements Serializable{
	private Hashtable<String,Journal> journals;
	private Vector<Subscriber> subscribers;
        private transient Object lock = new Object(); //lock olarak kullanacagimiz obje
        private boolean reportActive = false; //report metodunun testi icin TestReportMetoduThreadCalismasi dosyasini calistirip inceleyebilirsiniz orada guzel bir aciklama yaptim.
	public Distributor() {
		this.journals = new Hashtable<>();
		this.subscribers = new Vector<>();
	}
	public boolean addJournal(Journal journal) {
		if(journal != null) { //yollanan journal null mu degil mi kontrolu
			String issn = journal.getIssn();
			if(!journals.containsKey(issn)) { //bizim hashtablemizde bu journal var mi yok mu kontrolu
				journals.put(issn,journal); //journali hashtable a ekle.
				System.out.println("Journal "+ journal.getName() + " succesfully added");
				return true;
			}
			else {
				System.out.println("Error: Journal with ISSN " + issn + " already exists.");
			}
		}
		return false;
	}
	public Journal searchJournal(String issn) {
		if(journals.get(issn) == null) {
			System.out.println("There is no journal with this issn.");
		}
        return journals.get(issn); //yoksa null varsa journali dondurcek.
    }
	public boolean addSubscriber(Subscriber subscriber) {
		if(subscriber != null) {
                    for(Subscriber sub: subscribers){
			if(sub.getName().equals(subscriber.getName()) && sub.getAddress().equals(subscriber.getAddress())) { //ayni isim ve adreste kullanici varsa ekleme.
                            System.out.println("Subscriber "+subscriber.getName() + " already exists.");
                            return false;
				
			}
                    }
                    subscribers.add(subscriber);
                    System.out.println("Subscriber "+subscriber.getName() + " succesfully added.");
                    return true;	
		}
                return false;
		
	}
	public Subscriber searchSubscriber(String name) {
		for(Subscriber subscriber:subscribers) {
			if(subscriber.getName().equals(name)) {
				System.out.println("Subscriber named "+ subscriber.getName() + " found.");
				return subscriber;
			}
		}
		System.out.println("Subscriber not found.");
		return null;
	}
	public boolean addSubscription(String issn, Subscriber subscriber,Subscription subscription) {
		Journal journal = searchJournal(issn);
		Subscriber existSubscriber = searchSubscriber(subscriber.getName());
		if(journal != null && existSubscriber != null) {
			if(subscription != null) {
                            for(Subscription subs:journal.getSubscriptions()){ //journalin subscriptionlarini geziyoruz.
                                if(subs.getSubscriber().equals(existSubscriber)){ //verilen journal ve subscriber icin zaten bir subscription varsa;
                                    subs.setCopies(1); //kopyayi bir arttir.
                                    System.out.println("An existing subscription found. Copies will be increased.");
                                    return true;
                                }
                            }
                        
                            journal.addSubscription(subscription);
                            System.out.println("A new subscription to the journal has been initiated.");
                            return true;
                            
                        }
                        
		}
		System.out.println("Such a subscription cannot be established. The Journal or Subscriber is not available.");
		return false;
		
	}
	
	public void listAllSendingOrders(int month, int year) {
            for(Journal journal: journals.values()){
                for(Subscription subscription: journal.getSubscriptions()){
                    if(subscription.canSend(month, year)){
                        System.out.println("The '"+journal.getName() + "' journal will be sent to the subscriber '"+subscription.getSubscriber().getName()+"', who is subscribed for '"+subscription.getCopies()+"' copies.");
                    }
                }
            }
	}
        public void listSendingOrders(String issn, int month, int year){
            Journal journal = searchJournal(issn); //verilen journal var mi diye bakiyoruz.
            if(journal != null){
                for(Subscription subscription: journal.getSubscriptions()){
                    if(subscription.canSend(month, year)){
                        System.out.println("The '" + journal.getName() + "' journal will be sent to the subscriber '" + subscription.getSubscriber().getName() + "', who is subscribed for '"+subscription.getCopies()+"' copies");
                    }
                }
            }
            else{
                System.out.println("The related journal could not be found.");
            }
        }
        public void listIncompletePayments(){
            for(Journal journal: journals.values()){
               for(Subscription subscription: journal.getSubscriptions()){
                   if(subscription.getPaymentInfo().getReceivedPayment() < subscription.getCopies() * journal.getFrequency() * (1-subscription.getPaymentInfo().getDiscountRatio())* journal.getIssuePrice()){
                       System.out.println("'" + journal.getName()+ "'" + " dergisi icin " + "'" + subscription.getSubscriber().getName()+"'" + " abonesinin odedigi para yetersiz!");
                   }
               
               }
                
            }
        }
        public void listSubscriptionsByName(String subscriberName){
            for(Journal journal:journals.values()){
                for(Subscription subscription:journal.getSubscriptions()){
                    if(subscription.getSubscriber().getName().equals(subscriberName)){
                        System.out.println(subscription);
                    }
                }
            }
        }
        
        public void listSubscriptionsByIssn(String issn){
            Journal journal = searchJournal(issn);
            if(journal != null){ //journal mevcutsa.
                for(Subscription subscription: journal.getSubscriptions()){
                    System.out.println(subscription);
                }
            }
        }
        public boolean saveState(String fileName){
            synchronized (lock) {
                try {
                   
                      while (reportActive) {
                        lock.wait(); //rapor metodu aktifse bekle.
                    }
                    

                    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
                        out.writeObject(this);
                        System.out.println("Succesfully Saved...");
                        return true;
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        public boolean loadState(String fileName){
            synchronized (lock) {
                try {
                    while (reportActive) {
                        lock.wait(); //rapor metodu aktifse bekle.
                    }
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                        Distributor loadedDistributor = (Distributor) in.readObject(); //yukleme islemi.
                        this.journals = loadedDistributor.journals;
                        this.subscribers = loadedDistributor.subscribers;
                        this.lock = new Object();
                        System.out.println("Loaded succesfully...");
                        return true;
                    } 
                    catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    }
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        public void report(int expiredMonth,int expiredYear, int year1,int year2){//BU metod, ayri bir threadda calisiyor ve savestate, loadstate metodlari bu metod eger calisiyorsa bu metodu bekliyor.
            synchronized(lock){
                reportActive = true;//rapor calisiyor.
                ExecutorService executorService = Executors.newFixedThreadPool(2); //verilen ay ve yil'da bitecek aboneleri bir threadda, verilen yillar arasi kazanilan yillik parayi ayri bir threadda yapmak icin thread havuzu.
                
                executorService.submit(() -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("expiredsubscriptions.html"))) { //kayit edilecek html dosyasi.
                        writer.write("<html>\n");
                        writer.write("<head>\n");
                        writer.write("<title>Java HTML Dosyasi</title>\n");
                        writer.write("</head>\n");
                        writer.write("<body>\n");
                        int count = 0;
                        for(Journal journal: journals.values()){
                            for (Subscription subscription : journal.getSubscriptions()) {
                                if(expiredYear > subscription.getDates().getEndYear()){
                                    writer.write("<p>" + subscription.toString() + "</p>\n"); //kayit ediyoruz.
                                    count++;
                                }
                                else if(expiredYear == subscription.getDates().getEndYear() && expiredMonth>subscription.getDates().getEndMonth()){
                                    writer.write("<p>" + subscription.toString() + "</p>\n");
                                    count++;
                                }
                                    
                            }
                        }
                        writer.write("<p>" + "The total number of subscriptions that will expire: " + count + "</p>\n");
                        Thread.sleep(3000); //threadi uyutuyorum ki savestate ve loadstate bekliyor mu beklemiyor mu gorebilmek icin yoksa isi cok hizli yapar boyle biraz uyutalim ki simule edisimiz guzel olsun.
                        writer.write("</body>\n");
                        writer.write("</html>");
                        writer.close();
                        System.out.println("HTML dosyasi olusturuldu: expiredsubscriptions.html"); //html dosyasi olusturduk.
                        File file = new File("expiredsubscriptions.html"); //olusan htmli acacagiz.
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.OPEN)) {
                                desktop.open(file);
                            } 
                            else {
                                System.out.println("Dosya acma eylemi desteklenmiyor: " + file.getAbsolutePath());
                            }
                        }       
                    } 
                    catch (IOException e) {
                        System.out.println("Dosya acilirken bir hata olustu: " + e.getMessage());
                    }   
                    catch (InterruptedException ex) {
                        Logger.getLogger(Distributor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    synchronized (lock) {
                        reportActive = false; //rapor isi bitti.
                        lock.notifyAll();
                    }
                });
                
                executorService.submit(() -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("annualpayments.html"))) {
                        writer.write("<html>\n");
                        writer.write("<head>\n");
                        writer.write("<title>Java HTML Dosyası</title>\n");
                        writer.write("</head>\n");
                        writer.write("<body>\n");
                       
                        //para hesabi.
                        for(int i = year1;i<=year2;i++){
                            double annual_payments = 0;
                            for(Journal journal:journals.values()){
                                for(Subscription subscription:journal.getSubscriptions()){
                                    if(subscription.getDates().getEndYear() >= i && subscription.getDates().getStartYear() <= i){
                                        //Para hesabini baslangic ve bitis yillari arasinda subscriptiondan toplanan paraya gore yapiyoruz.
                                        annual_payments += subscription.getPaymentInfo().getReceivedPayment();
                                    }
                                }
                            }
                            String text = String.valueOf(i) + ": annual payment: " + String.valueOf(annual_payments);
                            writer.write("<p>" + text + "</p>\n");
                            
                        }
                        Thread.sleep(3000);
                        writer.write("</body>\n");
                        writer.write("</html>");
                        writer.close();
                        System.out.println("HTML dosyasi olusturuldu: annualpayments.html");
                        File file = new File("annualpayments.html");
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.OPEN)) {
                                desktop.open(file);
                            } 
                            else {
                                System.out.println("Dosya acma eylemi desteklenmiyor: " + file.getAbsolutePath());
                            }
                        }       
                    }
                    catch (IOException e) {
                        System.out.println("Dosya acilirken bir hata olustu: " + e.getMessage());
                    } 
                    catch (InterruptedException ex) {
                        Logger.getLogger(Distributor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    synchronized (lock) {
                        reportActive = false;
                        lock.notifyAll();
                    }
                });
                     
                executorService.shutdown();
   
            }
        }

    public Hashtable<String, Journal> getJournals() {
        return journals;
    }

    public Vector<Subscriber> getSubscribers() {
        return subscribers;
    }
        
        
    }
        

