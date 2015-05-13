package br.ufc.dc.sd4mp.todo.mytodolist;

import android.database.Cursor;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Caio on 5/12/15.
 */
public class Util {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static String formatDate(Date date, String dateFormatString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString
                , Locale.getDefault());
        return dateFormat.format(date);
    }

    public static boolean getStatus(Cursor cursor) {
        String statusStr = cursor.getString(cursor.getColumnIndex(TasksProvider.STATUS));
        return statusStr.equalsIgnoreCase("true") ? true : false;
    }

    public static String getStatus(RadioGroup statusRadio) {
        boolean status = false;
        int selectedId = statusRadio.getCheckedRadioButtonId();

        if (selectedId == R.id.button_radio_done) {
            status = true;
        }

        return Boolean.toString(status);
    }

    public static int getTaskIdFromToString(String task) {
        String id = task.substring(1,2);
        return Integer.valueOf(id);
    }
}
