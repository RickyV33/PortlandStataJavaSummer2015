package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Slick on 8/11/15.
 */
public class PhoneBillView extends Composite{
    FlexTable table = new FlexTable();

    public PhoneBillView() {
        VerticalPanel panel = new VerticalPanel();
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>View the Phone Bill</h2>"));
        panel.add(table);
        initWidget(panel);
    }


    public void displayPhoneCalls() {
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        async.getPhoneBill(getPhoneBillCallback());
    }

    private AsyncCallback<AbstractPhoneBill> getPhoneBillCallback() {
        return new AsyncCallback<AbstractPhoneBill>() {
            @Override
            public void onFailure(Throwable throwable) {
                Label empty = new Label("There are no calls in the phone bill yet.");
                empty.setStyleName("label");
                table.setWidget(0, 0, empty);
            }

            @Override
            public void onSuccess(AbstractPhoneBill bill) {
                int counter = 0;
                String customer = bill.getCustomer();
                Collection<AbstractPhoneCall> phoneCalls = bill.getPhoneCalls();
                PrettyPrinter printer = new PrettyPrinter();
                table.removeAllRows();
                Label label = new Label("Phone bill for " + customer + ":");
                label.setStyleName("label");
                table.setWidget(counter++, 0, label);
                for (AbstractPhoneCall call : phoneCalls) {
                    try {
                        label = new Label(printer.dumpWeb(call));
                        label.setStyleName("label");
                    } catch (IOException e) {
                        Window.alert(e.toString());
                    }
                    table.setWidget(counter++, 0, label);
                }
            }
        };
    }
}
