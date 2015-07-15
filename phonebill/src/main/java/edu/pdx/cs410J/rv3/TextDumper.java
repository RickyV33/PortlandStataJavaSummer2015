package edu.pdx.cs410J.rv3;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rickyy on 7/12/2015.
 */
public class TextDumper implements PhoneBillDumper{
    String filename;

    public TextDumper(String filename) {
        this.filename = filename;
    }

    @Override
    public void dump(AbstractPhoneBill bll) throws IOException {
        //String calls = new String();
        ArrayList<String> calls = new ArrayList<String>();
        PrintWriter writer;
        ArrayList<PhoneCall> callList = new ArrayList<PhoneCall>();

        try {
            writer = new PrintWriter(filename);
        } catch (IOException e) {
            throw e;
        }

        PhoneBill bill = new PhoneBill("Test");
        bill.addPhoneCall(new PhoneCall("541-541-2222", "541-412-1212", "06/26/1994 9:30", "02/12/1242 3:30"));
        System.out.println(bill.getPhoneCalls());
        for (PhoneCall call: callList) {
            for (String c: calls) {
                c += call.getCaller() + ";";
                c += call.getCallee() + ";";
                c += call.getStartTimeString() + ";";
                c += call.getEndTimeString();
                System.out.println(c);
                writer.println(c);
            }
        }

        writer.close();

    }
}