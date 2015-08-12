package edu.pdx.cs410J.rv3.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import java.util.Collection;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Adds a phone call to the phone bill on the server
   *
   * @param args the arguments for the phone call
   * @return the new phone call that was added
   * @throws RuntimeException if any of the arguments fail to parse
   */
  AbstractPhoneCall addPhoneCall(Collection<String> args) throws RuntimeException;

  /**
   * Retrieves the phone bill from the server
   *
   * @return the phone bill
   * @throws RuntimeException if the phone bill is null
   */
  AbstractPhoneBill getPhoneBill() throws RuntimeException;

  /**
   * Retrieves the calls that fall within a certain start date
   *
   * @param start the earliest the call an start by
   * @param end the latest the call can start by
   * @return A collection of phone calls from within the range
   * @throws RuntimeException If the dates fail to parse
   */
  Collection<AbstractPhoneCall> getPhoneCallsWithinRange(String start, String end) throws RuntimeException;
}
