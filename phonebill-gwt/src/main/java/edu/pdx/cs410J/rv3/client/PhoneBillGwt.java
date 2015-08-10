package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint, ClickHandler {
    private Button addPhoneCallButton = new Button("Add");
    private Button addPhoneCallViewButton = new Button("Add Phone Call");
    private Button phoneBillViewButton = new Button("View Phone Bill");
    private Button searchPhoneCallViewButton = new Button("Search Phone Calls");
    private Button searchCallsButton = new Button("Search");
    private TextBox nameTextBox = new TextBox();
    private TextBox callerTextBox = new TextBox();
    private TextBox calleeTextBox = new TextBox();
    private TextBox startTextBox = new TextBox();
    private TextBox endTextBox = new TextBox();
    private TextBox startSearchTextBox = new TextBox();
    private TextBox endSearchTextBox = new TextBox();
    FlexTable table = new FlexTable();
    DeckPanel viewPanel = new DeckPanel();

    public void onModuleLoad() {
        viewPanel.add(buildInputScreenView());
        viewPanel.add(buildPhoneBillView());
        viewPanel.add(buildSearchCallsView());

        addPhoneCallViewButton.addClickHandler(this);
        phoneBillViewButton.addClickHandler(this);
        searchPhoneCallViewButton.addClickHandler(this);

        RootPanel rootPanel = RootPanel.get("view-display");
        rootPanel.add(viewPanel);
        rootPanel.setStyleName("display");
        RootPanel.get("view-buttons").add(buildButtons());
        viewPanel.showWidget(0);
        nameTextBox.setFocus(true);
    }

    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == addPhoneCallButton) {
            addPhoneCall();
        } else if (sender == searchCallsButton) {
            if (checkSearchTextBoxes()) {
                Window.alert("Please fill in all the required fields.");
                return;
            }
            PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        } else if (sender == addPhoneCallViewButton) {
            viewPanel.showWidget(0);
            nameTextBox.setFocus(true);
        } else if (sender == phoneBillViewButton) {
            viewPanel.showWidget(1);
        } else if (sender == searchPhoneCallViewButton) {
            viewPanel.showWidget(2);
            startSearchTextBox.setFocus(true);
        }
    }

    private void addPhoneCall() {
        if (checkInputTextBoxes()) {
            Window.alert("Please fill in all the required fields.");
            return;
        }
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        Collection<String> phoneCall = getPhoneCall();
        async.addPhoneCall(phoneCall, addPhoneCallCallback());
    }

    private Collection<String> getPhoneCall() {
        Collection<String> phoneCall = new ArrayList<>();
        phoneCall.add(nameTextBox.getText());
        phoneCall.add(callerTextBox.getText());
        phoneCall.add(calleeTextBox.getText());
        phoneCall.add(startTextBox.getText());
        phoneCall.add(endTextBox.getText());
        return phoneCall;
    }

    private AsyncCallback<AbstractPhoneCall> addPhoneCallCallback() {
        return new AsyncCallback<AbstractPhoneCall>() {
            @Override
            public void onFailure(Throwable ex) {
                if (ex instanceof RuntimeException) {
                    Window.alert(ex.toString());
                }
            }
            @Override
            public void onSuccess(AbstractPhoneCall phoneCall) {
                Window.alert("Added " + phoneCall.toString().toLowerCase());
                clearInputTextBoxes();
            }
        };
    }

    private void clearInputTextBoxes() {
        nameTextBox.setText("");
        callerTextBox.setText("");
        calleeTextBox.setText("");
        startTextBox.setText("");
        endTextBox.setText("");
        nameTextBox.setFocus(true);
    }

    private VerticalPanel buildInputScreenView() {
        VerticalPanel panel = new VerticalPanel();
        HorizontalPanel input = new HorizontalPanel();
        input.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        input.add(buildInputLabels());
        input.add(buildInputTextBoxes());
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>Add a Phone Call</h2>"));
        panel.add(input);
        addPhoneCallButton.setStyleName("small-button");
        panel.add(addPhoneCallButton);
        addPhoneCallButton.addClickHandler(this);
        return panel;
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
        return panel;
    }

    private VerticalPanel buildPhoneBillView() {
        VerticalPanel panel = new VerticalPanel();
        panel.add(new HTML("<h2>View the Phone Bill</h2>"));
        panel.add(table);
        return panel;
    }

    private VerticalPanel buildSearchCallsView() {
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
        searchCallsButton.addClickHandler(this);
        return panel;
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

    private HorizontalPanel buildButtons() {
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(3);
        addPhoneCallViewButton.setStyleName("normal-button");
        phoneBillViewButton.setStyleName("normal-button");
        searchPhoneCallViewButton.setStyleName("normal-button");
        panel.add(addPhoneCallViewButton);
        panel.add(phoneBillViewButton);
        panel.add(searchPhoneCallViewButton);
        return panel;
    }

    private boolean checkInputTextBoxes() {
        return (textBoxNotEmpty(nameTextBox) || textBoxNotEmpty(callerTextBox) || textBoxNotEmpty(calleeTextBox) ||
                textBoxNotEmpty(startTextBox) || textBoxNotEmpty(endTextBox));
    }

    private boolean checkSearchTextBoxes() {
        return (textBoxNotEmpty(startSearchTextBox) || textBoxNotEmpty(endSearchTextBox));
    }

    private boolean textBoxNotEmpty(TextBox textBox) {
        return (textBox.getText() == null || textBox.getText().equals(""));
    }
}
