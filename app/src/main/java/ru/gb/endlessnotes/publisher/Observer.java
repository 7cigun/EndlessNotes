package ru.gb.endlessnotes.publisher;

import ru.gb.endlessnotes.repository.NoteData;

public interface Observer {
    void receiveMessage(NoteData noteData);
}
