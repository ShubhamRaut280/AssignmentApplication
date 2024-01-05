package com.shubham.arachnomeshassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    EditText name, contact;
    TextView location;
    Button getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.nameField);
        contact = findViewById(R.id.contactField);
        location = findViewById(R.id.locationField);
        getLocation = findViewById(R.id.getlocation);





    }
}