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
   * Returns the a dummy Phone Bill
   */
  AbstractPhoneCall addPhoneCall(Collection<String> args) throws RuntimeException;

  AbstractPhoneBill getPhoneBill() throws RuntimeException;

  Collection<AbstractPhoneCall> getPhoneCallsWithinRange(String start, String end) throws RuntimeException;
}
