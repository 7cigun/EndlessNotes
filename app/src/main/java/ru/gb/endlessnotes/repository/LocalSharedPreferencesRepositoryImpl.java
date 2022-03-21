package ru.gb.endlessnotes.repository;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.gb.endlessnotes.R;

public class LocalSharedPreferencesRepositoryImpl implements NotesSource {
    private List<NoteData> dataSource;
    private SharedPreferences sharedPreferences;

    static final String KEY_CELL_1 = "cell_2";
    public static final String KEY_SP_2 = "key_sp_2";

    public LocalSharedPreferencesRepositoryImpl(SharedPreferences sharedPreferences){
        dataSource = new ArrayList<NoteData>();
        this.sharedPreferences = sharedPreferences;
    }

    public LocalSharedPreferencesRepositoryImpl init(){

        String savedNote = sharedPreferences.getString(KEY_CELL_1, null);
        if (savedNote != null) {
            Type type = new TypeToken<ArrayList<NoteData>>(){}.getType();
            dataSource = (new GsonBuilder().create().fromJson(savedNote, type));
        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public List<NoteData> getAllNotesData() {
        return dataSource;
    }

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public void clearNotesData() {
        dataSource.clear();
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData);
        applySharedPreferences();
    }

    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
        applySharedPreferences();
    }

    @Override
    public void updateNoteData(int position, NoteData newNoteData) {
        dataSource.set(position, newNoteData);
        applySharedPreferences();
    }

    void applySharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }
}
