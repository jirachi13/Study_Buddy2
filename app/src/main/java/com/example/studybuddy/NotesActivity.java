package com.example.studybuddy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            TextView noteDate = noteView.findViewById(R.id.noteDate);

            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getContent());
            noteDate.setText(note.getDate()); // Display the note date

            noteView.setOnClickListener(v -> showNoteDetails(note));
            notesListContainer.addView(noteView);
        }
    }

    private void showNoteDetails(Note note) {
        View noteDetailView = LayoutInflater.from(this).inflate(R.layout.note_details, null);

        TextView noteDetailTitle = noteDetailView.findViewById(R.id.noteDetailTitle);
        TextView noteDetailDate = noteDetailView.findViewById(R.id.noteDetailDate);  // Reference to the date TextView
        TextView noteDetailDescription = noteDetailView.findViewById(R.id.noteDetailDescription);
        ImageView noteCompleteIcon = noteDetailView.findViewById(R.id.noteCompleteIcon);
        ImageView noteDeleteIcon = noteDetailView.findViewById(R.id.noteDeleteIcon);
        Button viewNotesButton = noteDetailView.findViewById(R.id.viewNotesButton);

        // Set the values from the Note object
        noteDetailTitle.setText(note.getTitle());
        noteDetailDescription.setText(note.getContent());
        noteDetailDate.setText("Date: " + note.getDate());  // Dynamically set the date (get the date from the Note object)

        // Complete action (mark as complete)
        noteCompleteIcon.setOnClickListener(v -> {
            // Handle complete action
            Toast.makeText(NotesActivity.this, "Note marked as complete", Toast.LENGTH_SHORT).show();
            displayNotes(); // Refresh the notes list after completion
        });

        // Delete action (delete the note)
        noteDeleteIcon.setOnClickListener(v -> {
            databaseHelper.deleteNote(note.getId());
            Toast.makeText(NotesActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            displayNotes(); // Refresh the notes list after deletion
        });

        // View Notes button action
        viewNotesButton.setOnClickListener(v -> {
            // Close the dialog and return to the notes list
            Toast.makeText(NotesActivity.this, "Returning", Toast.LENGTH_SHORT).show();
            finish();  // Close the popup
        });

        // Show the dialog with the note details
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
