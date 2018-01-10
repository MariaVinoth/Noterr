package com.example.shiv.cal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.shiv.cal.Noterr.Cal_sched;
import com.example.shiv.cal.Noterr.DatabaseHelper;
import com.example.shiv.cal.Noterr.R;
import java.util.ArrayList;

/**
 * Created by Shiv on 2017-11-24.
 */


//adapter for populating values into list view

public class EventsAdapter  extends ArrayAdapter<Cal_sched>{

        DatabaseHelper dbhelper = new DatabaseHelper(getContext());
    public EventsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cal_sched> cal_scheds){
        super(context, resource, cal_scheds);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_items,null);
        }

        Cal_sched cal_elm = getItem(position);

        if (cal_elm != null)
        {
            TextView Name = (TextView) convertView.findViewById(R.id.event_Name);
            TextView datetime = (TextView) convertView.findViewById(R.id.event_datetime);

            Name.setText(cal_elm.getName());
            datetime.setText(dbhelper.getDateTime(cal_elm.getDate_time()));

        }
        return convertView;

    }

}
