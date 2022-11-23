package com.example.customdrawer;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private FloatingActionButton fab;
    private int draggedItemIndex;
    private RecyclerView recylerView_TodoWaiting;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        //dataLoad();

        fab = view.findViewById(R.id.fab);
        recylerView_TodoWaiting = view.findViewById(R.id.recylerView_TodoWaiting);
        recylerView_TodoWaiting.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerView_TodoWaiting.setHasFixedSize(true);

        NoteAdapter noteAdapter = new NoteAdapter(Note.generate4Notes(), getContext());
        recylerView_TodoWaiting.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.frameLayout){
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout, new MusicFragment()).commit();
                }
            }
        });

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
                        //TODO:  noteAdapter.getDoneList send to Fragment Feed
                        Note note = noteAdapter.getNote(viewHolder.getAdapterPosition());

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("NoteDone", note);

                        getParentFragmentManager().setFragmentResult("WaitToDone",bundle);

                        noteAdapter.removeNote(viewHolder.getAdapterPosition());
                        break;

                    case ItemTouchHelper.START:
                        noteAdapter.removeNote(viewHolder.getAdapterPosition());
                        break;
                }

            }
        });
        helper.attachToRecyclerView(recylerView_TodoWaiting);

    }
}
