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
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        ArrayList<String> argList = new ArrayList<String>();
        Collections.addAll(argList, args);
        new Project4().run(argList);
    }

    private void run(ArrayList<String> args) {

        StringBuilder hostName = null;
        StringBuilder portString = null;
        String customer = null;
        String caller = null;
        String callee = null;
        String startDate = null;
        String startTime = null;
        String startMeridian = null;
        String endDate = null;
        String endTime = null;
        String endMeridian = null;
        boolean verbose = false;
        boolean search = false;

        if (args.size() == 0) {
            usage(MISSING_ARGS);
        }
        parseReadMe(args);
        verbose = parsePrint(args);
        search = parseSearch(args);
        parseHostAndPort(hostName, portString, args);

        int port;
        try {
            port = Integer.parseInt(portString.toString());

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName.toString(), port);

        HttpRequestHelper.Response response;
        try {
            if (search) {

            } else {
               response = client.addCall(args);
            }
            if (key == null) {
                // Print all key/value pairs
                response = client.getAllKeysAndValues();

            } else if (value == null) {
                // Print all values of key
                response = client.getValues(key);

            } else {
                // Post the key/value pair
                response = client.addKeyValuePair(key, value);
            }

            checkResponseCode(HttpURLConnection.HTTP_OK, response);

        } catch (IOException ex) {
            error("While contacting server: " + ex);
            return;
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
        System.out.println("Author: Ricky Valencia\n Assignment: Project 1\n" +
                "This program takes in command line arguments which include customer's name, caller phone number, callee\n" +
                "phone number, the start date and time of the call, and when the call ended. The Order of the arguments is\n" +
                "important and they can be followed by the optional tags -print and -README, which can be in any order. This\n" +
                "program will parse the CL arguments and check to make sure they are in the correct format. If the user \n" +
                "inputs the -print tag then it will print a description of the call. If the user adds the -README tag, then\n" +
                "it will print a description of the program without executing the rest of the program. It can read and write\n " +
                "the phone call to the file if the -textFile tag exists with a filename. Now, it can use a -pretty tag that" +
                "will display the contents of the phone bill in a text file or the output stream in a neat, pretty format.");
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

    private boolean parseSearch(ArrayList<String> list) {
        if (list.contains("-search")) {
            list.remove("-search");
            return true;
        }
        return false;
    }

    private void parseHostAndPort(StringBuilder host, StringBuilder port, ArrayList<String> list) {
        if (list.contains("-host") && list.contains("-port")) {
            int hostIndex = list.indexOf("-host") + 1;
            int portIndex = list.indexOf("-port") + 1;
            host.append(list.get(hostIndex));
            port.append(list.get(portIndex));
            list.remove(hostIndex-1);
            list.remove(hostIndex);
            list.remove(portIndex-1);
            list.remove(portIndex);
        } else if(list.contains("-host")) {
            usage("Missing port!");
        } else if(list.contains("-port")) {
            usage("Missing the hostname!");
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
        err.println("usage: java Project4 host port [key] [value]");
        err.println("  host    Host of web server");
        err.println("  port    Port of web server");
        err.println("  key     Key to query");
        err.println("  value   Value to add to server");
        err.println();
        err.println("This simple program posts key/value pairs to the server");
        err.println("If no value is specified, then all values are printed");
        err.println("If no key is specified, all key/value pairs are printed");
        err.println();

        System.exit(1);
    }
}