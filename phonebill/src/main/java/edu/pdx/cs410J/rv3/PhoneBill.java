package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * Created by Rickyy on 7/3/2015.
 */
public class PhoneBill extends AbstractPhoneBill{
    public String customer;

    @Override
    public String getCustomer() {
        return customer;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall call) {

    }

    @Override
    public Collection getPhoneCalls() {
        return null;
    }
}
