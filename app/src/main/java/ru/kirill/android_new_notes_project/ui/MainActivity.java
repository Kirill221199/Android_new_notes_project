package ru.kirill.android_new_notes_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.ui.fragment_communication.Publisher;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_container,
                    MainNotesFragment.newInstance()).commit();
        }
    }
}