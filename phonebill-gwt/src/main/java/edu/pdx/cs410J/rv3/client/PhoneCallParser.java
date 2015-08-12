package edu.pdx.cs410J.rv3.client;

import com.google.gwt.i18n.shared.DateTimeFormat;
import edu.pdx.cs410J.ParserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Slick on 8/10/15.
 */
public class PhoneCallParser {

    /**
     * Parses the dateString string against two different string formats, MM/dd/yyyy HH:mm and "M/dd/yyyy HH:mm, to make
     * sure that they were entered in under the correct format. If it's not in the proper format, <code>ParseDateAndTime</code>
     * will throw a <code>ParserException</code>, give an error message.
     *
     * @param date The date and time string from the command line that will be parsed.
     * @return Returns the new <code>Date</code> object that was parsed if it was in the correct format.
     * @throws ParserException Throws this exception if the date is in the wrong format.
     */
    public Date parseDate(String date) throws ParserException {
        date = date.replace("pm", "PM").replace("am", "AM");

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new ParserException("Date must be in the format MM/dd/yyyy hh:mm a.");
        }
    }

    /**
     * Parses the number string against a regex pattern (xxx-xxx-xxxx where x's are numbers) to verify that the phone
     * number was passed in under the correct format. If it's not in the proper format, <code>ParseTelephone</code>
     * will print an error message.
     *
     * @param number The phone number from the command line that will be parsed by the method.
     * @throws ParserException Throws this exception if the phone number is not in the correct format.
     */
    public void parseTelephone(String number) throws ParserException {

        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(number); //Check if the phone number is in the format xxx-xxx-xxxx

        if (!matcher.matches()) {
            throw new ParserException("Phone number must be in the format xxx-xxx-xxxx");
        }
    }
}
