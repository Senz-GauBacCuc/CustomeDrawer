package com.example.customdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private ArrayList<Note> noteList;
    private Context context;
    private IClickedNoteListener miClickedNoteListener;
    public interface IClickedNoteListener{
        void onClickNote(Note note);
    }

    public NoteAdapter(ArrayList<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public Note getNote(int position){
        Note note = noteList.get(position);
        return note;
    }

    public void addNote(Note note){
         noteList.add(note);
         notifyDataSetChanged();
    }


    public void addLateNote(){
        //TODO: add Note with "Late" status
    }

    public void removeNote(int position){
        noteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteList.size());
    }

    /*public static Fragment newInstance(Fragment fragment) {
        FeedFragment fragment = new CreditCardDetail();
        Bundle args = new Bundle();
        args.putParcelable(CREDIT_KEY,creditCardItem);
        fragment.setArguments(args);
        return fragment;
    }*/

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            Note note = noteList.get(position);
            holder.name.setText(note.getName());
            holder.date.setText(note.getDate());

    }

    @Override
    public int getItemCount() {
        return noteList.size(); }

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

