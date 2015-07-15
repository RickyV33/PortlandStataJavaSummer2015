package edu.pdx.cs410J.rv3;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
        ArrayList<String> callInfo = new ArrayList<String>();
        Project2 parseArgs = new Project2();

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
            if (!inputFile.hasNext())
                throw new ParserException("File is empty!");
            bill = new PhoneBill(inputFile.nextLine()); //Reads the phone bill customer
            while (inputFile.hasNextLine()) {
                for (String string: inputFile.nextLine().split(";")) {
                    callInfo.add(string);
                }
                try {
                    parseArgs.parseCLSize(callInfo);
                    parseArgs.parseTelephone(callInfo.get(0));
                    parseArgs.parseTelephone(callInfo.get(1));
                    parseArgs.parseDateAndTime(callInfo.get(2));
                    parseArgs.parseDateAndTime(callInfo.get(3));
                }catch (ParserException e) {
                    throw e;
                }
                bill.addPhoneCall(new PhoneCall(callInfo.get(0), callInfo.get(1), callInfo.get(2), callInfo.get(3)));
            }
        }
        return bill;
    }
}
