package com.example.notespro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class NotesDetailActivity extends AppCompatActivity {

    EditText noteTitleEditText, noteContentEditText;
    ImageButton saveNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail);

        noteTitleEditText = findViewById(R.id.notes_title_editText);
        noteContentEditText = findViewById(R.id.notes_content_editText);
        saveNoteBtn = findViewById(R.id.save_note_btn);

        saveNoteBtn.setOnClickListener((v)->saveNote());
    }

    void saveNote()
    {
        String title = noteTitleEditText.getText().toString();
        String content = noteContentEditText.getText().toString();

        if( title==null || title.isEmpty())
        {
            noteTitleEditText.setError("Title is required");
            return;
        }
    }
}