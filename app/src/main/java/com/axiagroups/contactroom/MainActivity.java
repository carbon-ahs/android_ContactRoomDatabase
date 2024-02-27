package com.axiagroups.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.axiagroups.contactroom.data.ContactViewModel;
import com.axiagroups.contactroom.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ContactViewModel contactViewModel;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

//        contactViewModel.getAllContacts().observe(this, contacts -> {
//            Log.d("Main", "onCreate: " + contacts);
//        });

        button.setOnClickListener(v -> {
            ContactViewModel.insert(new Contact("Booo", "boooooo"));
        });

    }
}