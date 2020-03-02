package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import org.w3c.dom.Text;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ListView;

public class NotesActivity extends AppCompatActivity {

    TextView welcomeText;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // Get the username from the intent
        final Intent intent = getIntent();
        String username = intent.getStringExtra("Username");

        // Set the welcome text
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome " + username + "!");

        // Get the SQLLite DB instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", MODE_PRIVATE, null);

        // Load the notes using DBHelper
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        // Get an array list of strings
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // Use ListView view to display the notes
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);

        // Add onItemClickListener for the notes
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Move to the Edit Note activity
                Intent intent1 = new Intent(getBaseContext(), EditNoteActivity.class);
                intent1.putExtra("noteid", position);
                startActivity(intent1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_item:
                // Remove the user from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();

                // Move back to the main activity
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.addnote_item:
                // Move to the Edit Note activity
                Intent intent1 = new Intent(getBaseContext(), EditNoteActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
