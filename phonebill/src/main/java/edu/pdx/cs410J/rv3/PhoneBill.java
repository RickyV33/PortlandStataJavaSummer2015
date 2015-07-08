package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Rickyy on 7/3/2015.
 */
public class PhoneBill extends AbstractPhoneBill{
    public String customer;
    public List<PhoneCall> phoneCalls = new ArrayList<PhoneCall>();

    public PhoneBill(String customer, List phoneCalls){
        this.customer = new String(customer);
        this.phoneCalls = new ArrayList<PhoneCall>(phoneCalls);
    }

    @Override
    public String getCustomer() {
        return customer;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        phoneCalls.add((PhoneCall) call);
    }

    @Override
    public Collection getPhoneCalls() {
        return phoneCalls;
    }
}
