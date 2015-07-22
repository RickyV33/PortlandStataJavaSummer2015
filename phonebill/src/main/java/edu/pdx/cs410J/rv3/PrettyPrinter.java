package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ricky on 7/21/2015.
 */
public class PrettyPrinter implements PhoneBillDumper {
    private String filename;
    private ArrayList<PhoneCall> callList;
    private long duration;
    private String startDate;
    private String endDate;

    public PrettyPrinter(String filename) {
        this.filename = filename;
    }

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

    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        PrintWriter writer;
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
}
