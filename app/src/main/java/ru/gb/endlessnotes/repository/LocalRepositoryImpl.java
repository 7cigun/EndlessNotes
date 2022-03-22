package ru.gb.endlessnotes.repository;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.gb.endlessnotes.R;

public class LocalRepositoryImpl implements NotesSource {

        private List<NoteData> dataSource;
        private Resources resources;

    public LocalRepositoryImpl(Resources resources){
            dataSource = new ArrayList<NoteData>();
            this.resources = resources;
        }

        public LocalRepositoryImpl init(){
            String[] titles = resources.getStringArray(R.array.titles);
            String[] descriptions = resources.getStringArray(R.array.descriptions);
            TypedArray pictures = resources.obtainTypedArray(R.array.pictures);

            for (int i = 0; i < titles.length; i++) {
                dataSource.add(new NoteData(titles[i],descriptions[i], pictures.getResourceId(i,0),false, Calendar.getInstance().getTime()));
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
        }

        @Override
        public void deleteNoteData(int position) {
            dataSource.remove(position);
        }

        @Override
        public void updateNoteData(int position, NoteData newNoteData) {
            dataSource.set(position, newNoteData);
        }
}
