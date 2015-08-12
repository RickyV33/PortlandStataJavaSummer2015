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
 * This class handles the search calls view that lets the user search for calls that start and within a specific range.
 *
 * @author Ricky Valencia
 */
public class SearchCallsView extends Composite implements ClickHandler {
    /**
     * Takes in the data from the text boxes and searches for phone calls within that range
     */
    private Button searchCallsButton = new Button("Search");
    /**
     * Clears all the text boxes
     */
    private Button clearButton = new Button("Clear");
    /**
     * Holds the earliest the phone call can start by
     */
    private TextBox startSearchTextBox = new TextBox();
    /**
     * Holds the latest the phone call can start by
     */
    private TextBox endSearchTextBox = new TextBox();
    /**
     * Holds a list of all the phone calls
     */
    private FlexTable table = new FlexTable();

    /**
     * Constructs a SearchCallsView object with all the proper widgets
     */
    public SearchCallsView() {
        VerticalPanel panel = new VerticalPanel();
        HorizontalPanel input = new HorizontalPanel();
        HorizontalPanel buttons = new HorizontalPanel();

        buttons.setSpacing(5);
        input.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        input.add(buildSearchLabels());
        input.add(buildSearchTextBoxes());
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>Search the Phone Calls</h2>"));
        panel.add(input);
        searchCallsButton.setStyleName("small-button");
        clearButton.setStyleName("small-button");
        buttons.add(clearButton);
        buttons.add(searchCallsButton);
        panel.add(buttons);
        panel.add(table);
        searchCallsButton.addClickHandler(this);
        clearButton.addClickHandler(this);
        initWidget(panel);
    }

    /**
     * Handles the click events that are sent from all the buttons in the program
     *
     * @param event the click event that is going to be handled
     */
    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == searchCallsButton) {
            searchPhoneCalls();
        } else if (sender == clearButton) {
            startSearchTextBox.setText("");
            endSearchTextBox.setText("");
            setFocus();
        }
    }

    /**
     * Called from the click handler that retrieves the phone calls from the server that are within the range from
     * the text boxes.
     */
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

    /**
     * The async call to the server that retrieves all the correct phone calls. If it fails then it will give an alert
     * in a window
     *
     * @return the AsyncCallback object to be used by an async object
     */
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

    /**
     * Displays a label stating that there were no phone calls that match a specific range in the table
     */
    private void displayEmpty() {
        Label empty = new Label("There are no phone calls that match this search query.");
        empty.setStyleName("label");
        table.setWidget(0, 0, empty);
    }

    /**
     * DIsplays all the phone calls that matched the search query for the user
     *
     * @param calls the phone calls that were within the range specified by the user
     */
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

    /**
     * Builds all the labels for UI that will be alongside the text boxes
     *
     * @return the vertical panel that holds all the labels
     */
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

    /**
     * Builds all the text boxes that will be alongside the labels
     *
     * @return the vertical panel that holds all the text boxes
     */
    private VerticalPanel buildSearchTextBoxes() {
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(8);
        panel.add(startSearchTextBox);
        panel.add(endSearchTextBox);
        return panel;

    }

    /**
     * Checks the text boxes to make sure they aren't empty
     *
     * @return true if they are all empty
     */
    private boolean checkSearchTextBoxes() {
        return (textBoxEmpty(startSearchTextBox) || textBoxEmpty(endSearchTextBox));
    }

    /**
     * Checks the text in the text box argument to see if it's empty or null
     *
     * @param textBox the text box that is being checked if it's empty
     * @return true if it is empty, false otherwise
     */
    private boolean textBoxEmpty(TextBox textBox) {
        return (textBox.getText() == null || textBox.getText().equals(""));
    }

    /**
     * Sets the focus to the first text box
     */
    public void setFocus() {
        startSearchTextBox.setFocus(true);
    }
}
