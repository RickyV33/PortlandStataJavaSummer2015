package edu.pdx.cs410J.rv3.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * The client-side interface to the ping service
 */
public interface PhoneBillServiceAsync {

    /**
     * Adds a phone call to the phone bill on the server
     *
     * @param args the arguments for the phone call
     * @throws RuntimeException if any of the arguments fail to parse
     */
    void addPhoneCall(Collection<String> args, AsyncCallback<AbstractPhoneCall> async) throws RuntimeException;

    /**
     * Retrieves the phone bill from the server
     *
     * @throws RuntimeException if the phone bill is null
     */
    void getPhoneBill(AsyncCallback<AbstractPhoneBill> async) throws RuntimeException;

    /**
     * Retrieves the calls that fall within a certain start date
     *
     * @param start the earliest the call an start by
     * @param end   the latest the call can start by
     * @throws RuntimeException If the dates fail to parse
     */
    void getPhoneCallsWithinRange(String start, String end, AsyncCallback<Collection<AbstractPhoneCall>> async) throws RuntimeException;

}
