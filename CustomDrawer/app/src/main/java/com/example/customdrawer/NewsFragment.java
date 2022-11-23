package com.example.customdrawer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    private int draggedItemIndex;
    private ArrayList<Note> noteArrayLate;
    private RecyclerView recylerView_TodoLate;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataLoad();

        recylerView_TodoLate = view.findViewById(R.id.recylerView_TodoLate);
        recylerView_TodoLate.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerView_TodoLate.setHasFixedSize(true);

        NoteAdapter noteAdapter = new NoteAdapter(noteArrayLate, getContext());
        recylerView_TodoLate.setAdapter(noteAdapter);
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
                        //noteAdapter.getNote(viewHolder.getAdapterPosition());
                        noteAdapter.removeNote(viewHolder.getAdapterPosition());
                        break;
                    case ItemTouchHelper.START:
                        noteAdapter.removeNote(viewHolder.getAdapterPosition());
                        break;
                }

            }
        });
        helper.attachToRecyclerView(recylerView_TodoLate);

    }

    private void dataLoad() {
        noteArrayLate = Note.generateSingleNote();
    }
}
