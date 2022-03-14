package ru.gb.endlessnotes.repository;

import java.util.List;

public interface NoteSource {
    int size();
    List<NoteData> getAllCardData();
    NoteData getCardData(int position);
}
