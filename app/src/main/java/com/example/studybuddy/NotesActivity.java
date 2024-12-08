package com.example.studybuddy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Calendar;

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

        // Make the note date input clickable
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
            View noteView = LayoutInflater.from(this).inflate(R.layout.note_item, notesListContainer, false);

            TextView noteTitle = noteView.findViewById(R.id.noteTitle);
            TextView noteDescription = noteView.findViewById(R.id.noteDescription);

            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getContent());

            noteView.setOnClickListener(v -> showNoteDetails(note));
            notesListContainer.addView(noteView);
        }
    }

    private void showNoteDetails(Note note) {
        View noteDetailView = LayoutInflater.from(this).inflate(R.layout.note_details, null);

        TextView noteDetailTitle = noteDetailView.findViewById(R.id.noteDetailTitle);
        TextView noteDetailDescription = noteDetailView.findViewById(R.id.noteDetailDescription);
        View noteCompleteIcon = noteDetailView.findViewById(R.id.noteCompleteIcon);
        View noteDeleteIcon = noteDetailView.findViewById(R.id.noteDeleteIcon);
        Button viewNotesButton = noteDetailView.findViewById(R.id.viewNotesButton);

        noteDetailTitle.setText(note.getTitle());
        noteDetailDescription.setText(note.getContent());

        noteCompleteIcon.setOnClickListener(v -> {
            // Handle complete action (you can mark it or move it)
            displayNotes();
        });

        noteDeleteIcon.setOnClickListener(v -> {
            databaseHelper.deleteNote(note.getId());
            displayNotes();
        });

        viewNotesButton.setOnClickListener(v -> {
            // Close dialog and return to notes
        });

        new AlertDialog.Builder(this)
                .setView(noteDetailView)
                .setPositiveButton(null, null)
                .setNegativeButton(null, null)
                .show();
    }

    private void clearInputs() {
        noteTitleInput.setText("");
        noteContentInput.setText("");
        noteDateInput.setText("");
    }

    private void showDatePicker() {
        // Show DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    String selectedDate = (selectedMonth + 1) + "/" + selectedDayOfMonth + "/" + selectedYear;
                    noteDateInput.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
}
