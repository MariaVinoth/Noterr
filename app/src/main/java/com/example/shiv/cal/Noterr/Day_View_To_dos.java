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

public class Day_View_To_dos extends Fragment {

    private ListView tododaylist;
    private DatabaseHelper dbhelper;
    ArrayList<Todo_main> todo_items;
    Notes_main_date todo_input = new Notes_main_date();
    String StartDate, Enddate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_view_to_dos, container, false);

        Context context = getActivity();
        dbhelper = new DatabaseHelper(context);

        Bundle bundle=getArguments();
        String Date= (String) bundle.get("Date");
        String Month = (String) bundle.get("Month");
        String Year = (String) bundle.get("Year");

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int HHs=12,MMs=0,SSs=0,HHf=23,MMf=59,SSf=59;
        int month =Integer.parseInt(Month);
        TextView date =(TextView) rootView.findViewById(R.id.Datetext);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();

        tododaylist = (ListView) getActivity().findViewById(R.id.tododayview);
        try {
            todo_input.setStart_date(dbhelper.getDateTime(StartDate));    //Setting start date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            todo_input.setEnd_date(dbhelper.getDateTime(Enddate));     //Setting end date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Retrieving To-Dos for the particular day

        todo_items = dbhelper.RetrieveTodo_main_date(todo_input);

        if (todo_items.isEmpty()) {
            Toast.makeText(getContext(), "No Todo's Created!", Toast.LENGTH_SHORT).show();


        } else {
            To_do_Adapter to_do_adapter = new To_do_Adapter(context,android.R.layout.simple_list_item_1,todo_items);
            tododaylist.setAdapter(to_do_adapter);
            tododaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Integer ident = ((Todo_main) tododaylist.getItemAtPosition(i)).getID();
                    String s = String.valueOf(ident);

                   // On selecting a to-do, call the next page where the corresponding content will be displayed.
                    Intent todoview = new Intent(getActivity(),Todo_ContentView.class);
                    todoview.putExtra("theId",s);
                    startActivity(todoview);

                    return;

                }
            });

            return;
        }

    }
}

