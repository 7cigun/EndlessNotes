package ru.gb.endlessnotes.publisher;

import java.util.ArrayList;
import java.util.List;

import ru.gb.endlessnotes.repository.NoteData;

public class Publisher {
    private List<Observer> observers;

    public Publisher(){
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void sendMessage(NoteData noteData) {
        for (Observer observer : observers) {
            observer.receiveMessage(noteData);
        }
    }

}
