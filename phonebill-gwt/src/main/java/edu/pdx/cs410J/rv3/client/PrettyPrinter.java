package edu.pdx.cs410J.rv3.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * This class can print the contents of a phone bill in a human readable format that looks 'pretty'.
 * It only prints out a phone call in a pretty format.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PrettyPrinter {
    /**
     * The default constructor that instantiates a <code>PrettyPrinter</code> object will all it's fields as null.
     */
    public PrettyPrinter() {
    }


    public String dumpWeb(AbstractPhoneCall call) throws IOException {
        //Holds the length of the phone calls in minutes.
        long duration = call.getEndTime().getTime() - call.getStartTime().getTime();
        //Sets the duration to minutes
        duration = duration/60000;
        DateTimeFormat format = DateTimeFormat.getFormat("MMMM dd, yyyy 'at' hh:mm a");
        //Holds the start date of the phone call.
        String startDate = format.format(call.getStartTime());
        //Holds the end date of the phone call.
        String endDate = format.format(call.getEndTime());
        return "You had a call from " + call.getCallee() + " to your phone " + call.getCaller() +
                " on " + startDate + " to " + endDate + " that lasted " + duration + " minutes.";
    }
}