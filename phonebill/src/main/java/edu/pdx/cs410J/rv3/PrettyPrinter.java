package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the <code>PhoneBillDumper</code> class so it can print the contents of a phone bill in a human
 * readable format that looks 'pretty'. It can either print to a specific file or it can print to the output stream.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PrettyPrinter implements PhoneBillDumper {
    /**
     * Holds the name of the file it will print to.
     */
    private String filename;
    /**
     * Holds the list of phone calls from the phone bill.
     */
    private ArrayList<PhoneCall> callList;
    /**
     * Holds the length of the phone calls in minutes.
     */
    private long duration;
    /**
     * Holds the start date of the phone call.
     */
    private String startDate;
    /**
     * Holds the end date of the phone call.
     */
    private String endDate;

    /**
     * The default constructor that instantiates a <code>PrettyPrinter</code> object will all it's fields as null.
     */
    public PrettyPrinter() {
    }

    /**
     * The constructor the <code>PrettyPrinter</code> that instantiates an object of itself with the name of the file
     * that the data will be stored in.
     *
     * @param filename The name of the file the data will be written to.
     */
    public PrettyPrinter(String filename) {
        this.filename = filename;
    }

    /**
     * This method takes in a <code>AbstractPhoneBill</code> object that will be parsed and displayed to standard out in
     * a pretty, human-readable format.
     *
     * @param bill The bill whose content will be display by standard out.
     */
    public void dumpStandardOut(AbstractPhoneBill bill) {
        callList = ((PhoneBill) bill).getPhoneCalls();

        System.out.format("Hello %s! You currently have %d calls logged on your bill.\nHere's a list of all your calls:\n",
                bill.getCustomer(), bill.getPhoneCalls().size());

        for (PhoneCall call : callList) {
            duration = call.getEndTime().getTime() - call.getStartTime().getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            startDate = DateFormat.getDateInstance(DateFormat.LONG).format(call.getStartTime());
            endDate = DateFormat.getDateInstance(DateFormat.LONG).format(call.getEndTime());
            System.out.format("You had a call from %s to your phone %s on %s to %s that lasted %s minutes.\n", call.getCallee(),
                    call.getCaller(), startDate, endDate, duration);

        }
    }

    /**
     * This method overrides <code>PhoneBillDumper</code>'s method so it can take a bill and write its content to an external
     * file.
     *
     * @param bill The bill whose content will be written to an external file.
     * @throws IOException Throws an exception if the file does not exist.
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        PrintWriter writer; //Used to write to the file
        callList = ((PhoneBill) bill).getPhoneCalls();

        try {
            writer = new PrintWriter(filename);
        } catch (IOException e) {
            throw e;
        }

        writer.format("Hello %s! You currently have %d calls logged on your bill.\nHere's a list of all your calls:\n",
                bill.getCustomer(), bill.getPhoneCalls().size());

        for (PhoneCall call : callList) {
            duration = call.getEndTime().getTime() - call.getStartTime().getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            startDate = DateFormat.getDateInstance(DateFormat.LONG).format(call.getStartTime());
            endDate = DateFormat.getDateInstance(DateFormat.LONG).format(call.getEndTime());
            writer.format("You had a call from %s to your phone %s on %s to %s that lasted %s minutes.\n", call.getCallee(),
                    call.getCaller(), startDate, endDate, duration);
        }
        writer.close();
    }

    public String dumpWeb(AbstractPhoneCall call) throws IOException {
        duration = call.getEndTime().getTime() - call.getStartTime().getTime();
        duration = TimeUnit.MILLISECONDS.toMinutes(duration);
        startDate = DateFormat.getDateInstance(DateFormat.LONG).format(call.getStartTime());
        endDate = DateFormat.getDateInstance(DateFormat.LONG).format(call.getEndTime());
        return String.format("You had a call from %s to your phone %s on %s to %s that lasted %s minutes.\n", call.getCallee(),
                call.getCaller(), startDate, endDate, duration);
    }
}
