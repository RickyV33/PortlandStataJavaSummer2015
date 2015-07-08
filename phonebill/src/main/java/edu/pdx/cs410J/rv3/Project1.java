package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project that parses command line arguments to make sure they are in the correct
 * format before creating a <code>PhoneBill</code> and <code>PhoneCall</code> objects of the passed in arguments.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class Project1 {

  public static void main(String[] args) {
      Project1 app = new Project1(); //Used to instantiate the main class to use helper methods
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
      ArrayList<String> comLineInput; //Holds the array of command line arguments
      boolean verbose = false; //Holds whether the '-print' tag was used
      Class c = AbstractPhoneBill.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      int argLength = args.length; //Holds the number of argumetns passed in

      //If no command line arguments are passed in, gracefully exit
      if (argLength == 0) {
          System.err.println("Missing command line arguments");
          System.exit(1);
      }
      comLineInput = new ArrayList<String>(); //Instantiate the command line arguments into an array
      //For each argument passed into the CL, add it to the array
      for (int i = 0; i < argLength; ++i) {
          comLineInput.add(args[i]);
      }
      //Parses the command line for any tags and prioritizes them.
      verbose = app.parseTags(app, comLineInput);

      //If the remaining arguments are less than 7 (not every argument was passed in), exit the program gracefully
      if (comLineInput.size() <= 6) {
          System.err.println("Missing command line arguments.");
          System.exit(1);
      }
      else if(comLineInput.size() >= 8) {
          System.err.println("Too many command line argumetns.");
          System.exit(1);
      }

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
      app.parseDateAndTime(start);
      app.parseDateAndTime(end);
      app.parseTelephone(caller);
      app.parseTelephone(callee);

      //If the user inputs the tag -print, then it will print here
      if (verbose) {
          call = new PhoneCall(caller, callee, start, end);
          bill = new PhoneBill(customer);
          bill.addPhoneCall(call);
          //System.out.println("Bill: " + bill.toString());
          System.out.println(call.toString());
      }
    }

    /**
     * Parses the command line arguments and checks for the tags '-print' and '-README'. If the '-print' tag is found,
     * then it returns true, otherwise false. If the '-README' tag is found, then it will print a description of the
     * project without parsing the rest of the command line.
     * @param app An instantiation of the Project1 Class so helper methods within the class can be used.
     * @param list The list of command line arguments passed in as an <code>ArrayList</code> object.
     * @return If the '-print' tag is found, it returns true, otherwise false.
     */
    public boolean parseTags(Project1 app, ArrayList list) {

        //If the -README tag exists, run the description method and exit the program
        if (list.contains("-README")) {
            app.printDescription();
            System.exit(1);
        }
        //If the -print tag exists, set verbose to true and remove it from the array so only the arguments are left
        if (list.contains("-print")) {
            list.remove("-print");
            return true;
        }
        return false;
    }

    /**
     * Prints a description of the project by explaining how to use it and what it is.
     */
    public void printDescription() {
       System.out.println("Author: Ricky Valencia\n Assignment: Project 1\n" +
               "This program takes in command line arguments which include customer's name, caller phone number, callee\n" +
               "phone number, the start date and time of the call, and when the call ended. The Order of the arguments is\n" +
               "important and they can be followed by the optional tags -print and -README, which can be in any order. This\n" +
               "program will parse the CL arguments and check to make sure they are in the correct format. If the user \n" +
               "inputs the -print tag then it will print a description of the call. If the user adds the -README tag, then\n" +
               "it will print a description of the program without executing the rest of the program.");
    }

    /**
     * Parses the dateString string against two different string formats, MM/dd/yyyy HH:mm and "M/dd/yyyy HH:mm, to make
     * sure that they were entered in under the correct format. If it's not in the proper format, <code>ParseDateAndTime</code>
     * will throw a <code>ParseException</code>, give an error message, and exit the program.
     * @param dateString The date and time string from the command line that will be parsed.
     */
    public void parseDateAndTime(String dateString) {
        String [] formatStrings = {"MM/dd/yyyy HH:mm", "M/dd/yyyy HH:mm"};

        for (String formatString : formatStrings) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                format.setLenient(false);
                Date date = format.parse(dateString);
            }
            catch (ParseException e) {
                System.err.println("Date must be in the format MM/dd/yyyy hh:mm.");
                System.exit(1);
            }
        }
    }

    /**
     *Parses the number string against a regex pattern (xxx-xxx-xxxx where x's are numbers) to verify that the phone
     * number was passed in under the correct format. If it's not in the proper format, <code>ParseTelephone</code>
     * will print an error message and exit the program.
     * @param number The phone number from the command line that will be parsed by the method.
     */
    public void parseTelephone(String number) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(number); //Check if the phone number is in the format xxx-xxx-xxxx

        if (!matcher.matches()) {
            System.err.println("Phone number must be in the format xxx-xxx-xxxx.");
            System.exit(1);
        }
    }

}