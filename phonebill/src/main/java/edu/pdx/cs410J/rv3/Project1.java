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
      String [] tag;

      ArrayList<String> comLineInput;
      boolean verbose = false;
      int dataIndexStart;
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
          System.out.println("This project yada yada ");
          System.exit(2);
      }
      if (comLineInput.contains("-print")) {
          verbose = true;
          comLineInput.remove("-print");
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
          else if (print == null)
              print = var;
          else if (readMe == null)
              readMe = var;
      }

      System.out.println(customer + "\n" + caller + "\n" + callee + "\n" +
              startDate + " " + startTime + "\n" + startDate + " " + endTime);
  }

    public void printDescription() {
       System.out.println("This project yada yada ");
    }

}