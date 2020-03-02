package c.sakshi.lab5;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import android.database.Cursor;

public class DBHelper {
    SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, " +
                "username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");

    }

    public ArrayList<Note> readNotes(String username) {
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM notes WHERE username LIKE '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        ArrayList<Note> notes = new ArrayList<>();

        while (!c.isAfterLast()) {
            String date = c.getString(dateIndex);
            String title = c.getString(titleIndex);
            String content = c.getString(contentIndex);

            Note note = new Note(date, username, title, content);
            notes.add(note);

            c.moveToNext();
        }

        c.close();
        sqLiteDatabase.close();

        return notes;
    }

    public void saveNotes(String username, String title, String content, String date) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO notes (username, date, title, content) " +
                "VALUES ('%s', '%s', '%s', '%s')", username, date, title, content));
    }

    public void updateNote(String username, String title, String content, String date) {
        createTable();
        sqLiteDatabase.execSQL(String.format("UPDATE notes SET content = '%s', date = '%s' " +
                "WHERE username == '%s' AND title == '%s'", content, date, username, title));
    }

}
