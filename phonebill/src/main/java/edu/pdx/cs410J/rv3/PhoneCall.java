package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

/**
 * Created by Rickyy on 7/3/2015.
 */
public class PhoneCall extends AbstractPhoneCall{
    public String caller;
    public String callee;
    public String startTime;
    public String endTime;

    public PhoneCall(String caller, String callee, String startTime, String endTime) {
        this.caller =  caller;
        this.callee = callee;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getCaller() {
        return caller;
    }

    @Override
    public String getCallee() {
        return callee;
    }

    @Override
    public String getStartTimeString() {
        return startTime;
    }

    @Override
    public String getEndTimeString() {
        return endTime;
    }
}
