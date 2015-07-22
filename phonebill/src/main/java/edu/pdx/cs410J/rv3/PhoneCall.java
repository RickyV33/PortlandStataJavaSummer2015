package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.util.Date;

/**
 * The <code>PhoneCall</code> class extends the <code>AbstractPhoneCall</code> class which
 * creates a phone call with the caller's phone number, callee's phone number, start time of the call, and end time of
 * the call.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable {
    /** Stores the caller's phone number in the format xxx-xxx-xxxx.*/
    private String caller;
    /**Stores the callee's phone number in the format xxx-xxx-xxxx.*/
    private String callee;
    /**Stores the start date and time of the phone call in the format MM/dd/yyyy hh:mm.*/
    private String startTimeString;
    /**Stores the end date and time of the phone call in the format MM/dd/yyyy hh:mm.*/
    private String endTimeString;

    private Date startTime;
    private Date endTime;



    /**
     *
     * Creates a new <code>PhoneCall</code> object using a caller, callee, start time, and end time as the initial times.
     * @param caller The phone number of the person who made the phone call.
     * @param callee The phone number of the person who received the phone call
     * @param startTime The start date and time of the phone call.
     * @param endTime The end date and time of the phone call.
     */
    public PhoneCall(String caller, String callee, Date startTime, Date endTime) {
        this.caller = caller;
        this.callee = callee;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getCaller</code> to get the caller's phone number.
     * @return caller The phone number of the caller as a string from the object.
     */
    @Override
    public String getCaller() {
        return caller;
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getCallee</code> to get the callee's phone number.
     * @return callee The phone number of the callee as a string from the object.
     */
    @Override
    public String getCallee() {
        return callee;
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getStartTimeString</code> to get the start date
     * and the time from the object.
     * @return callee The phone number of the callee as a string.
     */
    @Override
    public String getStartTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(startTime);
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getEndTimeString</code> to get the end date
     * and the time from the object.
     * @return callee The phone number of the callee as a string.
     */
    @Override
    public String getEndTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(endTime);
    }

    @Override
    public Date getEndTime() {
        return super.getEndTime();
    }

    @Override
    public Date getStartTime() {
        return super.getStartTime();
    }

    /**
     * Returns <code>AbstractPhoneCall</code>'s method <code>toString</code> that prints a description of the phone call
     * that took place between two phone numbers with dates and times.
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneCall phoneCall = (PhoneCall) o;

        if (caller != null ? !caller.equals(phoneCall.caller) : phoneCall.caller != null) return false;
        if (callee != null ? !callee.equals(phoneCall.callee) : phoneCall.callee != null) return false;
        if (startTime != null ? !startTime.equals(phoneCall.startTime) : phoneCall.startTime != null) return false;
        return !(endTime != null ? !endTime.equals(phoneCall.endTime) : phoneCall.endTime != null);

    }

    @Override
    public int hashCode() {
        int result = caller != null ? caller.hashCode() : 0;
        result = 31 * result + (callee != null ? callee.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
