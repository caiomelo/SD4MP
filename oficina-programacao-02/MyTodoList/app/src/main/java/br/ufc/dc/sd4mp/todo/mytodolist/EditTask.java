package br.ufc.dc.sd4mp.todo.mytodolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;


public class EditTask extends Activity {

    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Bundle extras = getIntent().getExtras();

        taskId = (int) extras.get("id");
        String titleText = (String) extras.get("title");
        String descriptionText = (String) extras.get("description");
        String status = (String) extras.get("status");

        EditText titleInput = (EditText) findViewById(R.id.editTaskTitle2);
        titleInput.setText(titleText, null);

        EditText descriptionInput = (EditText) findViewById(R.id.editTaskDescription2);
        descriptionInput.setText(descriptionText, null);

        setRadioButtons(status);
    }

    private void setRadioButtons(String status) {
        boolean statusB = Boolean.valueOf(status);

        if (statusB) {
            RadioButton buttonDone = (RadioButton) findViewById(R.id.button_radio_done2);
            buttonDone.setChecked(true);
        } else {
            RadioButton buttonTodo = (RadioButton) findViewById(R.id.button_radio_todo2);
            buttonTodo.setChecked(true);
        }
    }

    public void updateTask(View view) {
        EditText titleText = (EditText) findViewById(R.id.editTaskTitle2);
        EditText descriptionText = (EditText) findViewById(R.id.editTaskDescription2);
        ContentValues values = new ContentValues();
        values.put(TasksProvider.TITLE, titleText.getText().toString());
        values.put(TasksProvider.DESCRIPTION, descriptionText.getText().toString());
        values.put(TasksProvider.STATUS, Util.getStatus((RadioGroup) findViewById(R.id.buttons_radio2)));
        values.put(TasksProvider.DATE, Util.formatDate(new Date(), Util.DATE_TIME));

        int updated = getContentResolver().update(TasksProvider.CONTENT_URI, values, "id = " + taskId, null);

        if (updated > 0) {
            Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
