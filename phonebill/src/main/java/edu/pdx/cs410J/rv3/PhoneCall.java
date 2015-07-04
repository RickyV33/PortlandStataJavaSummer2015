package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

/**
 * Created by Rickyy on 7/3/2015.
 */
public class PhoneCall extends AbstractPhoneCall{
    @Override
    public String getCaller() {
        return null;
    }

    @Override
    public String getCallee() {
        return null;
    }

    @Override
    public Date getStartTime() {
        return super.getStartTime();
    }

    @Override
    public String getStartTimeString() {
        return null;
    }

    @Override
    public Date getEndTime() {
        return super.getEndTime();
    }

    @Override
    public String getEndTimeString() {
        return null;
    }
}
