package com.example.shiv.cal.Noterr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;


/**
 * Created by Shiv on 2017-11-05.
 */

public class Calendar_view extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        CalendarView cal = (CalendarView) findViewById(R.id.calendarView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), Create_Event.class));
            }
        });

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {    // Date selection from calendar view

                String Date = String.valueOf(dayOfMonth);
                String  Month = String.valueOf(month);
                String Year = String.valueOf(year);

                //Open the Day view
                Intent i = new Intent(getApplicationContext(), Day_view.class);
                i.putExtra("Day", Date);
                i.putExtra("Month",Month);
                i.putExtra("Year",Year);
                startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

}
