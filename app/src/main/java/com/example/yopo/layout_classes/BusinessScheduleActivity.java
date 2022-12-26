package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.yopo.R;

public class BusinessScheduleActivity extends AppCompatActivity {
    // field variables
    private Spinner day_chooser;
    private ConstraintLayout day_viewer;
    private TextView day_text;
    private Button edit_services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_schedule_layout);

        // get field variables
        day_chooser = findViewById(R.id.day_chooser);
        day_viewer = findViewById(R.id.day_viewer);
        day_text = day_viewer.findViewById(R.id.day_text);
        edit_services = findViewById(R.id.edit_services_button);

        // create an array adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        day_chooser.setAdapter(adapter);

        // set event listeners
        day_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = "Schedule for " + adapter.getItem(position).toString();
                day_text.setText(text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }


}