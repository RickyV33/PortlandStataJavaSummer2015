package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PhoneBillServlet extends HttpServlet {
    private PhoneBill bill;

    /**
     * Handles an HTTP GET request from a client by writing the value of the customer, start, and end specified by 'start',
     * 'start', and 'end' HTTP parameter to the HTTP response. If the customers don't match, it will not retrieve. If
     * only the customer is present, it retrieves all the phone calls from that customer. If there is a start and end time,
     * it retrieves all the phone calls from within the time frame for the customer.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String customer = getParameter("customer", request);
        String start = getParameter("startTime", request);
        String end = getParameter("endTime", request);

        if (bill == null) {
            writeEmptyBill(response);
        } else {
            if (customer != null && !customer.equals(bill.getCustomer())) {
                writeIncorrectCustomer(customer, response);
            } else {
                if (customer != null && start == null && end == null) {
                    writeValue(customer, response);
                } else if (customer != null && start != null & end != null) {
                    writeRangeValues(start, end, response);
                } else {
                    writeAllMappings(response);
                }
            }
        }
    }

    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * customer, caller, callee, start, and end request parameters.  It writes the key/value pair to the
     * HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String customer = getParameter("customer", request);
        if (customer == null) {
            missingRequiredParameter(response, "customer");
            return;
        }

        //Retrieves the phone number values and checks if it's in the correct format
        String caller = getParameter("caller", request);
        if (caller == null) {
            missingRequiredParameter(response, "caller");
            return;
        } else {
            try {
                parseTelephone(caller);
            } catch (ParserException e) {
                writeIncorrectPhoneFormat("caller", e.getMessage(), response);
                return;
            }
        }

        String callee = getParameter("callee", request);
        if (callee == null) {
            missingRequiredParameter(response, "callee");
            return;
        } else {
            try {
                parseTelephone(callee);
            } catch (ParserException e) {
                writeIncorrectPhoneFormat("callee", e.getMessage(), response);
                return;
            }
        }


        //Parses the dates to be in the correct format
        String start = getParameter("startTime", request);
        Date startDate = null;
        if (start == null) {
            missingRequiredParameter(response, "startTime");
            return;
        } else {
            try {
                startDate = parseDate(start);
            } catch (ParseException e) {
                writeIncorrectDateFormat("startTime", response);
                return;
            }
        }

        String end = getParameter("endTime", request);
        Date endDate = null;
        if (end == null) {
            missingRequiredParameter(response, "endTime");
            return;
        } else {
            try {
                endDate = parseDate(end);
            } catch (ParseException e) {
                writeIncorrectDateFormat("endTime", response);
                return;
            }
        }

        if (bill == null) {
            bill = new PhoneBill(customer);
        } else if (!bill.getCustomer().equals(customer)) {
            writeIncorrectCustomer(customer, response);
            return;
        }
        PhoneCall call = new PhoneCall(caller, callee, startDate, endDate);
        bill.addPhoneCall(call);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.addedCall(call.toString()));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.missingRequiredParameter(parameterName));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
    }

    /**
     * Writes an error message about the incorrect format of the date to the HTTP response.
     *
     * @param response      where the error message is written.
     * @param parameterName the parameter that was incorrect so it can be displayed to the user
     * @throws IOException
     */
    private void incorrectDateFormat(HttpServletResponse response, String parameterName) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.incorrectDateFormat(parameterName));
        pw.flush();
        response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
    }


    /**
     * Parses the dateString string against two different string formats, MM/dd/yyyy HH:mm and "M/dd/yyyy HH:mm, to make
     * sure that they were entered in under the correct format. If it's not in the proper format, <code>ParseDateAndTime</code>
     * will throw a <code>ParseException</code>, give an error message.
     *
     * @param date The date and time string from the command line that will be parsed.
     * @return Returns the new <code>Date</code> object that was parsed if it was in the correct format.
     * @throws ParseException Throws this exception if the date is in the wrong format.
     */
    private Date parseDate(String date) throws ParseException {
        date = date.replace("pm", "PM").replace("am", "AM");

        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        format.setLenient(false);
        return format.parse(date);
    }


    /**
     * Parses the number string against a regex pattern (xxx-xxx-xxxx where x's are numbers) to verify that the phone
     * number was passed in under the correct format. If it's not in the proper format, <code>ParseTelephone</code>
     * will print an error message.
     *
     * @param number The phone number from the command line that will be parsed by the method.
     * @throws ParserException Throws this exception if the phone number is not in the correct format.
     */
    private void parseTelephone(String number) throws ParserException {

        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(number); //Check if the phone number is in the format xxx-xxx-xxxx

        if (!matcher.matches()) {
            throw new ParserException("Phone number must be in the format xxx-xxx-xxxx");
        }
    }

    /**
     * Writes the value of the given key to the HTTP response.
     * <p>
     * The text of the message is formatted with {@link Messages#getMappingCount(int)}
     */
    private void writeValue(String key, HttpServletResponse response) throws IOException {
        ArrayList<PhoneCall> value = bill.getPhoneCalls();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(value != null ? value.size() : 0));
        for (PhoneCall call : value) {
            pw.println(Messages.printPrettyPhoneCall(call));
        }

        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes the value of the given key that is within the range of startKey and endKey to the HTTP response.
     *
     * @param startKey when the call must have started by
     * @param endKey   the latest the phone call could have started by
     * @param response where the message is written to
     * @throws IOException
     */
    private void writeRangeValues(String startKey, String endKey, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        Date start = null;
        Date end = null;
        try {
            start = parseDate(startKey);
        } catch (ParseException e) {
            writeIncorrectDateFormat("startTime", response);
            return;
        }
        try {
            end = parseDate(endKey);
        } catch (ParseException e) {
            writeIncorrectDateFormat("endTime", response);
            return;
        }
        ArrayList<PhoneCall> rangeCalls = bill.getPhoneCallsWithinRange(start, end);

        if (rangeCalls.size() == 0) {
            pw.println("There are no calls that start within this range.");
        } else {
            for (PhoneCall call : rangeCalls) {
                pw.println(Messages.printPrettyPhoneCall(call));
            }
        }
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);


    }

    /**
     * Writes an error message to the HTTP response if there hasn't been any phone calls added to the phone bill yet.
     *
     * @param response Where the messsage is written to.
     * @throws IOException
     */
    private void writeEmptyBill(HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("The phone bill is empty! Add some phone calls first.");
        pw.flush();

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Writes all of the key/value pairs to the HTTP response.
     * <p>
     * The text of the message is formatted with
     */
    private void writeAllMappings(HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(bill.getPhoneCalls().size()));

        pw.println(bill.toString());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes a message stating that the date was not in the correct format to the HTTP response.
     *
     * @param date     stores which date parameter was incorrect
     * @param response where the message is written to
     * @throws IOException
     */
    private void writeIncorrectDateFormat(String date, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.incorrectDateFormat(date));
        pw.flush();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Writes a message stating that the phone number was not in the correct format to the HTTP response.
     *
     * @param number   stores which phone number parameter was incorrect
     * @param message  stores what message is written to the response
     * @param response where the response is written
     * @throws IOException
     */
    private void writeIncorrectPhoneFormat(String number, String message, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(number + ": " + message);
        pw.flush();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Writes a message stating that the customer to a phone call does not match the present bill's customer to the
     * HTTP response.
     *
     * @param customer the name of the customer they attempted to check
     * @param response where the messag eis written
     * @throws IOException
     */
    private void writeIncorrectCustomer(String customer, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.incorrectCustomer(customer));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     * <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

}
