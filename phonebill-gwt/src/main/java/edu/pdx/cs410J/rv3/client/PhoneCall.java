package edu.pdx.cs410J.rv3.client;

import com.google.gwt.i18n.shared.DateTimeFormat;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.util.Date;




/**
 * The <code>PhoneCall</code> class extends the <code>AbstractPhoneCall</code> class which
 * creates a phone call with the caller's phone number, callee's phone number, start time of the call, and end time of
 * the call. It also implements the <code>Comparable</code> interface so we it can be used to compare two different phone
 * calls for sorting.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<AbstractPhoneCall> {
    /**
     * Stores the caller's phone number in the format xxx-xxx-xxxx.
     */
    private String caller;
    /**
     * Stores the callee's phone number in the format xxx-xxx-xxxx.
     */
    private String callee;
    /**
     * Stores the start date and time of the phone call in the format MM/dd/yyyy hh:mm (am/pm).
     */
    private Date startTime;
    /**
     * Stores the end date and time of the phone call in the format MM/dd/yyyy hh:mm (am/pm).
     */
    private Date endTime;


    /**
     * Creates a new <code>PhoneCall</code> object using a caller, callee, start time, and end time as the initial times.
     *
     * @param caller    The phone number of the person who made the phone call.
     * @param callee    The phone number of the person who received the phone call
     * @param startTime The start date and time of the phone call.
     * @param endTime   The end date and time of the phone call.
     */
    public PhoneCall(String caller, String callee, Date startTime, Date endTime) {
        this.caller = caller;
        this.callee = callee;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public PhoneCall() {}
    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getCaller</code> to get the caller's phone number.
     *
     * @return caller The phone number of the caller as a string from the object.
     */
    @Override
    public String getCaller() {
        return caller;
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getCallee</code> to get the callee's phone number.
     *
     * @return callee The phone number of the callee as a string from the object.
     */
    @Override
    public String getCallee() {
        return callee;
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getStartTimeString</code> to get the start date
     * and the time from the object.
     *
     * @return The phone number of the callee as a string.
     */
    @Override
    public String getStartTimeString() {
        //return DateTimeFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(startTime);
        DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy HH:mm:ss a");
        return format.format(startTime);
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getEndTimeString</code> to get the end date
     * and the time from the object.
     *
     * @return The phone number of the callee as a string.
     */
    @Override
    public String getEndTimeString() {
        //return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(endTime);
        DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy HH:mm:ss a");
        return format.format(endTime);
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getEndTime</code> to get the end date and the
     * time as a <code>Date</code> object.
     *
     * @return The date object that holds the end date and time of the phone call.
     */
    @Override
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Overrides <code>AbstractPhoneCall</code>'s abstract method <code>getStartTime</code> to get the start date and the
     * time as a <code>Date</code> object.
     *
     * @return The date object that holds the start date and time of the phone call.
     */
    @Override
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Returns <code>AbstractPhoneCall</code>'s method <code>toString</code> that prints a description of the phone call
     * that took place between two phone numbers with dates and times.
     *
     * @return Returns a small description of the phone call.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Overrides <code>Comparable</code>'s method to compare two seperate phone calls with each other. They are being compared
     * based on their start time, but if they share the same start time, then they are sorted by their phone call.
     *
     * @param call The next <code>PhoneCall</code> object that is being compared to the current object.
     * @return Returns -1 if <code>this</code> comes before <code>call</code>, 1 if it comes after, or 0 if they are equal.
     */
    @Override
    public int compareTo(AbstractPhoneCall call) {
        Date startTime = call.getStartTime();
        String caller = call.getCaller();

        //Compares the begin time
        if (this.startTime.equals(startTime)) {
            return (this.caller.compareTo(caller));
        }

        return this.startTime.compareTo(startTime);
    }
}