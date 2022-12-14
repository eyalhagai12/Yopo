package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;

import java.util.HashMap;
import java.util.List;

public class ClientCalendarActivity extends AppCompatActivity {

    private CalendarView calendar;
    private TextView date_view;
    private Spinner appointments;
    private String selected_date;

    private Session session;
    private Server server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_calendar_layout);

        initializeFields();
        initializeServerAndSession();
        initializeCalenderListener();
    }

    private void initializeFields() {
        calendar = findViewById(R.id.calendar_client);
        date_view = findViewById(R.id.date_info_client);
        appointments = findViewById(R.id.appointments_client_spinner);
    }

    private void initializeServerAndSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerFactory", e.toString());
        }
        session = Session.getInstance();
    }

    private void initializeCalenderListener() {
        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            // In this Listener have one method
            // and in this method we will
            // get the value of DAYS, MONTH, YEARS
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // this will serve as a key in the future
                // in order to extract the appointments from the database.
                selected_date = dayOfMonth + "/" + (month + 1) + "/" + year;

                // set this date in TextView for Display
                date_view.setText(selected_date);

                // retrieve the appointments for the selected day
                List<HashMap<String, Object>> appointments_list = server.get_appointment_info(session.get_session_attribute("username").toString(), selected_date, true);
                if (appointments_list != null && !appointments_list.isEmpty()) {
                    loadAndSetAppointmentsToSpinner(appointments_list);
                } else {
                    noAppointments();
                }
            }
        });
    }

    private void loadAndSetAppointmentsToSpinner(List<HashMap<String, Object>> appointments_list) {
        String[] appointment_info_list = loadAppointmentsInfo(appointments_list);
        ArrayAdapter<String> appointments_adapter = new ArrayAdapter<String>(ClientCalendarActivity.this, android.R.layout.simple_spinner_item, appointment_info_list);
        appointments.setAdapter(appointments_adapter);
    }

    private String[] loadAppointmentsInfo(List<HashMap<String, Object>> appointments_list) {
        String[] appointment_info_list = new String[appointments_list.size()];
        int counter = 0;
        for (HashMap<String, Object> appointment : appointments_list) {
            String business_name = server.get_business_info(appointment.get("business_username").toString()).get("business_name").toString();
            String service_name = (String) server.get_service((String) appointment.get("service")).get("service");
            String app = business_name + ":" + service_name + ", in:" + appointment.get("date") + " " + appointment.get("time");
            appointment_info_list[counter] = app;
            counter++;
        }

        return appointment_info_list;
    }

    private void noAppointments() {
        String[] appointment_info_list = new String[1];
        appointment_info_list[0] = "No appointments";
        ArrayAdapter<String> appointments_adapter = new ArrayAdapter<String>(ClientCalendarActivity.this, android.R.layout.simple_spinner_item, appointment_info_list);

        appointments.setAdapter(appointments_adapter);
    }
}
