package br.ufc.dc.sd4mp.todo.mytodolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListTasks extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
    }

    public void listDone(View view) {
        listTasks(view, true, false);
    }

    public void listToDo(View view) {
        listTasks(view, false, false);
    }

    public void listAll(View view) {
        listTasks(view, true, true);
    }

    private void listTasks(View view, boolean status, boolean all) {
        ListView listView = (ListView) findViewById(R.id.listView);
        final List<Task> tasksList = new ArrayList<>();

        StringBuffer buffer = new StringBuffer();

        StringBuilder url = new StringBuilder(TasksProvider.URL);

        if (!all) {
            if (status) {
                url.append("/status/done");
            } else {
                url.append("/status/todo");
            }

        }

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
                tasksList.add(task);
            } while (cursor.moveToNext());
        }

        final ArrayAdapter<Task> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        listView.setAdapter(listAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {

                final int selectedItem = i;

                AlertDialog.Builder alert = new AlertDialog.Builder(ListTasks.this);

                alert.setMessage("What to do?");

                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task task = (Task) tasksList.get(selectedItem);
                        int deleted = getContentResolver().delete(TasksProvider.CONTENT_URI, "id = " + task.getId(), null);
                        tasksList.remove(selectedItem);
                        listAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                alert.show();
                return false;
            }
        });
    }


}
