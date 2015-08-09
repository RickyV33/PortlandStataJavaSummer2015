package edu.pdx.cs410J.rv3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
    private Button addPhoneCallButton;
    private TextBox nameTextBox;
    private TextBox callerTextBox;
    private TextBox calleeTextBox;
    private TextBox startTextBox;
    private TextBox endTextBox;
    private Label nameLabel;
    private Label callerLabel;
    private Label calleeLabel;
    private Label startLabel;
    private Label endLabel;

    private void setup() {
        this.addPhoneCallButton = new Button("Add Phone Call");
        this.nameTextBox = new TextBox();
        this.callerTextBox = new TextBox();
        this.calleeTextBox = new TextBox();
        this.startTextBox = new TextBox();
        this.endTextBox = new TextBox();
        this.nameLabel = new Label("Name:");
        this.callerLabel = new Label("Caller:");
        this.calleeLabel = new Label("Callee:");
        this.startLabel = new Label("Start Date:");
        this.endLabel = new Label("End Date:");
    }
    public void onModuleLoad() {
        Button button = new Button("Ping Server");
        button.addClickHandler(new ClickHandler() {
            public void onClick( ClickEvent clickEvent )
            {
                PhoneBillServiceAsync async = GWT.create( PhoneBillService.class );
                async.ping( new AsyncCallback<AbstractPhoneBill>() {

                    public void onFailure( Throwable ex )
                    {
                        Window.alert(ex.toString());
                    }

                    public void onSuccess( AbstractPhoneBill phonebill )
                    {
                        StringBuilder sb = new StringBuilder( phonebill.toString() );
                        Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
                        for ( AbstractPhoneCall call : calls ) {
                            sb.append(call);
                            sb.append("\n");
                        }
                        Window.alert( sb.toString() );
                    }
                });
            }
        });
        RootPanel rootPanel = RootPanel.get();
        rootPanel.add(button);
      }
}


