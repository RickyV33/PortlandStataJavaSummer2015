package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
      Project1 app = new Project1();
      PhoneBill bill;
      PhoneCall call;
      String customer = null;
      String caller = null;
      String callee = null;
      String startDate = null;
      String startTime = null;
      String endDate = null;
      String endTime = null;
      String start;
      String end;
      ArrayList<String> comLineInput;
      boolean verbose = false;
      Class c = AbstractPhoneBill.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      int argLength = args.length;

      if (argLength == 0) {
          System.err.println("Missing command line arguments");
          System.exit(1);
      }
      comLineInput = new ArrayList<String>();
      for (int i = 0; i < argLength; ++i) {
          comLineInput.add(args[i]);
      }
      if (comLineInput.contains("-README")) {
          app.printDescription();
          System.exit(1);
      }
      if (comLineInput.contains("-print")) {
          verbose = true;
          comLineInput.remove("-print");
      }
      if (comLineInput.size() < 7) {
          System.err.println("Missing command line arguments");
          System.exit(1);
      }
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

      start = startDate + " " + startTime;
      end = endDate + " " + endTime;
      app.parseDateAndTime(start);
      app.parseDateAndTime(end);
      app.parseTelephone(caller);
      app.parseTelephone(callee);

      if (verbose) {
          call = new PhoneCall(caller, callee, start, end);
          bill = new PhoneBill(customer);
          bill.addPhoneCall(call);
          System.out.println("Bill: " + bill.toString());
          System.out.println("Call: " + call.toString());
      }
  }

    public void printDescription() {
       System.out.println("This project yada yada ");
    }

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

    public void parseTelephone(String number) {


        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(number);

        if (!matcher.matches()) {
            System.err.println("Phone number must be in the format xxx-xxx-xxxx.");
            System.exit(1);
        }
    }

}