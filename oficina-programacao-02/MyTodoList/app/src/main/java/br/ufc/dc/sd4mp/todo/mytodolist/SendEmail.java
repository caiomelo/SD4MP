package br.ufc.dc.sd4mp.todo.mytodolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SendEmail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
    }


    public void cancel(View view) {
        finish();
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String emailTo = ((EditText) findViewById(R.id.sendEmailTo)).getText().toString();

        if (TextUtils.isEmpty(emailTo)) {
            Toast.makeText(getApplicationContext(), "Send to' field is mandatory!", Toast.LENGTH_SHORT).show();
        } else {

            intent.putExtra(Intent.EXTRA_SUBJECT, "My ToDo List");
            intent.putExtra(Intent.EXTRA_TEXT, listToDoTasks());
            intent.setType("plain/text");
            startActivity(Intent.createChooser(intent, "Sending email..."));
        }

    }

    private String listToDoTasks() {
        StringBuffer buffer = new StringBuffer();
        StringBuilder url = new StringBuilder(TasksProvider.URL);
        url.append("/status/todo");

        Uri notesURI = Uri.parse(url.toString());
        Cursor cursor = getContentResolver().query(notesURI, null, null, null, TasksProvider.ID);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(TasksProvider.ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(TasksProvider.TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(TasksProvider.DESCRIPTION)));
                task.setDate(cursor.getString(cursor.getColumnIndex(TasksProvider.DATE)));
                task.setStatus(Util.getStatus(cursor));
                buffer.append(task.toString());
                buffer.append(System.getProperty("line.separator"));
                buffer.append(System.getProperty("line.separator"));
            } while (cursor.moveToNext());
        }

        return buffer.toString();
    }
}
