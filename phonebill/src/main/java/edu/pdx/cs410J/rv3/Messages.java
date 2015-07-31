package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class Messages {
    public static String getMappingCount(int count) {
        return String.format("Server contains %d phone calls.", count);
    }

    public static String printPrettyPhoneCall(AbstractPhoneCall call) {
        PrettyPrinter pp = new PrettyPrinter();
        try {
            return pp.dumpWeb(call);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String missingRequiredParameter(String parameterName) {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String incorrectDateFormat(String parameterName) {
        return String.format("%s should be in the format MM/ddd/yyyy hh:mm (am/pm)", parameterName);
    }

    public static String addedCall(String call) {
        return String.format("Added %s", call);
    }

    public static String incorrectCustomer(String parameterName) {
        return String.format("%s does not match the customer of the already existing bill!", parameterName);
    }

}
