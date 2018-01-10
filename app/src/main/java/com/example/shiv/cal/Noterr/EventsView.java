package com.example.shiv.cal.Noterr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventsView extends AppCompatActivity {

    private DatabaseHelper dbhelper = new DatabaseHelper(this);
    public Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);


        TextView name = (TextView)findViewById(R.id.name);
        TextView datetime = (TextView)findViewById(R.id.textView8);
        TextView address = (TextView)findViewById(R.id.textView9);
        Button back =(Button) findViewById(R.id.button6);
        Button Delete = (Button)findViewById(R.id.button7);


        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        String Name = i.getStringExtra("Name");
        String Datetime = i.getStringExtra("Date/Time");
        String Address = i.getStringExtra("Address");
         name.setText(Name);
         datetime.setText(Datetime);       //Viewing the contents of events
         address.setText(Address);

         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Confirming before deleting the  events
                AlertDialog.Builder dialog = new AlertDialog.Builder(EventsView.this);
                dialog.setTitle("Delete Event");
                dialog.setMessage("Do you really want to delete?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Cal_sched cal = new Cal_sched();
                        cal.setID(id);
                        dbhelper.deleteCal_shed(cal);      //Deletes event
                        Toast.makeText(getBaseContext(),"Event Deleted.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
            }
        }).setCancelable(false);
                dialog.show();
            }
        });
    }
}
