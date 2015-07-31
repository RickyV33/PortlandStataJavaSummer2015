package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        ArrayList<String> argList = new ArrayList<String>();
        Collections.addAll(argList, args);
        new Project4().run(argList);
    }

    /**
     * This is the main method that runs the program
     *
     * @param args an ArrayList of all the command line arguments
     */
    private void run(ArrayList<String> args) {

        StringBuilder hostName = new StringBuilder();
        StringBuilder portString = new StringBuilder();
        String customer = null;
        String caller = null;
        String callee = null;
        String startDate = null;
        String startTime = null;
        String startMeridian = null;
        String endDate = null;
        String endTime = null;
        String endMeridian = null;
        String start = null;
        String end = null;
        boolean verbose = false;
        boolean search = false;

        if (args.size() == 0) {
            usage(MISSING_ARGS);
        }

        //Parse all the options
        parseReadMe(args);
        verbose = parsePrint(args);
        search = parseSearch(args);
        parseHostAndPort(hostName, portString, args);

        //Parses the port
        int port;
        try {
            port = Integer.parseInt(portString.toString());
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        //Fill all the data variables
        for (String arg : args) {
            if (customer == null) {
                customer = arg;
            } else if (caller == null) {
                caller = arg;
            } else if (callee == null) {
                callee = arg;
            } else if (startDate == null) {
                startDate = arg;
            } else if (startTime == null) {
                startTime = arg;
            } else if (startMeridian == null) {
                startMeridian = arg;
            } else if (endDate == null) {
                endDate = arg;
            } else if (endTime == null) {
                endTime = arg;
            } else if (endMeridian == null) {
                endMeridian = arg;
            }
        }

        //Checks to make sure the right amount of arguments are there
        if (search) {
            //Shifting the data appropriately since the caller and callee are not present
            start = caller + " " + callee + " " + startDate;
            end = startTime + " " + startMeridian + " " + endDate;
            parseCLSize(args, 7);

        } else {
            start = startDate + " " + startTime + " " + startMeridian;
            end = endDate + " " + endTime + " " + endMeridian;
            parseCLSize(args, 9);
        }

        //Uses the client to interact with the servlet
        PhoneBillRestClient client = new PhoneBillRestClient(hostName.toString(), port);
        HttpRequestHelper.Response response = null;

        try {
            if (args.size() == 1) {
                response = client.getAllPhoneCalls();
            } else if (search) {
                response = client.getRangePhoneCalls(customer, start, end);
            } else {
                response = client.addPhoneCall(customer, caller, callee, start, end);
            }
            checkResponseCode(HttpURLConnection.HTTP_OK, response);

        } catch (IOException ex) {
            error("While contacting server: " + ex);
            return;
        }

        if (verbose && !search) {
            System.out.println(String.format("Phone call from %s to %s from %s to %s.)", caller, callee, start, end));
        }

        System.out.println(response.getContent());

        System.exit(0);
    }

    /**
     * This method parses the command line arguments in list and checks to see if the -README tag was in it. If the tag is
     * present, then it will print a description of the project then exists the program.
     *
     * @param list Takes in the command line arguments as an ArrayList to parse it for the tag.
     */
    private void parseReadMe(ArrayList<String> list) {
        if (list.contains("-README")) {
            printDescription();
            System.exit(1);
        }
    }

    /**
     * Prints a description of the project by explaining how to use it, what it is, and any new features.
     */
    private void printDescription() {
        System.out.println("Author: Ricky Valencia\n Assignment: Project 4\n" +
                "This project is an extension of the phone bill application to support a phone bill server\n" +
                "that provides REST-ful web services to a phone bill client.\n");
        usage("This is how to use the program:");
    }

    /**
     * Parses the command line arguments and checks for the tags '-print'. If the '-print' tag is found,
     * then it returns true, otherwise false.
     *
     * @param list The list of command line arguments passed in as an <code>ArrayList</code> object.
     * @return If the '-print' tag is found, it returns true, otherwise false.
     */
    private boolean parsePrint(ArrayList<String> list) {
        //If the -print tag exists, set verbose to true and remove it from the array so only the arguments are left
        if (list.contains("-print")) {
            list.remove("-print"); //Removes the tag from the list of arguments
            return true;
        }
        return false;
    }

    /**
     * Parses the command line for the '-search' tag. If it has it, then it returns true and removes it fro m the list,
     * otherwise it returns false
     *
     * @param list is the command line argumetn
     * @return true if the command line arguments contain '-search' otherwise false
     */
    private boolean parseSearch(ArrayList<String> list) {
        if (list.contains("-search")) {
            list.remove("-search");
            return true;
        }
        return false;
    }

    /**
     * Parses the command line argumetns for the '-port' and '-host' tags. If they are included, then it removes them from
     * the list after adding them to the host and port arguments.
     *
     * @param host Where the host will be written to if it is included
     * @param port Where the port will be written to if it is included
     * @param list The list of command line arguments
     */
    private void parseHostAndPort(StringBuilder host, StringBuilder port, ArrayList<String> list) {
        if (list.contains("-host") && list.contains("-port")) {
            int hostIndex = list.indexOf("-host") + 1;
            int portIndex = list.indexOf("-port") + 1;
            host.append(list.get(hostIndex));
            port.append(list.get(portIndex));
            list.remove(hostIndex);
            list.remove(hostIndex - 1);
            portIndex = list.indexOf("-port") + 1;
            list.remove(portIndex);
            list.remove(portIndex - 1);
        } else if (list.contains("-host")) {
            usage("Missing port!");
        } else if (list.contains("-port")) {
            usage("Missing the hostname!");
        }
    }

    /**
     * This method parses the command line arguments and checks to see if it has the correct number of arguments for a
     * phone call. It must have 7 arguments in total and it will exit with an error message if it is under or over the
     * size of the argument passed in.
     *
     * @param comLineInput The command line arguments passed in by the main method
     * @param size         Holds the specific size the command line arguments should be.
     */
    private void parseCLSize(ArrayList<String> comLineInput, int size) {
        if (comLineInput.size() < size) {
            usage("Missing command line arguments.");
        } else if (comLineInput.size() > size) {
            usage("Too many command line arguments.");
        }
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     *
     * @param code     The expected status code
     * @param response The response from the server
     */
    private void checkResponseCode(int code, HttpRequestHelper.Response response) {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    private void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     *
     * @param message An error message to print
     */
    private void usage(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
        err.println("args are (in this order)");
        err.println("  customer   Person whose phone bill we're modeling");
        err.println("  callerNumber   Phone number of caller");
        err.println("  calleeNumber     Phone number of person who was called");
        err.println("  startTime   Date and time call began");
        err.println("  endTime   Date and time call began");
        err.println();
        err.println("options are (options may appear in any order)");
        err.println("-host hostname   Host computer on which the server runs");
        err.println("-port port   Port on which the server is listening");
        err.println("-search   Phone calls should be searched for");
        err.println("-print Prints a description of the new phone call");
        err.println("-README prints a README for this project and exits");
        err.println();

        System.exit(1);
    }
}