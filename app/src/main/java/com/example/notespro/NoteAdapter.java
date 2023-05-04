package com.example.notespro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note,NoteAdapter.NoteViewHolder> {

    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.noteTitleTextView.setText(note.title);
        holder.noteContentTextView.setText(note.content);
        holder.noteTimestampTextView.setText(Utility.timestampToString(note.timestamp));

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_note_item,parent,false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitleTextView , noteContentTextView , noteTimestampTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.note_title_text_view);
            noteContentTextView = itemView.findViewById(R.id.note_content_text_view);
            noteTimestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);

        }
    }
}
