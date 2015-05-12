package br.ufc.dc.sd4mp.todo.mytodolist;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MyTodoList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_todo_list);
    }

    public void addTask(View view) {
        EditText titleText = (EditText) findViewById(R.id.editTaskTitle);
        EditText contentText = (EditText) findViewById(R.id.editTaskDescription);
        ContentValues values = new ContentValues();
        values.put(TasksProvider.TITLE, titleText.getText().toString());
        values.put(TasksProvider.DESCRIPTION, contentText.getText().toString());

        Uri uri = getContentResolver().insert(TasksProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void listTasks(View view) {
        StringBuffer buffer = new StringBuffer();
        String URL = TasksProvider.URL;
        Uri notesURI = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(notesURI, null, null, null, TasksProvider.ID);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(TasksProvider.ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(TasksProvider.TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(TasksProvider.DESCRIPTION)));
                buffer.append(task.toString());
                buffer.append("\n\n");
            } while (cursor.moveToNext());
        }
        TextView list = (TextView) findViewById(R.id.textViewTasks);
        list.setText(buffer.toString());
    }
}
