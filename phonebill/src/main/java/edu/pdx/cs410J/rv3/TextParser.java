package edu.pdx.cs410J.rv3;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Rickyy on 7/12/2015.
 */
public class TextParser implements PhoneBillParser {
    String filename;
    File file;
    Scanner inputFile;
    PhoneBill bill;

    public TextParser(String filename) {
        this.filename = filename;
        file = new File(filename);
    }

    @Override
    public AbstractPhoneBill parse() throws ParserException {
        String line;
        String [] callInfo;

        if (!file.exists()) {
            try {
                boolean test = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                inputFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            line  = inputFile.nextLine(); //Reads the phone bill customer
            bill = new PhoneBill(line);

            while ( inputFile.hasNextLine()) {
                line = inputFile.nextLine();
                callInfo = line.split(";");
                bill.addPhoneCall(new PhoneCall(callInfo[0], callInfo[1], callInfo[2], callInfo[3]));
            }
            System.out.println(bill.toString());

        }
        return bill;
    }
}
