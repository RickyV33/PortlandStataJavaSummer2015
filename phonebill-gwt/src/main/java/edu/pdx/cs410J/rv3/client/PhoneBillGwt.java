package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A basic GWT class that makes sure that sends phone calls back to the server, lets the user search for specific phone calls,
 * and displays the entire phone bill.
 */
public class PhoneBillGwt implements EntryPoint, ClickHandler {
    /**
     * Changes the view to addPhoneCallView that lets you add a phone call
     */
    private Button addPhoneCallViewButton = new Button("Add Phone Call");
    /**
     * Changes the view to the phoneBillView that lets you display the entire phone bill in pretty text
     */
    private Button phoneBillViewButton = new Button("View Phone Bill");
    /**
     * Changes the view to the searchPhoneCallView that lets you search for specific phone calls
     */
    private Button searchPhoneCallViewButton = new Button("Search Phone Calls");
    /**
     * Holds the view that lets you add phone calls to a bill
     */
    private AddPhoneCallView addPhoneCallView = new AddPhoneCallView();
    /**
     * Holds the view that lets you view your phone bill
     */
    private PhoneBillView phoneBillView = new PhoneBillView();
    /**
     * Holds the view that lets you search for phone calls and display it
     */
    private SearchCallsView searchCallsView = new SearchCallsView();
    /**
     * Holds the deck panel that lets you navigate between all the different views
     */
    private DeckPanel viewPanel = new DeckPanel();


    /**
     * This is the main module that loads the web page.
     */
    public void onModuleLoad() {

        viewPanel.add(addPhoneCallView);
        viewPanel.add(phoneBillView);
        viewPanel.add(searchCallsView);
        addPhoneCallViewButton.addClickHandler(this);
        phoneBillViewButton.addClickHandler(this);
        searchPhoneCallViewButton.addClickHandler(this);
        MenuBar help = buildMenu();

        //Add everything to the root panels
        RootPanel rootPanel = RootPanel.get("view-display");
        RootPanel.get("help-display").add(help);
        rootPanel.add(viewPanel);
        rootPanel.setStyleName("display");
        RootPanel.get("view-buttons").add(buildButtons());
        viewPanel.showWidget(0);
    }

    /**
     * Builds the menu that has the README command in it
     *
     * @return the menu bar with help options
     */
    private MenuBar buildMenu() {
        MenuBar readMeMenu = new MenuBar(true);
        readMeMenu.addItem("Read me", displayReadMe());
        MenuBar help = new MenuBar();
        help.addItem("Help", readMeMenu);
        return help;
    }

    /**
     * Displays a window with the Read Me for the program.
     *
     * @return a command that executes the a window alert
     */
    private Command displayReadMe() {
        return new Command() {
            public void execute() {
                Window.alert("This website lets you manage an entire phone bill for one user. There are three separate views," +
                        " one for adding phone calls, one for viewing the phone bill, and the last for searching for specific" +
                        " phone calls that start within a specific time frame. Use the buttons to navigate to the different views.");
            }
        };
    }

    /**
     * The method that handles all the click events
     *
     * @param event the click event that will be handled
     */
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

    /**
     * Builds the buttons that transition to different views of the web page
     *
     * @return the horizontal panel that holds the buttons
     */
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
