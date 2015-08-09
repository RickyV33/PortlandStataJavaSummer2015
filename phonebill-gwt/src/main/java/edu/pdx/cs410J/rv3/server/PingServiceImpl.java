package edu.pdx.cs410J.rv3.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.rv3.client.PhoneBill;
import edu.pdx.cs410J.rv3.client.PhoneCall;
import edu.pdx.cs410J.rv3.client.PingService;

import java.lang.Override;
import java.util.Date;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService
{
  @Override
  public AbstractPhoneBill ping() {
    PhoneBill phonebill = new PhoneBill("Test");
    phonebill.addPhoneCall(new PhoneCall("1", "2", new Date(), new Date()));
    return phonebill;
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
