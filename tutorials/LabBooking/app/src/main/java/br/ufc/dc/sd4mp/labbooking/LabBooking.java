package br.ufc.dc.sd4mp.labbooking;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LabBooking extends Activity {

    private EditText dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_booking);
        dateText = (EditText) findViewById(R.id.editText);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment) newFragment).setDateReference(dateText);
        newFragment.show(getFragmentManager(), "datePicker");
    }
}

