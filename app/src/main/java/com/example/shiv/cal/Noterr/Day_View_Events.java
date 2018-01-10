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

public class Day_View_Events extends Fragment {

    private ListView eventsdaylist;
    private DatabaseHelper dbhelper;
    ArrayList<Cal_sched> event_items;
    Cal_shed_retrival cal_events = new Cal_shed_retrival();
    String StartDate, Enddate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_events, container, false);


        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle = getArguments();
        String Date = (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int month = Integer.parseInt(Month);
        int HHs = 12, MMs = 0, SSs = 0, HHf = 23, MMf = 59, SSf = 59;
        TextView date = (TextView) rootView.findViewById(R.id.Datetext);
        Calendar c = Calendar.getInstance();
        Integer Day = Integer.parseInt(Date);
        c.set(Calendar.DATE, Day);
        c.set(Calendar.MONTH, Integer.parseInt(Month));
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        c.set(Calendar.HOUR, HHs);
        c.set(Calendar.MINUTE, MMs);
        c.set(Calendar.SECOND, SSs);
        c.add(Calendar.DATE, -1);
        StartDate = dt.format(c.getTime());       //StartDate for retrival

        c.set(Calendar.HOUR, HHf);
        c.set(Calendar.MINUTE, MMf);
        c.set(Calendar.SECOND, SSf);
        Enddate = dt.format(c.getTime());     //EndDate for retrival

        month++;
        date.setText(Date + "/" + month + "/" + Year);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        eventsdaylist = (ListView) getActivity().findViewById(R.id.listdayevents);
        //Framing start and end dates
        try {
            cal_events.setStart_Datetime(dbhelper.getDateTime(StartDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            cal_events.setEnd_Datetime(dbhelper.getDateTime(Enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        event_items = dbhelper.RetrieveCal_sched(cal_events);

        if (event_items.isEmpty()) {
            Toast.makeText(getContext(), "No Events Created!", Toast.LENGTH_SHORT).show();   //Checks if the event list is empty


        } else {
            EventsAdapter eventsadapter = new EventsAdapter(getContext(), R.layout.event_items, event_items);
            eventsdaylist.setAdapter(eventsadapter);       //Setting eventlist adapter
            eventsdaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Long ident = ((Cal_sched) eventsdaylist.getItemAtPosition(i)).getID();
                    String name = ((Cal_sched) eventsdaylist.getItemAtPosition(i)).getName();
                    String datetime = dbhelper.getDateTime(((Cal_sched) eventsdaylist.getItemAtPosition(i)).getDate_time());
                    String address = ((Cal_sched) eventsdaylist.getItemAtPosition(i)).getVenue();

                    //On selecting a event, call the next page where the corresponding content will be displayed.
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
