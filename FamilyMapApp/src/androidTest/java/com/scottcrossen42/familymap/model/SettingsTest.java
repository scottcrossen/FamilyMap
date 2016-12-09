package com.scottcrossen42.familymap.model;

import junit.framework.TestCase;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/8/16.
 */
public class SettingsTest extends TestCase {

    // Note: SEE THE MODEL-TEST CLASS FOR THE REST OF THE TESTING on this class.
    // Because this class is mostly just a container the majority of the testing will happen there.

    Settings settings=Settings.getInstance();

    // Test the correct colors:
    public void testLifeColors() {
        settings.setLifeLineColor(Settings.LineColor.BLACK);
        assertEquals(settings.getLifeLineColor(),Settings.LineColor.BLACK);
    }

    // Test the correct colors:
    public void testFamilyColors() {
        settings.setFamilyTreeColor(Settings.LineColor.BLACK);
        assertEquals(settings.getFamilyTreeColor(),Settings.LineColor.BLACK);
    }

    // Test the correct colors:
    public void testSpouseColors() {
        settings.setSpouseLineColor(Settings.LineColor.BLACK);
        assertEquals(settings.getSpouseLineColor(),Settings.LineColor.BLACK);
    }

    // Test the correct map type:
    public void testBackground() {
        settings.setMapBackground(Settings.MapBackground.NORMAL);
        assertEquals(settings.getMapBackground(),Settings.MapBackground.NORMAL);
    }

    // Test the line functionality
    public void testLifeLine() {
        settings.setLifeLine(true);
        assertTrue(settings.isLifeLineEnabled());
    }

    // Test the line functionality
    public void testFamilyTree() {
        settings.setFamilyTree(true);
        assertTrue(settings.isFamilyTreeEnabled());
    }

    // Test the line functionality
    public void testLines () {
        settings.setSpouseLine(true);
        assertTrue(settings.isSpouseLineEnabled ());
    }
}
