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
 * Created by Slick on 8/11/15.
 */
public class AddPhoneCallView extends Composite implements ClickHandler{
    private Button addPhoneCallButton = new Button("Add");
    private Button clearButton = new Button("Clear");
    private TextBox nameTextBox = new TextBox();
    private TextBox callerTextBox = new TextBox();
    private TextBox calleeTextBox = new TextBox();
    private TextBox startTextBox = new TextBox();
    private TextBox endTextBox = new TextBox();

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
        buttons.add(addPhoneCallButton);
        buttons.add(clearButton);
        panel.add(buttons);
        addPhoneCallButton.addClickHandler(this);
        clearButton.addClickHandler(this);
        initWidget(panel);
    }

    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == addPhoneCallButton) {
            addPhoneCall();
        } else if (sender == clearButton) {
            clearInputTextBoxes();
        }
    }

    private void addPhoneCall() {
        if (checkInputTextBoxes()) {
            Window.alert("Please fill in all the required fields.");
            return;
        }
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        Collection<String> phoneCall = getPhoneCallInfo();
        async.addPhoneCall(phoneCall, addPhoneCallCallback(nameTextBox.getText()));
    }

    private Collection<String> getPhoneCallInfo() {
        Collection<String> phoneCall = new ArrayList<>();
        phoneCall.add(nameTextBox.getText());
        phoneCall.add(callerTextBox.getText());
        phoneCall.add(calleeTextBox.getText());
        phoneCall.add(startTextBox.getText());
        phoneCall.add(endTextBox.getText());
        return phoneCall;
    }

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

    private void clearInputTextBoxes() {
        nameTextBox.setText("");
        callerTextBox.setText("");
        calleeTextBox.setText("");
        startTextBox.setText("");
        endTextBox.setText("");
        setFocus();
    }

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

    private boolean checkInputTextBoxes() {
        return (textBoxNotEmpty(nameTextBox) || textBoxNotEmpty(callerTextBox) || textBoxNotEmpty(calleeTextBox) ||
                textBoxNotEmpty(startTextBox) || textBoxNotEmpty(endTextBox));
    }

    private boolean textBoxNotEmpty(TextBox textBox) {
        return (textBox.getText() == null || textBox.getText().equals(""));
    }

    public void setFocus() {
        nameTextBox.setFocus(true);
    }
}
