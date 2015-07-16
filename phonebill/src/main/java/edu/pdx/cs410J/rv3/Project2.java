package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project that parses command line arguments to make sure they are in the correct
 * format before creating a <code>PhoneBill</code> and <code>PhoneCall</code> objects of the passed in arguments.
 *
 * @author Ricky Valencia
 * @version 1.0
 */


public class Project2 {
    /**Holds the name of the file if it exists*/
    public static String filename;
    /**Holds whether the user inserted a -fileName tag*/
    public static boolean hasFile;

  public static void main(String[] args) {
      Project2 app = new Project2(); //Used to instantiate the main class to use helper methods
      PhoneBill bill; //Holds the phone bill for the user
      PhoneCall call; //Holds the phone call for the user
      String customer = null; //Holds the customers name
      String caller = null; //Holds the caller's phone number
      String callee = null; //Holds the callee's phone number
      String startDate = null; //Holds the start date of the call
      String startTime = null; //Holds the start time of the call
      String endDate = null; //Holds the end date of the call
      String endTime = null; //Holds the end time of the call
      String start; //Holds the concatenation of the start date and start time
      String end; //Holds the concatenation of the end date and end time
      ArrayList<String> comLineInput = new ArrayList<String>(); //Holds the array of command line arguments
      boolean verbose; //Holds whether the '-print' tag was used
      Class c = AbstractPhoneBill.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      int argLength = args.length; //Holds the number of arguments passed in

      //If no command line arguments are passed in, gracefully exit
      if (argLength == 0) {
          System.err.println("Missing command line arguments");
          System.exit(1);
      }
      //For each argument passed into the CL, add it to the array
      for (String arg: args) {
          comLineInput.add(arg);
      }
      //Parses the command line for any tags and prioritizes them.
      app.parseReadMe(app, comLineInput);
      bill = (PhoneBill) app.parseFile(comLineInput);
      verbose = app.parsePrint(comLineInput);

      //Assign each index of comLineInput to it's own unique variable to use/understand them easier
      for (String var : comLineInput) {
          if (customer == null)
              customer = var;
          else if(caller == null)
              caller = var;
          else if (callee == null)
              callee = var;
          else if (startDate == null)
              startDate = var;
          else if (startTime == null)
              startTime = var;
          else if (endDate == null)
              endDate = var;
          else if (endTime == null)
              endTime = var;
      }

      //Parses the data to make sure it's in the correct format
      start = startDate + " " + startTime;
      end = endDate + " " + endTime;

      //Parses all the command line argumetns to make sure they are in the correct format/size
      try {
          app.parseCLSize(comLineInput);
          app.parseDateAndTime(start);
          app.parseDateAndTime(end);
          app.parseTelephone(caller);
          app.parseTelephone(callee);
      } catch (ParserException e) {
          System.err.println(e);
          System.exit(1);
      }

      //Creates the phone call and writes it to a file if they want
      call = new PhoneCall(caller, callee, start, end);
      if (bill == null) {
          bill = new PhoneBill(customer);
          bill.addPhoneCall(call);
      }
      else {
          if (!bill.getCustomer().equalsIgnoreCase(customer)) {
              System.err.println("The customers do not match!");
              System.exit(1);
          }
          bill.addPhoneCall(call);
      }

      if (hasFile)
          writeToFile(bill);

      //If the user inputs the tag -print, then it will print here
      if (verbose) {
          System.out.println(call.toString());
      }
    }

