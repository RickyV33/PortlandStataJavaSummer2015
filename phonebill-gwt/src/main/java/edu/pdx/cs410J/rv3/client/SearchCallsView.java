package edu.pdx.cs410J.rv3.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Slick on 8/11/15.
 */
public class SearchCallsView extends Composite implements ClickHandler {
    private Button searchCallsButton = new Button("Search");
    private TextBox startSearchTextBox = new TextBox();
    private TextBox endSearchTextBox = new TextBox();

    public SearchCallsView () {
        VerticalPanel panel = new VerticalPanel();
        HorizontalPanel input = new HorizontalPanel();
        input.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        input.add(buildSearchLabels());
        input.add(buildSearchTextBoxes());

        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(new HTML("<h2>gTSearch the Phone Calls</h2>"));
        panel.add(input);
        searchCallsButton.setStyleName("small-button");
        panel.add(searchCallsButton);
        searchCallsButton.addClickHandler(this);
        initWidget(panel);
    }

    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == searchCallsButton){
            if (checkSearchTextBoxes()) {
                Window.alert("Please fill in all the required fields.");
                return;
            }
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

}
