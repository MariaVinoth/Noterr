package com.example.shiv.cal.Noterr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by Vinod on 18/11/17.
 */

//Adapter to populate values to the listview of Attachments page
public class Notes_attachment_list_adapter extends ArrayAdapter<Notes_content> {
    DatabaseHelper dbhelper = new DatabaseHelper(getContext());
    public Notes_attachment_list_adapter(@NonNull Context context, int resource, @NonNull ArrayList<Notes_content> notes_attach_item) {
        super(context, resource, notes_attach_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_attachment_items,null);
        }

        Notes_content note_elm = getItem(position);

        if (note_elm != null)
        {

            ImageView attach_type = (ImageView) convertView.findViewById(R.id.content_type);

            //setting images for the ttype of attachment

            if(note_elm.getCont_type().equalsIgnoreCase( "Image"))
                attach_type.setImageResource(R.drawable.image);
            else if(note_elm.getCont_type().equalsIgnoreCase( "Audio"))
                attach_type.setImageResource(R.drawable.audio);

        }


        return convertView;
    }
}
