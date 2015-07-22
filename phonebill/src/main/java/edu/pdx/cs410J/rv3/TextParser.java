package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

/**
 * The <code>TextParser</code> class extends the <code>PhoneBillParser</code> class which reads information for a phone
 * bill into a PhoneBill object and returns the object.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class TextParser implements PhoneBillParser {
    /**
     * Stores the name of the file that the class will parse the information from.
     */
    private String filename;

    /**
     * Creates a new <code>TextParser</code> object using the filename argument.
     *
     * @param filename Stores the name of the file that the <code>TextParser</code> object reads from.
     */
    public TextParser(String filename) {
        this.filename = filename;
    }

    /**
     * This method checks to see if a file exists with the name of filename. If it doesn't exist, then it creates
     * the file and returns a null <code>PhoneBill</code> object, if it does exist, then it loads in the contents of the
     * file and parses the data. If the data is not in the correct format, then it'll throw a <code>ParserException</code>.
     * In the end, it returns the <code>PhoneBill</code> object created from the file.
     *
     * @return Returns the <code>PhoneBill</code> object instantiated with all information from filename
     * @throws ParserException Thrown if the data stored in filename is in the wrong format.
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        ArrayList<String> callInfo;
        Scanner inputFile = null;
        PhoneBill bill = null;
        PhoneCall call = null;
        Project4 parseArgs = new Project4();
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                inputFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Checks if the file is empty
            if (!inputFile.hasNext())
                return null;

            bill = new PhoneBill(inputFile.nextLine()); //Reads the phone bill customer
            while (inputFile.hasNextLine()) {
                callInfo = new ArrayList<>(); //Clear out callInfo by instantiating a new object
                Collections.addAll(callInfo, inputFile.nextLine().split(";"));
                parseArgs.parseCLSize(callInfo, 4);
                try {
                    call = (PhoneCall) parseArgs.parsePhoneCall(callInfo);
                } catch (ParserException e) {
                    throw e;
                }
                //Adds a Phone Call object to the bill from the callInfo List
                bill.addPhoneCall(call);
            }
        }
        return bill;
    }
}
