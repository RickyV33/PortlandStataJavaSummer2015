package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sun.scenario.effect.GaussianShadow;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

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
    private TextBox nameTextBox = new TextBox();
    private TextBox callerTextBox = new TextBox();
    private TextBox calleeTextBox = new TextBox();
    private TextBox startTextBox = new TextBox();
    private TextBox endTextBox = new TextBox();
    FlexTable table = new FlexTable();
    DeckPanel viewPanel = new DeckPanel();

    public void onModuleLoad() {
        viewPanel.add(buildInputScreenView());
        viewPanel.add(buildPrintedCallsView());
        viewPanel.add(buildSearchCallsView());

        addPhoneCallButton.addClickHandler(this);
        addPhoneCallViewButton.addClickHandler(this);
        phoneBillViewButton.addClickHandler(this);
        searchPhoneCallViewButton.addClickHandler(this);
        RootPanel rootPanel = RootPanel.get("view-display");
        rootPanel.add(viewPanel);
        rootPanel.setStyleName("display");
        RootPanel.get("view-buttons").add(buildButtons());
        viewPanel.showWidget(0);
    }

    private VerticalPanel buildInputScreenView() {
        VerticalPanel panel = new VerticalPanel();
        HorizontalPanel input = new HorizontalPanel();
        input.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        input.add(buildLabels());
        input.add(buildTextBoxes());
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>Add a Phone Call</h2>"));
        panel.add(input);
        addPhoneCallButton.setStyleName("small-button");
        panel.add(addPhoneCallButton);
        /*
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        HTML title = new HTML("<h2>Add a Phone Call</h2>");
        panel.add(title);
        panel.add(buildLabelWithTextBox("Name:", nameTextBox));
        panel.add(buildLabelWithTextBox("Caller:", callerTextBox));
        panel.add(buildLabelWithTextBox("Callee:", calleeTextBox));
        panel.add(buildLabelWithTextBox("Start Date:", startTextBox));
        panel.add(buildLabelWithTextBox("End Date:", endTextBox));
        addPhoneCallButton.setStyleName("small-button");
        panel.add(addPhoneCallButton);
        */
        return panel;
    }

    private VerticalPanel buildLabels() {
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

    private VerticalPanel buildTextBoxes() {
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(8);
        panel.add(nameTextBox);
        panel.add(callerTextBox);
        panel.add(calleeTextBox);
        panel.add(startTextBox);
        panel.add(endTextBox);
        return panel;
    }

    private HorizontalPanel buildLabelWithTextBox(String label, TextBox textBox) {
        HorizontalPanel panel = new HorizontalPanel();
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        Label text = new Label(label);
        text.setStyleName("label");
        panel.add(text);
        panel.add(textBox);
        return panel;
    }

    private VerticalPanel buildPrintedCallsView() {
        VerticalPanel panel = new VerticalPanel();
        panel.add(new HTML("<h2>View the Phone Bill</h2>"));
        return panel;
    }

    private VerticalPanel buildSearchCallsView() {
        VerticalPanel panel = new VerticalPanel();
        panel.add(new HTML("<h2>Search the Phone Calls</h2>"));
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

    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == addPhoneCallButton) {
            PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
            if (checkTextBoxes()) {
                Window.alert("Please fill in all the fields");
                return;
            }
            Collection<String> test = new ArrayList<>();
            test.add("RICKY");
            async.addPhoneCall(test, new AsyncCallback<AbstractPhoneBill>() {

                public void onFailure(Throwable ex) {
                    Window.alert(ex.toString());
                }

                public void onSuccess(AbstractPhoneBill phonebill) {
                    StringBuilder sb = new StringBuilder(phonebill.toString());
                    Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
                    for (AbstractPhoneCall call : calls) {
                        sb.append(call);
                        sb.append("\n");
                    }
                    Window.alert(sb.toString());
                }
            });
        } else if (sender == addPhoneCallViewButton) {
            viewPanel.showWidget(0);
        } else if (sender == phoneBillViewButton) {
            viewPanel.showWidget(1);
        } else if (sender == searchPhoneCallViewButton) {
            viewPanel.showWidget(2);
        }
    }

    private boolean checkTextBoxes() {
        return (textBoxNotEmpty(nameTextBox) || textBoxNotEmpty(callerTextBox) || textBoxNotEmpty(calleeTextBox) ||
                textBoxNotEmpty(startTextBox) || textBoxNotEmpty(endTextBox));
    }

    private boolean textBoxNotEmpty(TextBox textBox) {
        return (textBox.getText() == null || textBox.getText().equals(""));
    }
}
