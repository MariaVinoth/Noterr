package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by PRADEESH S on 2017-11-27.
 */


//Adapter for to-do to populate list items
public class To_do_Adapter extends ArrayAdapter<Todo_main> {
    public To_do_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Todo_main> todo_mains) {
        super(context, resource, todo_mains);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,null);
        }

        Todo_main todo_elm = getItem(position);

        if (todo_elm != null)
        {
            TextView desc = (TextView) convertView.findViewById(android.R.id.text1);


            desc.setText(todo_elm.getDesc());

        }
        return convertView;
    }
}
