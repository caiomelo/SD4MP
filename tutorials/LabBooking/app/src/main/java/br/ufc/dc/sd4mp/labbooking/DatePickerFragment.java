package br.ufc.dc.sd4mp.labbooking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText dateText;

    public DatePickerFragment() {
    }

    public void setDateReference(EditText dateText) {
        this.dateText = dateText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) { // Use the current date as the default values for the picker final Calendar calendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, final int year, final int month, final int day) {
        dateText.post(new Runnable() {
            @Override
            public void run() {
                dateText.setText("" + day + "/" + (month + 1) + "/" + year);
            }
        });
    }
}