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

public class Month_View_Events extends Fragment {

    private ListView eventsmonthlist;
    private DatabaseHelper dbhelper;
    ArrayList<Cal_sched> event_items;
    Cal_shed_retrival cal_events = new Cal_shed_retrival();
    String StartDate, Enddate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.month_view_events, container, false);

        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");
        int m = Integer.parseInt(Month);
            m++;
        TextView month =(TextView) rootView.findViewById(R.id.Monthtext);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int DDs=1,DDf=30, HHs=12,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE,DDs);
        c.set(Calendar.MONTH,Integer.parseInt(Month));
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        c.set(Calendar.HOUR,HHs);
        c.set(Calendar.MINUTE,MMs);
        c.set(Calendar.SECOND,SSs);
        c.add(Calendar.DATE, -1);
        StartDate =dt.format(c.getTime());    //Start Date for retrival

        c.set(Calendar.DATE,DDf);
        c.set(Calendar.HOUR,HHf);
        c.set(Calendar.MINUTE,MMf);
        c.set(Calendar.SECOND,SSf);
        Enddate =dt.format(c.getTime());    // End Date for retrival

        switch(m){        //Setting the value of month into textview
            case 1:
                month.setText("January");
                break;
            case 2:
                month.setText("Febraury");
                break;
            case 3:
                month.setText("March");
                break;
            case 4:
                month.setText("April");
                break;
            case 5:
                month.setText("May");
                break;
            case 6:
                month.setText("June");
                break;
            case 7:
                month.setText("July");
                break;
            case 8:
                month.setText("August");
                break;
            case 9:
                month.setText("September");
                break;
            case 10:
                month.setText("October");
                break;
            case 11:
                month.setText("November");
                break;
            case 12:
                month.setText("December");
                break;
        }
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        eventsmonthlist = (ListView) getActivity().findViewById(R.id.listmonthevents);
        try {
            cal_events.setStart_Datetime(dbhelper.getDateTime(StartDate));      //Setting start date for retrival
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            cal_events.setEnd_Datetime(dbhelper.getDateTime(Enddate));     //Setting end date for retrival
        } catch (ParseException e) {
            e.printStackTrace();
        }


        event_items = dbhelper.RetrieveCal_sched(cal_events);

        if (event_items.isEmpty()) {
            Toast.makeText(getContext(), "No Events Created!", Toast.LENGTH_SHORT).show();     //Checks for empty event list


        } else {
            EventsAdapter eventsadapter = new EventsAdapter(getContext(), R.layout.event_items, event_items);
            eventsmonthlist.setAdapter(eventsadapter);     //Setting the event list contents into list

            eventsmonthlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Long ident = ((Cal_sched) eventsmonthlist.getItemAtPosition(i)).getID();
                    String name = ((Cal_sched) eventsmonthlist.getItemAtPosition(i)).getName();
                    String datetime = dbhelper.getDateTime(((Cal_sched) eventsmonthlist.getItemAtPosition(i)).getDate_time());
                    String address = ((Cal_sched) eventsmonthlist.getItemAtPosition(i)).getVenue();
                    // On selecting a event, call the next page where the corresponding content will be displayed
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
