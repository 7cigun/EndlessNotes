package ru.gb.endlessnotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.gb.endlessnotes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, NotesFragment.newInstance()).commit();
        }
    }
}