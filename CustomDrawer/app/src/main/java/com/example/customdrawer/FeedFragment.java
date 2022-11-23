package com.example.customdrawer;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private FloatingActionButton fab2;
    private int draggedItemIndex;
    private RecyclerView recylerView_TodoDone;
    private Note nte;

    public FeedFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        getParentFragmentManager().setFragmentResultListener("WaitToDone", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                nte = (Note) result.getSerializable("NoteDone");
                result.clear();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*if(noteArrayDone.size() == 0){
            dataLoad();
        }
        else{
            noteArrayDone.add(nte);
        }*/

        //dataLoad();


        fab2 = view.findViewById(R.id.floatingActionButton2) ;
        recylerView_TodoDone = view.findViewById(R.id.recylerView_TodoDone);
        recylerView_TodoDone.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerView_TodoDone.setHasFixedSize(true);

        //DoneNoteAdapter doneNoteAdapter = new DoneNoteAdapter(noteArrayDone, getContext());
        NoteAdapter noteAdapter = new NoteAdapter(Note.generateSingleNote(), getContext());
        recylerView_TodoDone.setAdapter(noteAdapter);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapter.addNote(nte);
            }
        });

        //noteAdapter.notifyDataSetChanged();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.END | ItemTouchHelper.START );
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                draggedItemIndex = viewHolder.getAdapterPosition();
                int targetIndex = target.getAdapterPosition();
                Collections.swap(noteAdapter.getNoteList(), draggedItemIndex, targetIndex);
                noteAdapter.notifyItemMoved(draggedItemIndex,targetIndex);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction){
                    case ItemTouchHelper.END:
                        //noteAdapter.removeNote(viewHolder.getAdapterPosition(), "From_Done");
                        noteAdapter.removeNote(viewHolder.getAdapterPosition());
                        break;
                    case ItemTouchHelper.START:
                        noteAdapter.removeNote(viewHolder.getAdapterPosition());
                        break;
                }

            }
        });
        helper.attachToRecyclerView(recylerView_TodoDone);

    }
}

