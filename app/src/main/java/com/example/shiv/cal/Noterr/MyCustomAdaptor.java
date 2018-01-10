package com.example.shiv.cal.Noterr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chaitanya on 22/11/2017.
 */


//Adapter for setting to-do list

public class MyCustomAdaptor extends BaseAdapter {

    private Context context;
    private ArrayList<Todo_content> todoitems;

    public MyCustomAdaptor(Context context, ArrayList<Todo_content> items) {
        this.context = context;
        this.todoitems = items;
    }

    @Override
    public int getCount() {
        return todoitems.size();
    }

    @Override
    public Object getItem(int i) {
        return todoitems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(String mycontent,int seqNo, int id){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.listview_rowitems, parent, false);
        }
        Todo_content todoContent=(Todo_content)getItem(position);

        TextView textViewName=(TextView)convertView.findViewById(R.id.textView2);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.imageView);


        textViewName.setText(todoitems.get(position).getContent());

        if(todoitems.get(position).getCompleted()==1){
            imageView.setVisibility(View.VISIBLE);
        }
        else
            imageView.setVisibility(View.GONE);

        return convertView;
    }
}
