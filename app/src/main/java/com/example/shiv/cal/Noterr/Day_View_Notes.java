package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Shiv on 2017-11-08.
 */

public class Day_View_Notes extends Fragment {

    private ListView notesdaylist;
    private DatabaseHelper dbhelper;
    ArrayList<Notes_main> notes_items;
    Notes_main_date notes_input = new Notes_main_date();
    String StartDate, Enddate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_notes, container, false);

        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");
        TextView date =(TextView) rootView.findViewById(R.id.Datetext);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int HHs=12,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;
        int month =Integer.parseInt(Month);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, Integer.parseInt(Date));
        c.set(Calendar.MONTH,Integer.parseInt(Month));
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        c.set(Calendar.HOUR,HHs);
        c.set(Calendar.MINUTE,MMs);
        c.set(Calendar.SECOND,SSs);
        c.add(Calendar.DATE, -1);
        StartDate =dt.format(c.getTime());       //StartDate for retrival


        c.set(Calendar.HOUR,HHf);
        c.set(Calendar.MINUTE,MMf);
        c.set(Calendar.SECOND,SSf);
        Enddate =dt.format(c.getTime());      //EndDate for retrival
        month++;
        date.setText(Date+"/"+month+"/"+Year);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


    notesdaylist = (ListView) getActivity().findViewById(R.id.notesdayview);
        try {
            notes_input.setStart_date(dbhelper.getDateTime(StartDate));   //Setting start date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            notes_input.setEnd_date(dbhelper.getDateTime(Enddate));    //Setting end date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Retrieving Notes  for a particular day
        notes_items = dbhelper.RetrieveNotes_main_date(notes_input);

        if (notes_items.isEmpty()) {
            Toast.makeText(getContext(), "No notes Created!", Toast.LENGTH_SHORT).show();    //Checks if notes list is empty


        } else {
            Notes_Adapter notesadapter = new Notes_Adapter(getContext(), R.layout.notes_item, notes_items);
            notesdaylist.setAdapter(notesadapter);        //Notes list adapter set
            notesdaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    long identity = ((Notes_main)notesdaylist.getItemAtPosition(i)).getID();
                    String title = ((Notes_main)notesdaylist.getItemAtPosition(i)).getDesc();
                    String tag = ((Notes_main)notesdaylist.getItemAtPosition(i)).getTag();
                    String bg_color = ((Notes_main)notesdaylist.getItemAtPosition(i)).getBg_color();

                    Notes_content nts_cont = new Notes_content();
                    nts_cont.setID(identity);

                    ArrayList<Notes_content> notes_cont = dbhelper.RetrieveNotes_content(nts_cont);

                    String file = notes_cont.get(0).getContent();

                    // On selecting a note, call the next page where the corresponding content will be displayed. To open a next page with corresponding
                    // details , the actual details of the selected notes are sent to the page
                    Intent vwnote = new Intent(getActivity(),CreateNotes.class);
                    vwnote.putExtra("ID",identity);
                    vwnote.putExtra("title",title);
                    vwnote.putExtra("tag",tag);
                    vwnote.putExtra("color",bg_color);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);

                    return;
                }
            });

            return;
        }
    }
}

