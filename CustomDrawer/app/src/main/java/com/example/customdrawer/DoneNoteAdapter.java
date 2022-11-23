package com.example.customdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DoneNoteAdapter extends RecyclerView.Adapter<DoneNoteAdapter.NoteViewHolder>{
    private ArrayList<Note> noteList_Done;
    private Context context;

    public DoneNoteAdapter(ArrayList<Note> noteList_Done, Context context) {
        this.noteList_Done = noteList_Done;
        this.context = context;
    }

    public void addDoneNote(Note note){
        noteList_Done.add(note);
        notifyDataSetChanged();
        /*notifyItemInserted(noteList_Done.size()-1);
        notifyItemRangeChanged(noteList_Done.size()-1, noteList_Done.size());*/
    }

    public ArrayList<Note> getNoteList() {
        return noteList_Done;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_list,parent,false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList_Done.get(position);
        holder.name.setText(note.getName());
        holder.date.setText(note.getDate());

    }

    @Override
    public int getItemCount() {
        return noteList_Done.size();
    }

    public void removeDoneNote(int adapterPosition) {
        noteList_Done.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, noteList_Done.size());
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, name.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }
        public void setNote(Note note) {
            name.setText(note.getName());
            date.setText(note.getDate());
        }
    }
}

