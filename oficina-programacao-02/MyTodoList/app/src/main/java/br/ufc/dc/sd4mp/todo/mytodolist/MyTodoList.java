package br.ufc.dc.sd4mp.todo.mytodolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MyTodoList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_todo_list);
    }

    public void addTask(View view) {
        EditText titleText = (EditText) findViewById(R.id.editTaskTitle);

        if (TextUtils.isEmpty(titleText.getText())) {
            Toast.makeText(getApplicationContext(), "Task's title is mandatory!", Toast.LENGTH_SHORT).show();

        } else {
            EditText descriptionText = (EditText) findViewById(R.id.editTaskDescription);

            ContentValues values = new ContentValues();
            values.put(TasksProvider.TITLE, titleText.getText().toString());
            values.put(TasksProvider.DESCRIPTION, descriptionText.getText().toString());
            values.put(TasksProvider.STATUS, Util.getStatus((RadioGroup) findViewById(R.id.buttons_radio)));
            values.put(TasksProvider.DATE, Util.formatDate(new Date(), Util.DATE_TIME));

            Uri uri = getContentResolver().insert(TasksProvider.CONTENT_URI, values);
            Toast.makeText(getApplicationContext(), "Task added", Toast.LENGTH_SHORT).show();
            titleText.setText("");
            descriptionText.setText("");
        }
    }

    public void listTasksActivity(View view) {
        Intent intent = new Intent(this, ListTasks.class);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(this, SendEmail.class);
        startActivity(intent);
    }
}
