package ru.gb.endlessnotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.gb.endlessnotes.R;
import ru.gb.endlessnotes.publisher.Publisher;
import ru.gb.endlessnotes.ui.editor.NoteFragment;
import ru.gb.endlessnotes.ui.main.NotesFragment;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;
    private Navigation navigation;

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());
        if(savedInstanceState==null){
            navigation.replaceFragment(NotesFragment.newInstance(), false);
        }
    }
}