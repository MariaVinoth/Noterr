package com.example.shiv.cal.Noterr;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Notes_Attachments extends AppCompatActivity {

    private ListView attachmentlist;
    private EditText editText;
    public static DatabaseHelper dbh;
    private  int currentPos;
    private Notes_attachment_list_adapter notesattachadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_attachments);

        attachmentlist = (ListView) findViewById(R.id.attachmentslist);
        editText = (EditText) findViewById(R.id.no_attachment_display);
        attachmentlist.setEmptyView(editText);
        dbh = new DatabaseHelper(this);

        Intent notesattach = getIntent();
        long notes_id = notesattach.getLongExtra("ID",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Notes_content notes_content = new Notes_content();
        notes_content.setID(notes_id);

        //Retriev all the attachments for a particular note from the database
        ArrayList<Notes_content> notes_items = dbh.RetrieveNotes_content(notes_content);
        final ArrayList<Notes_content> mediafilter = new ArrayList<Notes_content>();

        //Filtering the contents from the database for oly media contents
        for (int i = 0;i<notes_items.size();i++)
        {
            if(!notes_items.get(i).getCont_type().equalsIgnoreCase("Text"))
            {
                mediafilter.add(notes_items.get(i));

            }

        }

        if( mediafilter == null)
        {
            Toast.makeText(this,"No attachments.Please attach something!",Toast.LENGTH_SHORT).show();
            return;

        }
        else
        {

            //setting the adapter to display the contents

            notesattachadapter = new Notes_attachment_list_adapter(this, R.layout.activity_notes_attachments,mediafilter);
            attachmentlist.setAdapter(notesattachadapter);

            attachmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String item_location = ((Notes_content)attachmentlist.getItemAtPosition(i)).getContent();
                    String type = ((Notes_content)attachmentlist.getItemAtPosition(i)).getCont_type();

                    open_media(item_location,type);
                    return;

                }
            });
            //attempting to delete on long press of an item would require a confirmation
            attachmentlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    currentPos = position;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Notes_Attachments.this);
                    dialog.setTitle("Delete Notes");
                    dialog.setMessage("Do you really want to delete ?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Integer seqno = ((Notes_content)attachmentlist.getItemAtPosition(currentPos)).getSeq_no();
                                    Notes_content del_notes_cont = new Notes_content();
                                    del_notes_cont.setSeq_no(seqno);
                                    if(dbh.deleteNotes_content(del_notes_cont))
                                        Toast.makeText(getBaseContext(),"Attachment deleted!",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getBaseContext(),"Sorry,attachment not deleted.Retry again!",Toast.LENGTH_SHORT).show();


                                    mediafilter.remove(currentPos);
                                    notesattachadapter.notifyDataSetChanged();


                                }
                            });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            }).setCancelable(false);
                        AlertDialog dialog1 = dialog.create();
                    dialog1.show();

                    Button positivebutton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE); // button color for consistency
                    positivebutton.setTextColor(ContextCompat.getColor(Notes_Attachments.this,R.color.Theme_blue));

                    Button negativebutton = dialog1.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negativebutton.setTextColor(ContextCompat.getColor(Notes_Attachments.this,R.color.Theme_blue));

                    return  true;

                }
            });

        }
    }

    //Back button on the menu bar to go back to the previous screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return  true;
    }

    //Function to open the media content from the respective gallery

    public void open_media(String media_location,String type)
    {
        File file = new File(media_location);

        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri mediaUri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID,file);

        //based on the type of media pass the parameters to call from the respective gallery
        if(type.equalsIgnoreCase("Image"))
            intent.setDataAndType(mediaUri,"image/*");
        else if(type.equalsIgnoreCase("Audio"))
            intent.setDataAndType(mediaUri,"audio/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);


        return;

    }
}
                                