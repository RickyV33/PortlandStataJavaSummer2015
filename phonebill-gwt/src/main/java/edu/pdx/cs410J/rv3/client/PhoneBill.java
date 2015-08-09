package edu.pdx.cs410J.rv3.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


/**
 * The <code>PhoneBill</code> class extends the <code>AbstractPhoneBill</code> class which creates
 * a bill consisting of a series of phone calls addressed to one person.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PhoneBill extends AbstractPhoneBill {
    /**
     * Stores the customer's name that the phone calls belong to.
     */
    public String customer;
    /**
     * Stores the list of all the phone calls made by the customer.
     */
    public Collection<AbstractPhoneCall> phoneCalls;

    /**
     * Creates a new <code>PhoneBill</code> object using only the customers name.
     *
     * @param customer The name of the customer that the list of phone calls belongs to.
     */
    public PhoneBill(String customer) {
        this.customer = customer;
    }


    public PhoneBill() {}

    /**
     * Overrides <code>AbstractPhoneBill</code>'s abstract method <code>getCustomer</code> to return the customer's name.
     *
     * @return The customer's name
     */
    @Override
    public String getCustomer() {
        return customer;
    }

    /**
     * Overrides <code>AbstractPhoneBill</code>'s abstract method <code>addPhoneCall</code> to add a phone call into
     * the colletion of phone calls held by the customer.
     *
     * @param call A <code>PhoneCall </code> object to be stored into the list
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        if (phoneCalls == null) {
            phoneCalls = new ArrayList<>();
        }
        phoneCalls.add((PhoneCall) call);
    }

    /**
     * Overrides <code>AbstractPhoneBill</code>'s abstract method <code>getPhoneCalls</code> to return the sorted list of
     * phone calls.
     *
     * @return The list of phone calls as a ArrayList.
     */
    @Override
    public Collection<AbstractPhoneCall> getPhoneCalls() {
        Collections.sort((ArrayList) phoneCalls);
        return phoneCalls;
    }

    public Collection<AbstractPhoneCall> getPhoneCallsWithinRange(Date start, Date end) {
        Collection<AbstractPhoneCall> rangeCalls = new ArrayList<>();

        for (AbstractPhoneCall call: phoneCalls) {
            Date callStart = call.getStartTime();
            if (callStart.compareTo(start) >= 0 && callStart.compareTo(end) <= 0) {
                rangeCalls.add(call);
            }
        }
        return rangeCalls;
    }

    /**
     * Returns <code>AbstractPhoneBill</code>'s method <code>toString</code> that prints a description of the customer's
     * phone bill.
     *
     * @return The customer's name and the number of phone calls in his bill.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}