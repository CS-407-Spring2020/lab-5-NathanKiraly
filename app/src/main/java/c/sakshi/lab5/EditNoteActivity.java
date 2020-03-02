package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;


public class EditNoteActivity extends AppCompatActivity {

    EditText editText;
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Get the EditText view
        editText = (EditText) findViewById(R.id.noteText);

        // Get the intent info
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            // Get the note contents
            Note note = NotesActivity.notes.get(noteid);
            String noteContent = note.getContent();

            // Load in the note contents
            editText.setText(noteContent);
        }
    }

    public void saveClicked(View view) {
        // Get the note content
        String noteContent = editText.getText().toString();

        // Initialize SQLite DB
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", MODE_PRIVATE, null);

        // Initialize the DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // Get the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Save the info to the DB
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (NotesActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, noteContent, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(username, title, noteContent, date);
        }

        // Go back to Notes activity
        Intent intent = new Intent(getBaseContext(), NotesActivity.class);
        intent.putExtra("Username", username);

        startActivity(intent);

    }
}
