package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
      String customer = null;
      String caller = null;
      String callee = null;
      String startDate = null;
      String startTime = null;
      String endDate = null;
      String endTime = null;
      String print = null;
      String readMe = null;
      List<String> tag;
      Class c = AbstractPhoneBill.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      int argLength = args.length;

      if (argLength <= 2 || argLength > 0) {
          tag = new ArrayList<String>();
          for (String arg : args)
              tag.add(arg);
          System.out.println(tag);
      }
      else if (argLength >= 7 || argLength <= 9) {
          for (String arg : args) {
              if (customer == null)
                  customer = arg;
              else if(caller == null)
                  caller = arg;
              else if (callee == null)
                  callee = arg;
              else if (startDate == null)
                  startDate = arg;
              else if (startTime == null)
                  startTime = arg;
              else if (endDate == null)
                  endDate = arg;
              else if (endTime == null)
                  endTime = arg;
              else if (print == null)
                  print = arg;
              else if (readMe == null)
                  readMe = arg;
          }

      }
      else {
          System.err.println("Missing command line arguments");
      }

      System.out.println(customer + "\n" + caller + "\n" + callee + "\n" +
              startDate + " " + startTime + "\n" + startDate + " " + endTime);
      System.exit(1);
  }

}