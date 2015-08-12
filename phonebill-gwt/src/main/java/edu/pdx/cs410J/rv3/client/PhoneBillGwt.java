package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint, ClickHandler {
    private Button addPhoneCallViewButton = new Button("Add Phone Call");
    private Button phoneBillViewButton = new Button("View Phone Bill");
    private Button searchPhoneCallViewButton = new Button("Search Phone Calls");
    private AddPhoneCallView addPhoneCallView = new AddPhoneCallView();
    private PhoneBillView phoneBillView = new PhoneBillView();
    private SearchCallsView searchCallsView = new SearchCallsView();
    private DeckPanel viewPanel = new DeckPanel();


    public void onModuleLoad() {

        viewPanel.add(addPhoneCallView);
        viewPanel.add(phoneBillView);
        viewPanel.add(searchCallsView);
        addPhoneCallViewButton.addClickHandler(this);
        phoneBillViewButton.addClickHandler(this);
        searchPhoneCallViewButton.addClickHandler(this);

        RootPanel rootPanel = RootPanel.get("view-display");
        rootPanel.add(viewPanel);
        rootPanel.setStyleName("display");
        RootPanel.get("view-buttons").add(buildButtons());
        viewPanel.showWidget(0);
    }

    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == addPhoneCallViewButton) {
            viewPanel.showWidget(0);
            addPhoneCallView.setFocus();
        } else if (sender == phoneBillViewButton) {
            viewPanel.showWidget(1);
            phoneBillView.displayPhoneCalls();
        } else if (sender == searchPhoneCallViewButton) {
            viewPanel.showWidget(2);
            searchCallsView.setFocus();
        }
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
}
