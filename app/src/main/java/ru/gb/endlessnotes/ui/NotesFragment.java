package ru.gb.endlessnotes.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.gb.endlessnotes.R;
import ru.gb.endlessnotes.repository.LocalRepositoryImpl;
import ru.gb.endlessnotes.repository.NoteData;
import ru.gb.endlessnotes.repository.NotesSource;

public class NotesFragment extends Fragment implements OnItemClickListener {

    NotesAdapter notesAdapter;
    NotesSource data;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                data.addNoteData(new NoteData("Новая заметка" + data.size(), "Текст новой заметки" + data.size(), R.drawable.acryl, false));
                notesAdapter.notifyItemInserted(data.size() - 1);
                return true;
            }
            case R.id.action_clear: {
                data.clearNotesData();
                notesAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void initAdapter() {
        notesAdapter = new NotesAdapter();
        data = new LocalRepositoryImpl(requireContext().getResources()).init();
        notesAdapter.setData(data);
        notesAdapter.setOnItemClickListener(this);
    }

    void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);
    }

    String[] getData() {
        String[] data = getResources().getStringArray(R.array.titles);
        return data;
    }

    @Override
    public void onItemClick(int position) {
        String[] data = getData();
        Toast.makeText(requireContext(), " Нажали на " + data[position], Toast.LENGTH_SHORT).show();
    }
}