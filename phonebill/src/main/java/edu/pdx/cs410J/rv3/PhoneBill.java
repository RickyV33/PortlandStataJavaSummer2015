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
    public List<PhoneCall> phoneCalls = null;

    public PhoneBill(String customer, List phoneCalls){
        this.customer = new String(customer);
        this.phoneCalls = new ArrayList<PhoneCall>(phoneCalls);
    }

    public PhoneBill(String customer) {
        this.customer = new String(customer);
    }

    @Override
    public String getCustomer() {
        return customer;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        if (phoneCalls == null)
            phoneCalls = new ArrayList<PhoneCall>();
        phoneCalls.add((PhoneCall) call);
    }

    @Override
    public Collection getPhoneCalls() {
        return phoneCalls;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
