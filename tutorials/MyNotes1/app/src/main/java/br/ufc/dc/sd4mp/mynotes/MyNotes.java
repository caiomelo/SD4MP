package br.ufc.dc.sd4mp.mynotes;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MyNotes extends Activity {
    private NoteDAO noteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        noteDAO = new NoteDAO(this);
    }

    public void addNote(View view) {
        EditText titleText = (EditText) findViewById(R.id.editTextTitle);
        EditText contentText = (EditText) findViewById(R.id.editTextContent);
        Note note = new Note();
        note.setTitle(titleText.getText().toString());
        note.setContent(contentText.getText().toString());
        noteDAO.create(note);

        List<Note> notes = noteDAO.list();
        Iterator<Note> it = notes.iterator();
        StringBuffer buffer = new StringBuffer();
        while (it.hasNext()) {
            note = it.next();
            buffer.append(note.toString());
            buffer.append("\n");
        }
        TextView list = (TextView) findViewById(R.id.textViewNotes);
        list.setText(buffer.toString());

    }
}
