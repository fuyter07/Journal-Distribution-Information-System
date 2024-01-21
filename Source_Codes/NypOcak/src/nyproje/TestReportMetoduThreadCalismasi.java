
package nyproje;

public class TestReportMetoduThreadCalismasi {
    
    public static void main(String[] args){
        Distributor distributor = new Distributor();
        Subscriber individual = new Individual("Huseyin","Antalya","5276",52,65,45);
        Subscriber deneme = new Individual("Huseyin","Antalya","5276",52,65,45);
        Subscriber corporation = new Corporation("Denizli AVM","Denizli",52,"Ziraat Bank",5,12,2023,52);
        Journal journal = new Journal("Kriz", 12, "678", 25);
        DateInfo dates = new DateInfo(5,2023);
        Subscription subscription = new Subscription(dates, 1, journal, individual);
        subscription.acceptPayment(5555);
        Subscription subscription2 = new Subscription(dates, 1, journal, corporation);
        distributor.addJournal(journal);
        distributor.addSubscriber(individual);
        distributor.addSubscriber(corporation);
        distributor.addSubscription("678",individual,subscription);
        distributor.addSubscription("678",corporation,subscription2);
 //############################################################################################
 
        distributor.report(4, 2222, 2022, 2027); //ayri threadlarda calisiyor
        
        distributor.listIncompletePayments(); //reportu beklemeden ondan once calisti "Kriz dergisi icin Denizli AVM Abonesinin odedigi para yetersiz."
        distributor.addSubscriber(deneme); //reportu beklemeden ondan once calisti "Subscriber Huseyin already exists."
        distributor.saveState("merhaba.ser"); //reportu bekledi. Report bitmeden calismadi.
        distributor.loadState("merhaba.ser");
        
 //####################################################################################################       
        

    }
    
}
