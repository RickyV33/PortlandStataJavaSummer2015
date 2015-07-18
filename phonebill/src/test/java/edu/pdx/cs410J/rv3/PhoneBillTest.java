package edu.pdx.cs410J.rv3;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class PhoneBillTest extends TestCase {
    PhoneBill test = new PhoneBill("RICKY");

    @Test
    public void testGetCustomer() throws Exception {
        assertTrue("RICKY".equals(test.getCustomer()));
    }

    @Test
    public void testAddPhoneCall() throws Exception {
        assertEquals("RICKY's phone bill with 0 phone calls", (test.toString()));
        test.addPhoneCall(new PhoneCall("CALLER", "CALLEE", "START", "END"));
        assertEquals("RICKY's phone bill with 1 phone calls", (test.toString()));
    }

    @Test
    public void testGetPhoneCalls() throws Exception {
        assertEquals("RICKY's phone bill with 0 phone calls", (test.toString()));
    }

    @Test
    public void testGetPhoneCallsWithTwoCalls() {
        test.addPhoneCall(new PhoneCall("CALLER", "CALLEE", "START", "END"));
        test.addPhoneCall(new PhoneCall("CALLER", "CALLEE", "START", "END"));
        assertEquals("RICKY's phone bill with 2 phone calls", (test.toString()));
    }
}