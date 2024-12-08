package com.example.studybuddy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout notesListContainer;
    private EditText noteTitleInput, noteContentInput;
    private TextView noteDateInput;
    private Button addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        databaseHelper = new DatabaseHelper(this);
        notesListContainer = findViewById(R.id.notesListContainer);
        noteTitleInput = findViewById(R.id.noteTitleInput);
        noteContentInput = findViewById(R.id.noteContentInput);
        noteDateInput = findViewById(R.id.noteDateInput);
        addNoteButton = findViewById(R.id.addNoteButton);

        noteDateInput.setOnClickListener(v -> showDatePicker());

        addNoteButton.setOnClickListener(v -> {
            String title = noteTitleInput.getText().toString().trim();
            String content = noteContentInput.getText().toString().trim();
            String date = noteDateInput.getText().toString().trim();

            if (!title.isEmpty() && !content.isEmpty() && !date.isEmpty()) {
                databaseHelper.addNote(title, content, date);
                displayNotes();
                clearInputs();
            }
        });

        displayNotes();
    }

    private void displayNotes() {
        notesListContainer.removeAllViews();
        List<Note> notes = databaseHelper.getAllNotes();
        for (Note note : notes) {
            addNoteToView(note);
        }
    }

    private void addNoteToView(Note note) {
        TextView noteView = new TextView(this);
        noteView.setText(note.getTitle() + "\n" + note.getContent() + "\n" + note.getDate());
        noteView.setPadding(16, 16, 16, 16);
        noteView.setTextSize(16);
        notesListContainer.addView(noteView);
    }

    private void clearInputs() {
        noteTitleInput.setText("");
        noteContentInput.setText("");
        noteDateInput.setText("");
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = (month1 + 1) + "/" + dayOfMonth + "/" + year1;
            noteDateInput.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }
}
