package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Slick on 8/11/15.
 */
public class SearchCallsView extends Composite implements ClickHandler {
    private Button searchCallsButton = new Button("Search");
    private TextBox startSearchTextBox = new TextBox();
    private TextBox endSearchTextBox = new TextBox();
    private FlexTable table = new FlexTable();

    public SearchCallsView () {
        VerticalPanel panel = new VerticalPanel();
        HorizontalPanel input = new HorizontalPanel();
        input.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        input.add(buildSearchLabels());
        input.add(buildSearchTextBoxes());

        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>Search the Phone Calls</h2>"));
        panel.add(input);
        searchCallsButton.setStyleName("small-button");
        panel.add(searchCallsButton);
        panel.add(table);
        searchCallsButton.addClickHandler(this);
        initWidget(panel);
    }

    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == searchCallsButton){
            searchPhoneCalls();
        }
    }

    private void searchPhoneCalls() {
        if (checkSearchTextBoxes()) {
            Window.alert("Please fill in all the required fields.");
            return;
        }
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        String start = startSearchTextBox.getText();
        String end = endSearchTextBox.getText();
        async.getPhoneCallsWithinRange(start, end, getPhoneCallsWithinRangeCallback());

    }
    private AsyncCallback<Collection<AbstractPhoneCall>> getPhoneCallsWithinRangeCallback() {
        return new AsyncCallback<Collection<AbstractPhoneCall>>() {
            @Override
            public void onFailure(Throwable ex) {
                if (ex instanceof RuntimeException) {
                    Window.alert(ex.toString());
                }
            }

            @Override
            public void onSuccess(Collection<AbstractPhoneCall> calls) {

                if (calls.size() == 0) {
                    displayEmpty();
                } else {
                    displaySearch(calls);
                }
            }
        };
    }

    private void displayEmpty() {
        Label empty = new Label("There are no phone calls that match this search query.");
        empty.setStyleName("label");
        table.setWidget(0, 0, empty);
    }

    private void displaySearch(Collection<AbstractPhoneCall> calls) {
        int counter = 0;
        PrettyPrinter printer = new PrettyPrinter();
        table.removeAllRows();
        Label label = null;
        for (AbstractPhoneCall call : calls) {
            try {
                label = new Label(printer.dumpWeb(call));
                label.setStyleName("label");
            } catch (IOException e) {
                Window.alert(e.toString());
            }
            table.setWidget(counter++, 0, label);
        }
    }

    private VerticalPanel buildSearchLabels() {
        VerticalPanel panel = new VerticalPanel();
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        panel.setSpacing(16);
        Label start = new Label("Start Date:");
        Label end = new Label("End Date:");
        start.setStyleName("label");
        end.setStyleName("label");
        panel.add(start);
        panel.add(end);
        return panel;
    }

    private VerticalPanel buildSearchTextBoxes() {
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(8);
        panel.add(startSearchTextBox);
        panel.add(endSearchTextBox);
        return panel;

    }

    private boolean checkSearchTextBoxes() {
        return (textBoxNotEmpty(startSearchTextBox) || textBoxNotEmpty(endSearchTextBox));
    }

    private boolean textBoxNotEmpty(TextBox textBox) {
        return (textBox.getText() == null || textBox.getText().equals(""));
    }

    public void setFocus() {
        startSearchTextBox.setFocus(true);
    }
}