    /**
     * This method writes the contents of bill into filename in the correct format.
     * @param bill Holds the <code>PhoneBill</code> object that is going to be written into the file. It includes a list
     *             of <code>PhoneCall</code>s in it that are written also.
     */
    private static void writeToFile(PhoneBill bill) {

        TextDumper dump = new TextDumper(filename);
        try {
            dump.dump(bill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method parses the command line arguments and checks to see if it has the correct number of arguments for a
     * phone call. It must have 7 arguments in total and it returns a ParserException with the appropriate message if otherwise.
     * @param comLineInput The command line arguments passed in by the main method
     * @throws ParserException Throws this exception with a message that says if it has too few or too many arguments.
     */
    public void parseCLSize(ArrayList<String> comLineInput) throws ParserException{
        if (comLineInput.size() <= 6) {
            throw new ParserException("Missing command line arguments.");
        }
        else if(comLineInput.size() >= 8) {
            throw new ParserException("Too many command line arguments.");
        }
    }

    /**
     * Parses the command line arguments and checks for the tags '-print'. If the '-print' tag is found,
     * then it returns true, otherwise false.
     * @param list The list of command line arguments passed in as an <code>ArrayList</code> object.
     * @return If the '-print' tag is found, it returns true, otherwise false.
     */
    public boolean parsePrint(ArrayList<String> list) {
        //If the -print tag exists, set verbose to true and remove it from the array so only the arguments are left
        if (list.contains("-print")) {
            list.remove("-print"); //Removes the tag from the list of arguments
            return true;
        }
        return false;
    }

    /**
     * This method takes in the command line arguments as the parameter, then it checks it to see if it has the -textFile
     * tag. If it has the tag, then it locates the name of the file, parses the files' content, and returns the new
     * <code>PhoneBill</code> object created from the text file. It then removes the tag and filename from the list so it
     * doesn't interfere with the parsing of the new phone call. If the tag doesn't exist, then it returns null.
     * @param list Stores the command line arguments being passed in.
     * @return Returns null if the tag does not exist, returns a <code>PhoneBill</code> object containing the information
     * from the text file if the tag exists. It can still return null if the file does not exist but the tag was still present.
     */
    public AbstractPhoneBill parseFile(ArrayList list) {
        if (list.contains("-textFile")) {
            int filenameIndex = list.indexOf("-textFile") + 1;
            filename = (String) list.get(filenameIndex);
            //Removes the tag and filename from the list of arguments
            list.remove("-textFile");
            list.remove(filename);
            hasFile = true;
            TextParser contents = new TextParser(filename);
            try {
                return (PhoneBill) contents.parse();


            }
            catch (ParserException e) {
                System.err.println(e);
                System.exit(1);
            }

        }
        return null;
    }

    /**
     * This method parses the command line arguments in list and checks to see if the -README tag was in it. If the tag is
     * present, then it will print a description of the project then exists the program.
     * @param app Takes in an object of the project so it can access the <code>printDescription</code> method.
     * @param list Takes in the command line arguments as an ArrayList to parse it for the tag.
     */
    public void parseReadMe(Project2 app, ArrayList list) {
        //If the -README tag exists, run the description method and exit the program
        if (list.contains("-README")) {
            app.printDescription();
            System.exit(1);
        }
    }

    /**
     * Prints a description of the project by explaining how to use it, what it is, and any new features.
     */
    public void printDescription() {
       System.out.println("Author: Ricky Valencia\n Assignment: Project 1\n" +
               "This program takes in command line arguments which include customer's name, caller phone number, callee\n" +
               "phone number, the start date and time of the call, and when the call ended. The Order of the arguments is\n" +
               "important and they can be followed by the optional tags -print and -README, which can be in any order. This\n" +
               "program will parse the CL arguments and check to make sure they are in the correct format. If the user \n" +
               "inputs the -print tag then it will print a description of the call. If the user adds the -README tag, then\n" +
               "it will print a description of the program without executing the rest of the program. It can read and write\n " +
               "the phone call to the file if the -textFile tag exists with a filename.");
    }

    /**
     * Parses the dateString string against two different string formats, MM/dd/yyyy HH:mm and "M/dd/yyyy HH:mm, to make
     * sure that they were entered in under the correct format. If it's not in the proper format, <code>ParseDateAndTime</code>
     * will throw a <code>ParseException</code>, give an error message, and exit the program.
     * @param dateString The date and time string from the command line that will be parsed.
     */
    public void parseDateAndTime(String dateString) throws ParserException{
        String [] formatStrings = {"MM/dd/yyyy HH:mm", "M/dd/yyyy HH:mm"};

        for (String formatString : formatStrings) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                format.setLenient(false);
                Date date = format.parse(dateString);
            }
            catch (ParseException e) {
                throw new ParserException("Date must be in the format MM/dd/yyyy hh:mm.");
                //System.err.println("Date must be in the format MM/dd/yyyy hh:mm.");
                //System.err.println(e);
                //System.exit(1);
            }
        }
    }

    /**
     *Parses the number string against a regex pattern (xxx-xxx-xxxx where x's are numbers) to verify that the phone
     * number was passed in under the correct format. If it's not in the proper format, <code>ParseTelephone</code>
     * will print an error message and exit the program.
     * @param number The phone number from the command line that will be parsed by the method.
     */
    public void parseTelephone(String number) throws ParserException{

        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(number); //Check if the phone number is in the format xxx-xxx-xxxx

        if (!matcher.matches()) {
            throw new ParserException("Phone number must be in the format xxx-xxx-xxxx");
            //System.err.println("Phone number must be in the format xxx-xxx-xxxx.");
            //System.exit(1);
        }
    }
}