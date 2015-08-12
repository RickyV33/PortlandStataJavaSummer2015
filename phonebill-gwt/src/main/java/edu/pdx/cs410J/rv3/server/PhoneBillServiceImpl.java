package edu.pdx.cs410J.rv3.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.rv3.client.PhoneBill;
import edu.pdx.cs410J.rv3.client.PhoneCall;
import edu.pdx.cs410J.rv3.client.PhoneBillService;
import edu.pdx.cs410J.rv3.client.PhoneCallParser;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService {
    private PhoneBill phonebill;

    /**
     * Adds a phone call to the phone bill on the server
     *
     * @param args the arguments for the phone call
     * @return the new phone call that was added
     * @throws RuntimeException if any of the arguments fail to parse
     */
    @Override
    public AbstractPhoneCall addPhoneCall(Collection<String> args) throws RuntimeException {
        String customer = null;
        String caller = null;
        String callee = null;
        String start = null;
        String end = null;
        PhoneCallParser parser = new PhoneCallParser();
        Date startDate;
        Date endDate;

        for (String arg : args) {
            if (customer == null) {
                customer = arg;
            } else if (caller == null) {
                caller = arg;
            } else if (callee == null) {
                callee = arg;
            } else if (start == null) {
                start = arg;
            } else if (end == null) {
                end = arg;
            }
        }

        try {
            parser.parseTelephone(caller);
            parser.parseTelephone(callee);
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
        try {
            startDate = parser.parseDate(start);
            endDate = parser.parseDate(end);
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }

        if (phonebill == null) {
            phonebill = new PhoneBill(customer);
        } else if (!phonebill.getCustomer().equals(customer)) {
            throw new RuntimeException("The customer '" + customer + "' does not match the current customer '" +
                    phonebill.getCustomer() + "'!");
        }
        PhoneCall call = new PhoneCall(caller, callee, startDate, endDate);
        phonebill.addPhoneCall(call);
        return call;
    }

    /**
     * Retrieves the phone bill from the server
     *
     * @return the phone bill
     * @throws RuntimeException if the phone bill is null
     */
    @Override
    public AbstractPhoneBill getPhoneBill() throws RuntimeException{
        if (phonebill == null) {
            throw new RuntimeException("There are no phone calls in the bill yet!");
        }
        return phonebill;
    }

    /**
     * Retrieves the calls that fall within a certain start date
     *
     * @param start the earliest the call an start by
     * @param end   the latest the call can start by
     * @return A collection of phone calls from within the range
     * @throws RuntimeException If the dates fail to parse
     */
    @Override
    public Collection<AbstractPhoneCall> getPhoneCallsWithinRange(String start, String end) throws RuntimeException {
        PhoneCallParser parser = new PhoneCallParser();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = parser.parseDate(start);
            endDate = parser.parseDate(end);
        } catch (ParserException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        if (phonebill == null) {
            return new ArrayList<AbstractPhoneCall>();
        }
        return phonebill.getPhoneCallsWithinRange(startDate, endDate);

    }
    /**
     * Log unhandled exceptions to standard error
     *
     * @param unhandled The exception that wasn't handled
     */
    @Override
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }
}
