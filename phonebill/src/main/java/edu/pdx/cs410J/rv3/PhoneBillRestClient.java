package edu.pdx.cs410J.rv3;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 *
 * @author Ricky Valencia
 * @version 1.0
 */
public class PhoneBillRestClient extends HttpRequestHelper {
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public PhoneBillRestClient(String hostName, int port) {
        this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
    }

    /**
     * Retrieves all the phone calls from the servlet
     *
     * @return all the phone calls within the servlet if there are any
     * @throws IOException
     */
    public Response getAllPhoneCalls() throws IOException {
        return get(this.url);
    }

    /**
     * Retreives all the phone calls that start between start and end.
     *
     * @param customer who the phone calls belong to
     * @param start    when the phone calls must have started by
     * @param end      the final day that the phone calls must have started by to still be included
     * @return all the phone calls within the specific range start and end
     * @throws IOException
     */
    public Response getRangePhoneCalls(String customer, String start, String end) throws IOException {
        return get(this.url, "customer", customer, "startTime", start, "endTime", end);
    }

    /**
     * POSTS a new phone call into the servlet based on the passed in parameters
     *
     * @param customer stores the name of the customer for the caller
     * @param caller   stores the caller's phone number
     * @param callee   stores the callee's phone number
     * @param start    stores when the phone call started
     * @param end      stores when the phone call ended
     * @return returns the response from the servlet based on what happened with the data (validation)
     * @throws IOException
     */
    public Response addPhoneCall(String customer, String caller, String callee, String start, String end) throws IOException {
        return post(this.url, "customer", customer, "caller", caller, "callee", callee, "startTime", start, "endTime", end);
    }
}
