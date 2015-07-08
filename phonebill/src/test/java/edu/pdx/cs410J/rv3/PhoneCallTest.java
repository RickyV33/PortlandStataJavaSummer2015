package edu.pdx.cs410J.rv3;

import junit.framework.TestCase;
import org.junit.Test;

public class PhoneCallTest extends TestCase {
    public PhoneCall test = new PhoneCall("CALLER", "CALLEE", "START", "END");

    @Test
    public void testGetCaller() {
        assertTrue("CALLER".equals(test.getCaller()));
    }

    @Test
    public void testGetCallee() {
        assertTrue("CALLEE".equals(test.getCallee()));
    }

    @Test
    public void testGetStartTime() {
        assertTrue("START".equals(test.getStartTimeString()));
    }

    @Test
    public void testGetEndTime() {
        assertTrue("END".equals(test.getEndTimeString()));
    }

    @Test
    public void testToString() {
        assertEquals(test.toString(), "Phone call from CALLER to CALLEE from START to END");
    }
}