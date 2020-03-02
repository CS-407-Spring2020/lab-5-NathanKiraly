package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if a user was previously logged in
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (username.equals("")) {
            setContentView(R.layout.activity_main);
        } else {
            moveToNotesActivity(username);
        }

        // Get the EditTexts assigned
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
    }

    public void loginClicked(View view) {
        String username = usernameText.getText().toString();

        // Store the username in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();

        moveToNotesActivity(username);
    }

    private void moveToNotesActivity(String username) {
        Intent intent = new Intent(getBaseContext(), NotesActivity.class);
        intent.putExtra("Username", username);

        startActivity(intent);
    }

}
