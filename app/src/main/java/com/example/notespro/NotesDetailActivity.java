package com.example.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;


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
        String noteTitle = noteTitleEditText.getText().toString();
        String noteContent = noteContentEditText.getText().toString();

        if( noteTitle==null || noteTitle.isEmpty())
        {
            noteTitleEditText.setError("Title is required");
            return;
        }
        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(Note note)
    {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Utility.showToast(NotesDetailActivity.this,"Save note successful");
                }
                else
                {
                    Utility.showToast(NotesDetailActivity.this,"Failure , please check again");
                }
            }
        });
    }


}