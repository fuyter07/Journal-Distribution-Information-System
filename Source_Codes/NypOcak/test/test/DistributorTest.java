
package test;


import nyproje.Corporation;
import nyproje.DateInfo;
import nyproje.Distributor;
import nyproje.Individual;
import nyproje.Journal;
import nyproje.Subscriber;
import nyproje.Subscription;

import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;


public class DistributorTest {
    private Distributor distributor;
    private Journal journal1,journal2,journal3;
    private Subscriber subscriber1,subscriber2,subscriber3;
    
    @Before
    public void setUp() {
        distributor = new Distributor();
        journal1 = new Journal("Bilim ve Gelecek", 12, "3333", 15.99);
        journal2 = new Journal("Bilim ve Teknik",2,"3334",16.50);
        journal3 = new Journal("Bilim Cocuk",6,"3335",9.99);
        subscriber1 = new Individual("Huseyin Emre Seyrek", "Antalya", "5276", 5, 2028, 156);
        subscriber2 = new Corporation("Tesla","United States",687,"Ziraat Bankasi",3,4,2023,99);
        subscriber3 = new Individual("Fahri Erdem", "Kastamonu", "5555", 4, 2025, 134);
    }
    
    @Test
    public void testAddJournal() {
        assertTrue(distributor.addJournal(journal1));
        assertFalse(distributor.addJournal(journal1)); // false donmeli zaten var ise.
        assertTrue(distributor.addJournal(journal2));
        assertTrue(distributor.addJournal(journal3));
        assertFalse(distributor.addJournal(journal2));
        assertFalse(distributor.addJournal(journal3));
    }
    @Test
    public void testSearchJournal() {
        distributor.addJournal(journal1);
        distributor.addJournal(journal2);
        distributor.addJournal(journal3);
        assertEquals(journal1, distributor.searchJournal("3333"));
        assertEquals(null, distributor.searchJournal("6666")); // Olmayan issn
        assertEquals(journal2, distributor.searchJournal("3334"));
        assertEquals(journal3, distributor.searchJournal("3335"));
    }
    @Test
    public void testAddSubscriber() {
        assertTrue(distributor.addSubscriber(subscriber1));
        assertFalse(distributor.addSubscriber(subscriber1)); // Subscriber zaten varsa false donmesi lazim
        assertTrue(distributor.addSubscriber(subscriber2));
        assertFalse(distributor.addSubscriber(subscriber2));
    }
    @Test
    public void testSearchSubscriber() {
        distributor.addSubscriber(subscriber1);
        distributor.addSubscriber(subscriber2);
        assertEquals(subscriber1, distributor.searchSubscriber("Huseyin Emre Seyrek"));
        assertEquals(null, distributor.searchSubscriber("Ahmet Cenet")); // Non-existent subscriber
        assertEquals(null, distributor.searchSubscriber("Teslaq"));
        assertEquals(subscriber2, distributor.searchSubscriber("Tesla"));
    }
    @Test
    public void testAddSubscription() {
        Subscription subscription = new Subscription(new DateInfo(3,2023), 1, journal1, subscriber2);
        distributor.addJournal(journal1);
        distributor.addSubscriber(subscriber2);
        assertTrue(distributor.addSubscription("3333", subscriber2, subscription));
        assertFalse(distributor.addSubscription("9876-5432", subscriber1, subscription)); 
        assertFalse(distributor.addSubscription("1234-5678", subscriber1, null)); 
        distributor.addSubscription("3333", subscriber2, subscription);
        assertEquals(subscription.getCopies(),2); //tekrar addsubscription dedigimiz icin kopya sayisi 1 artacak.
    }
    @Test
    public void testListSendingOrders() {
        distributor.addJournal(journal1);
        distributor.addJournal(journal2);
        distributor.addJournal(journal3);
        distributor.addSubscriber(subscriber1);
        distributor.addSubscriber(subscriber2);
        distributor.addSubscriber(subscriber3);
        Subscription subscription1 = new Subscription(new DateInfo(3,2023), 1, journal2, subscriber1);
        subscription1.acceptPayment(500.0);
        Subscription subscription2 = new Subscription(new DateInfo(3,2023), 4, journal2, subscriber2);
        subscription2.acceptPayment(1000.0);
        Subscription subscription3 = new Subscription(new DateInfo(3,2023), 1, journal1, subscriber1);
        subscription3.acceptPayment(750.0);
        Subscription subscription4 = new Subscription(new DateInfo(3,2023), 1, journal3, subscriber3);
        subscription3.acceptPayment(5.0);
        distributor.addSubscription("3334", subscriber1,subscription1); //journal2ye abonelik bagliyoruz
        distributor.addSubscription("3334", subscriber2,subscription2); //journal2ye abonelik bagliyoruz
        distributor.addSubscription("3333", subscriber1,subscription3); //journal1ye abonelik bagliyoruz
        distributor.addSubscription("3335", subscriber3,subscription4); //journal3ye abonelik bagliyoruz
        distributor.listAllSendingOrders(5, 2023);
        System.out.println("");
        distributor.listIncompletePayments();
        System.out.println("");
        distributor.listSendingOrders("3334", 5,2023);
        System.out.println("");
        distributor.listSubscriptionsByName("Huseyin Emre Seyrek");
        System.out.println("");
        distributor.listSubscriptionsByIssn("3334");
        
    }
    @Test
    public void testSaveAndLoadState() {
        distributor.addJournal(journal1);
        distributor.addSubscriber(subscriber1);
        Subscription subscription = new Subscription(new DateInfo(3,2023), 3, journal1, subscriber1);
        distributor.addSubscription("3333", subscriber1, subscription);
        assertTrue(distributor.saveState("test_state.ser"));

        Distributor loadedDistributor = new Distributor();
        assertTrue(loadedDistributor.loadState("test_state.ser"));
        
        Journal originalJournal = distributor.searchJournal("3333"); 
        Journal loadedJournal = loadedDistributor.searchJournal("3333");
        Subscriber originalSubscriber = distributor.searchSubscriber("Huseyin Emre Seyrek");
        Subscriber loadedSubscriber = loadedDistributor.searchSubscriber("Huseyin Emre Seyrek");
        
        assertEquals(originalJournal.getName(), loadedJournal.getName()); //bu sekilde karsilastirmam gerekiyor diger turlu yeni obje olarak algiliyor. Aslinda her degeriyle ayni yukleniyor.
        assertEquals(originalJournal.getSubscriptions().size(), loadedJournal.getSubscriptions().size());
        assertEquals(originalSubscriber.toString(), loadedSubscriber.toString()); //subscriberler da dogru her sey dogru.
       
    }
    @Test
    public void testReport() {
        distributor.addJournal(journal1);
        distributor.addSubscriber(subscriber1);
        Subscription subscription1 = new Subscription(new DateInfo(2,2022), 3, journal1, subscriber1);
        distributor.addSubscription("3333", subscriber1, subscription1);

        distributor.report(12, 2023, 2022, 2023);

    }
    
    
}
