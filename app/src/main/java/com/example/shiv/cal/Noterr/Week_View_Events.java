package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiv.cal.EventsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Shiv on 2017-11-08.
 */

public class Week_View_Events extends Fragment {

    private ListView eventsweeklist;
    private DatabaseHelper dbhelper;
    ArrayList<Cal_sched> event_items;
    Cal_shed_retrival cal_events = new Cal_shed_retrival();
    String StartDate, Enddate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.week_view_events, container, false);

        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle=getArguments();
        String Day= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");
        int HHs=12,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView datetext =(TextView) rootView.findViewById(R.id.Weektext);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, Integer.parseInt(Day));
        c.set(Calendar.MONTH,Integer.parseInt(Month));
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        c.set(Calendar.HOUR,HHs);
        c.set(Calendar.MINUTE,MMs);
        c.set(Calendar.SECOND,SSs);
        c.add(Calendar.DATE,-4);
        c.add(Calendar.DATE, -1);
        datetext.append(sdf.format(c.getTime()));
        StartDate =dt.format(c.getTime());  // Start Date for retrival
            c.add(Calendar.DATE,7);
        c.set(Calendar.HOUR,HHf);
        c.set(Calendar.MINUTE,MMf);
        c.set(Calendar.SECOND,SSf);
        datetext.append(" To "+sdf.format(c.getTime()));
           Enddate =dt.format(c.getTime());    // End Date for retrival

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        eventsweeklist = (ListView) getActivity().findViewById(R.id.listweekevents);
        try {
            cal_events.setStart_Datetime(dbhelper.getDateTime(StartDate));     //Setting start date for retrival
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            cal_events.setEnd_Datetime(dbhelper.getDateTime(Enddate));   //Setting end date for retrival
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Retrieving calendar evets for a week
        event_items = dbhelper.RetrieveCal_sched(cal_events);

        if (event_items.isEmpty()) {
            Toast.makeText(getContext(), "No Events Created!", Toast.LENGTH_SHORT).show();      //Chack if the event list is empty


        } else {
            EventsAdapter eventsadapter = new EventsAdapter(getContext(), R.layout.event_items, event_items);
            eventsweeklist.setAdapter(eventsadapter);      //Setting adapter for events list
            eventsweeklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Long ident = ((Cal_sched) eventsweeklist.getItemAtPosition(i)).getID();
                    String name = ((Cal_sched) eventsweeklist.getItemAtPosition(i)).getName();
                    String datetime = dbhelper.getDateTime(((Cal_sched) eventsweeklist.getItemAtPosition(i)).getDate_time());
                    String address = ((Cal_sched) eventsweeklist.getItemAtPosition(i)).getVenue();
                        // On selecting a note, call the next page where the corresponding content will be displayed.
                    Intent intent = new Intent(getActivity(), EventsView.class);
                    intent.putExtra("ID",ident);
                    intent.putExtra("Name", name);
                    intent.putExtra("Date/Time", datetime);
                    intent.putExtra("Address", address);
                    startActivity(intent);


                    return;

                }
            });


            return;
        }

    }
}
