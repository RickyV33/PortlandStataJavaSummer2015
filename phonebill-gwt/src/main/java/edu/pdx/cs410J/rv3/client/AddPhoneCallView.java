package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class manages the page that lets you add information into text boxes in order add a phone call into the phone bill
 * on the server side.
 *
 * @author Ricky Valencia
 */
public class AddPhoneCallView extends Composite implements ClickHandler {
    /**
     * Collects the data from the text box and add it to the phone bill if it's valid.
     */
    private Button addPhoneCallButton = new Button("Add");
    /**
     * Clears all the text boxes.
     */
    private Button clearButton = new Button("Clear");
    /**
     * Stores the name of the customer.
     */
    private TextBox nameTextBox = new TextBox();
    /**
     * Stores the caller's phone number.
     */
    private TextBox callerTextBox = new TextBox();
    /**
     * Stores the callee's phone number.
     */
    private TextBox calleeTextBox = new TextBox();
    /**
     * Stores the start date of the phone call.
     */
    private TextBox startTextBox = new TextBox();
    /**
     * Stores the end date of the phone call.
     */
    private TextBox endTextBox = new TextBox();

    /**
     * Constructor that builds an AddPhoneCallView object with all the necessary labels, buttons, and text boxes.
     */
    public AddPhoneCallView() {
        VerticalPanel panel = new VerticalPanel();
        HorizontalPanel input = new HorizontalPanel();
        HorizontalPanel buttons = new HorizontalPanel();

        buttons.setSpacing(5);
        input.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        input.add(buildInputLabels());
        input.add(buildInputTextBoxes());
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>Add a Phone Call</h2>"));
        panel.add(input);
        addPhoneCallButton.setStyleName("small-button");
        clearButton.setStyleName("small-button");
        buttons.add(clearButton);
        buttons.add(addPhoneCallButton);
        panel.add(buttons);
        addPhoneCallButton.addClickHandler(this);
        clearButton.addClickHandler(this);
        initWidget(panel);
    }

    /**
     * Click handler that handles all the events from the buttons on this page.
     *
     * @param event The click event that is going to be handled.
     */
    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == addPhoneCallButton) {
            addPhoneCall();
        } else if (sender == clearButton) {
            clearInputTextBoxes();
        }
    }

    /**
     * Gathers the information from the text box and sends it to the server to create a phone call and add it to the
     * server.
     */
    private void addPhoneCall() {
        if (checkInputTextBoxes()) {
            Window.alert("Please fill in all the required fields.");
            return;
        }
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        Collection<String> phoneCall = getPhoneCallInfo();
        async.addPhoneCall(phoneCall, addPhoneCallCallback(nameTextBox.getText()));
    }

    /**
     * Collects all the information from the text boxes and stores it in a collection.
     *
     * @return A collection of all the phone call information from the text boxes.
     */
    private Collection<String> getPhoneCallInfo() {
        Collection<String> phoneCall = new ArrayList<>();
        phoneCall.add(nameTextBox.getText());
        phoneCall.add(callerTextBox.getText());
        phoneCall.add(calleeTextBox.getText());
        phoneCall.add(startTextBox.getText());
        phoneCall.add(endTextBox.getText());
        return phoneCall;
    }

    /**
     * AsyncCallback that sends a post request to add the phone call into the phone bill. It displays an error in a window
     * if the data was not valid.
     *
     * @param customer The name of the customer for the bill.
     * @return The Async callback object used by the async object.
     */
    private AsyncCallback<AbstractPhoneCall> addPhoneCallCallback(String customer) {
        final String cust = customer;
        return new AsyncCallback<AbstractPhoneCall>() {
            @Override
            public void onFailure(Throwable ex) {
                if (ex instanceof RuntimeException) {
                    Window.alert(ex.toString());
                }
            }

            @Override
            public void onSuccess(AbstractPhoneCall phoneCall) {
                Window.alert("Added " + phoneCall.toString().toLowerCase() + " to " + cust + "'s phone bill.");
            }
        };
    }

    /**
     * Clears the input of the text boxes and sets the focus to the first text box
     */
    private void clearInputTextBoxes() {
        nameTextBox.setText("");
        callerTextBox.setText("");
        calleeTextBox.setText("");
        startTextBox.setText("");
        endTextBox.setText("");
        setFocus();
    }

    /**
     * Builds all the labels that will go alongside the text boxes.
     *
     * @return The Vertical panel that holds all the labels.
     */
    private VerticalPanel buildInputLabels() {
        VerticalPanel panel = new VerticalPanel();
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        panel.setSpacing(16);
        Label name = new Label("Name:");
        Label caller = new Label("Caller:");
        Label callee = new Label("Callee:");
        Label start = new Label("Start Date:");
        Label end = new Label("End Date:");
        name.setStyleName("label");
        caller.setStyleName("label");
        callee.setStyleName("label");
        start.setStyleName("label");
        end.setStyleName("label");
        panel.add(name);
        panel.add(caller);
        panel.add(callee);
        panel.add(start);
        panel.add(end);
        return panel;
    }

    /**
     * Builds all the text boxes that will go alongside the labels.
     *
     * @return The Vertical panel that holds all the text boxes
     */
    private VerticalPanel buildInputTextBoxes() {
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(8);
        panel.add(nameTextBox);
        panel.add(callerTextBox);
        panel.add(calleeTextBox);
        panel.add(startTextBox);
        panel.add(endTextBox);
        setFocus();
        return panel;
    }

    /**
     * Checks all the text boxes to make sure they aren't empty.
     *
     * @return True if a text box is empty.
     */
    private boolean checkInputTextBoxes() {
        return (textBoxEmpty(nameTextBox) || textBoxEmpty(callerTextBox) || textBoxEmpty(calleeTextBox) ||
                textBoxEmpty(startTextBox) || textBoxEmpty(endTextBox));
    }

    /**
     * Checks a text box to see if it's empty.
     *
     * @param textBox The text box who's text is being checked.
     * @return True if it is an empty text box.
     */
    private boolean textBoxEmpty(TextBox textBox) {
        return (textBox.getText() == null || textBox.getText().equals(""));
    }

    /**
     * Sets the focus to the first text box in the page.
     */
    public void setFocus() {
        nameTextBox.setFocus(true);
    }
}
