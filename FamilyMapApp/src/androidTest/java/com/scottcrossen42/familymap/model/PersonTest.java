package com.scottcrossen42.familymap.model;

import junit.framework.TestCase;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/8/16.
 */
public class PersonTest extends TestCase {

    // Note: SEE THE MODEL-TEST CLASS FOR THE REST OF THE TESTING on this class.
    // Because this class is mostly just a container the majority of the testing will happen there.

    private Person person1=new Person("scottcrossen", "Scott", "Crossen", "BAMF");

    public void testGender(){
        assertEquals(person1.getGender(),"BAMF");
    }
    public void testID(){
        assertEquals(person1.getID(),"scottcrossen");
    }

    public void testName1(){
        assertEquals(person1.getFirstName(),"Scott");
    }

    public void testName2(){
        assertEquals(person1.getLastName(),"Crossen");
    }

}
