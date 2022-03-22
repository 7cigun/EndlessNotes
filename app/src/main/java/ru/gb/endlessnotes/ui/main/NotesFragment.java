package ru.gb.endlessnotes.ui.main;

import static ru.gb.endlessnotes.repository.LocalSharedPreferencesRepositoryImpl.KEY_SP_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

import ru.gb.endlessnotes.R;
import ru.gb.endlessnotes.publisher.Observer;
import ru.gb.endlessnotes.repository.LocalRepositoryImpl;
import ru.gb.endlessnotes.repository.LocalSharedPreferencesRepositoryImpl;
import ru.gb.endlessnotes.repository.PictureIndexConverter;
import ru.gb.endlessnotes.repository.RemoteFireStoreRepositoryImpl;
import ru.gb.endlessnotes.repository.NoteData;
import ru.gb.endlessnotes.repository.NotesSource;
import ru.gb.endlessnotes.repository.RemoteFireStoreResponse;
import ru.gb.endlessnotes.ui.MainActivity;
import ru.gb.endlessnotes.ui.editor.NoteFragment;

public class NotesFragment extends Fragment implements OnItemClickListener {

    NotesAdapter notesAdapter;
    NotesSource data;
    RecyclerView recyclerView;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSource();
        initRecycler(view);
        setHasOptionsMenu(true);
        initRadioGroup(view);
    }

    void setupSource(){
        switch (getCurrentSource()) {
            case SOURCE_ARRAY:
                data = new LocalRepositoryImpl(requireContext().getResources()).init();
                initAdapter();
                break;
            case SOURCE_SP:
                data = new LocalSharedPreferencesRepositoryImpl(requireContext().getSharedPreferences(KEY_SP_2, Context.MODE_PRIVATE)).init();
                initAdapter();
                break;
            case SOURCE_GF:
                data = new RemoteFireStoreRepositoryImpl().init(new RemoteFireStoreResponse() {
                    @Override
                    public void initialized(NotesSource notesSource) {
                        initAdapter();
                    }
                });
                initAdapter();
                break;
        }
    }

    private void initRadioGroup(View view) {
        view.findViewById(R.id.sourceArrays).setOnClickListener(listener);
        view.findViewById(R.id.sourceSP).setOnClickListener(listener);
        view.findViewById(R.id.sourceGF).setOnClickListener(listener);

        switch (getCurrentSource()) {
            case SOURCE_ARRAY:
                ((RadioButton) view.findViewById(R.id.sourceArrays)).setChecked(true);
                break;
            case SOURCE_SP:
                ((RadioButton) view.findViewById(R.id.sourceSP)).setChecked(true);
                break;
            case SOURCE_GF:
                ((RadioButton) view.findViewById(R.id.sourceGF)).setChecked(true);
                break;
        }
    }

    static final int SOURCE_ARRAY = 1;
    static final int SOURCE_SP = 2;
    static final int SOURCE_GF = 3;

    static String KEY_SP_S1 = "key_1";
    static String KEY_SP_S1_CELL1_C1 = "s1_cell1";

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sourceArrays:
                    setCurrentSource(SOURCE_ARRAY);
                    break;
                case R.id.sourceSP:
                    setCurrentSource(SOURCE_SP);
                    break;
                case R.id.sourceGF:
                    setCurrentSource(SOURCE_GF);
                    break;
            }
            setupSource();
        }
    };

    void setCurrentSource(int currentSource) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SP_S1_CELL1_C1, currentSource);
        editor.apply();
    }

    int getCurrentSource() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SP_S1_CELL1_C1, SOURCE_ARRAY);

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
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(NoteData noteData) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        data.addNoteData(noteData);
                        notesAdapter.notifyItemInserted(data.size() - 1);
                        recyclerView.smoothScrollToPosition(data.size() - 1);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(NoteFragment.newInstance(new NoteData("" + data.size(), "" + data.size(), PictureIndexConverter.getPictureByIndex(PictureIndexConverter.randomPictureIndex()), false, Calendar.getInstance().getTime())), true);
                //data.addNoteData(new NoteData("Новая заметка" + data.size(), "Текст новой заметки" + data.size(), PictureIndexConverter.getPictureByIndex(PictureIndexConverter.randomPictureIndex()), false, Calendar.getInstance().getTime()));

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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = notesAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update: {
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(NoteData noteData) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        data.updateNoteData(menuPosition, noteData);
                        notesAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(NoteFragment.newInstance(data.getNoteData(menuPosition)), true);
                return true;
            }
            case R.id.action_delete: {
                data.deleteNoteData(menuPosition);
                notesAdapter.notifyItemRemoved(menuPosition);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    void initAdapter() {
        if (notesAdapter == null)
        notesAdapter = new NotesAdapter(this);

        notesAdapter.setData(data);
        notesAdapter.setOnItemClickListener(this);
    }

    void initRecycler(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(2500);
        animator.setRemoveDuration(2500);
        recyclerView.setItemAnimator(animator);
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