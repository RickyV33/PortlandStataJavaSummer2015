package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The <code>TextDumper</code> class extends the <code>PhoneBillDumper</code> class which writes the contents
 * of a phone bill into a given text file.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class TextDumper implements PhoneBillDumper {
    /**
     * Stores the name of the file that it writes to.
     */
    private String filename;

    /**
     * Creates a new <code>TextDumper</code> object using the filename argument.
     *
     * @param filename Stores the name of the file that the <code>TextDumper</code> object writes to.
     */
    public TextDumper(String filename) {
        this.filename = filename;
    }

    /**
     * This method takes in an <code>AbstractPhoneBill</code> object and writes the contents of the phone bill
     * to an external file with the the name filename.txt
     *
     * @param bill Stores the name of the customer and a list of all the phone calls the customer has logged.
     * @throws IOException Thrown if there was a problem with the input of the file.
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        PrintWriter writer;
        ArrayList<PhoneCall> callList = new ArrayList<>((((PhoneBill) bill).getPhoneCalls()));

        try {
            writer = new PrintWriter(filename);
        } catch (IOException e) {
            throw e;
        }

        //Writes the customers name on the fist line
        writer.println(bill.getCustomer());
        //Writes the phone calls into the file in the proper format
        for (PhoneCall call : callList) {
            writer.println(call.getCaller() + ";" + call.getCallee() + ";" + call.getStartTimeString() + ";" +
                    call.getEndTimeString());
        }
        writer.close();
    }
}