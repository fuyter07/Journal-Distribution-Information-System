package test;


import nyproje.DateInfo;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateInfoTest {
    @Test
    public void testDateInfo() {
   
    int startMonth = 3;
    int startYear = 2022;

    DateInfo dateInfo = new DateInfo(startMonth, startYear);

    System.out.println("startMonth: " + startMonth);
    System.out.println("expected endMonth: " + 2);
    System.out.println("actual endMonth: " + dateInfo.getEndMonth());

    assertEquals(startMonth, dateInfo.getStartMonth());
    assertEquals(2, dateInfo.getEndMonth());
    assertEquals(startYear, dateInfo.getStartYear());
    }
    @Test
    public void testToString() {
        int startMonth = 3;
        int startYear = 2022;

        DateInfo dateInfo = new DateInfo(startMonth, startYear);

        String expectedToString = "DateInfo{startMonth=3, startYear=2022, endMonth=2, endYear =2023}";
        
        assertEquals(expectedToString, dateInfo.toString());
    }
    
    
}
