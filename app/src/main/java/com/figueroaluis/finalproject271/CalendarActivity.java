package com.figueroaluis.finalproject271;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {


    private CalendarView calendarView;
    private ListView calendarList;
    private Long date;
    private Context mContext;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        mContext = getApplicationContext();
        calendarView = findViewById(R.id.calendarView);
        calendarList = findViewById(R.id.calendar_listview);
        backButton = findViewById(R.id.calendar_back_button);
        date = calendarView.getDate();

        // On click equivalent
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                date = calendarView.getDate();
                Toast.makeText(mContext, "Year: " + year + " Month: " + month + " Day: " + dayOfMonth,Toast.LENGTH_LONG).show();



            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goBack = new Intent(mContext, MainActivity.class);
                startActivity(goBack);
            }

        });


    }



}
