package com.scottcrossen42.familymap.model;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/8/16.
 */
public class EventTest extends TestCase {

    // Note: SEE THE MODEL-TEST CLASS FOR THE REST OF THE TESTING on this class.
    // Because this class is mostly just a container the majority of the testing will happen there.

    private Event event1=new Event("1", "2", 100.00, 100.00, "USA!", "Albuquerque", "Died in Nowhere", 2016) ;
    private Event event2=new Event("1", "2", 100.00, 100.00, "USA!", "Albuquerque", "Died in Nowhere", 2016) ;
    private Event event3=new Event("2", "2", 100.00, 100.00, "USA!", "Albuquerque", "Died in Nowhere", 2016) ;

    public void testEventID(){
        assertEquals("1",event1.getID());
    }

    public void testPersonID(){
        assertEquals("2",event1.getID());
    }

    public void testLatLong(){
        LatLng coordinates = new LatLng(100.00, 100.00);
        assertEquals(coordinates,event1.getCoordinates());
    }

    public void testPlace(){
        assertEquals("USA!",event1.getCountry());
        assertEquals("Albuquerque",event1.getCity());
    }

    public void testDescription(){
        assertEquals("Died in Nowhere",event1.getDescription());
    }

    public void testTime(){
        assertEquals(2016,event1.getYear());
    }

    public void testCompare(){
        assertTrue(event1==event2);
        assertTrue(!(event1==event3));
    }
}
