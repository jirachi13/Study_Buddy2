package com.example.studybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "studybuddy.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_NOTE_ID = "id";
    private static final String COLUMN_NOTE_TITLE = "title";
    private static final String COLUMN_NOTE_CONTENT = "content";
    private static final String COLUMN_NOTE_DATE = "date";

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "id";
    private static final String COLUMN_TASK_TITLE = "title";
    private static final String COLUMN_TASK_DESCRIPTION = "description";
    private static final String COLUMN_TASK_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotesTable = "CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTE_TITLE + " TEXT, " +
                COLUMN_NOTE_CONTENT + " TEXT, " +
                COLUMN_NOTE_DATE + " TEXT)";
        db.execSQL(createNotesTable);

        String createTasksTable = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_TITLE + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_TASK_DATE + " TEXT)";
        db.execSQL(createTasksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Add a note
    public void addNote(String title, String content, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, title);
        values.put(COLUMN_NOTE_CONTENT, content);
        values.put(COLUMN_NOTE_DATE, date);
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    // Get all notes
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_CONTENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_DATE))
                );
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    // Add a task
    public void addTask(String title, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, title);
        values.put(COLUMN_TASK_DESCRIPTION, description);
        values.put(COLUMN_TASK_DATE, date);
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DATE))
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
