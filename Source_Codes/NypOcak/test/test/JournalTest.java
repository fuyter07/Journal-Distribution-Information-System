/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import java.util.ArrayList;
import nyproje.DateInfo;
import nyproje.Individual;
import nyproje.Journal;
import nyproje.Subscription;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author varso
 */
public class JournalTest {
    
    private Journal journal;
    @Before
    public void setUp() {
        journal = new Journal("Bilim ve Gelecek", 12, "3333", 15.99);
    }
    @Test
    public void testGetIssuePrice() {
        assertEquals(15.99, journal.getIssuePrice(), 0.001);
    }
    @Test
    public void testGetIssn() {
        assertEquals("3333", journal.getIssn());
    }
    @Test
    public void testGetName() {
        assertEquals("Bilim ve Gelecek", journal.getName());
    }
    @Test
    public void testGetFrequency() {
        assertEquals(12, journal.getFrequency());
    }
    @Test
    public void testAddSubscription() {
        Individual individual = new Individual("Huseyin Emre Seyrek", "Antalya", "5276", 5, 2028, 123);
        DateInfo dateinfo = new DateInfo(3,2022);
        
    
        journal.addSubscription(new Subscription(dateinfo, 1, journal, individual));
        ArrayList<Subscription> subscriptions = journal.getSubscriptions();
        assertEquals(1, subscriptions.size());

        Subscription addedSubscription = subscriptions.get(0);
        assertEquals(individual, addedSubscription.getSubscriber());
        assertEquals(2, addedSubscription.getDates().getEndMonth());
    }
    
}
