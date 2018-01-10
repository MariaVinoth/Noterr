package com.example.shiv.cal.Noterr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Todo_ContentView extends AppCompatActivity {

    String a="";
    int mInt=1;
    Intent myIntent;

    DatabaseHelper databaseHelper;
    private ListView listItems;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> listToString;
    private ArrayList<Integer> listToCompleted;
    ArrayList<Todo_content> output1;
    ArrayList<String> checkCompleted;
    SharedPreferences sPrefs;

    MyCustomAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo__content_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listItems=(ListView) findViewById(R.id.listViewContent);
        listToString=new ArrayList<String>();
        listToCompleted=new ArrayList<Integer>();
        databaseHelper=new DatabaseHelper(Todo_ContentView.this);
        myIntent = getIntent();
        checkCompleted=new ArrayList<String>();
        a=myIntent.getStringExtra("theId");

        sPrefs= getSharedPreferences("intValue", 0);
        mInt = sPrefs.getInt("myValue",1);
        Todo_content todo_content = new Todo_content();
        todo_content.setID(Integer.parseInt(a));
        output1=databaseHelper.RetrieveTodo_content(todo_content);


        adapter = new MyCustomAdaptor(this, output1);

// get the ListView and attach the adapter

        listItems.setAdapter(adapter);

        onItemclickChanged();

        removeOnLongPress();

        onButtonClicked();

    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return  true;
    }

    private void onButtonClicked(){
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);      //Floating dailogue view to add cotents onto to-do
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Todo_ContentView.this);
                builder.setTitle("Add new Description");
                final EditText input = new EditText(Todo_ContentView.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setTextSize(20);
                builder.setView(input);


                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(input.getText().toString().isEmpty())
                            Toast.makeText(Todo_ContentView.this,"Please eneter the description of the item to add",Toast.LENGTH_SHORT).show();
                        Todo_content todo_content=new Todo_content();
                        todo_content.setID(Integer.parseInt(a));
                        todo_content.setContent(input.getText().toString());
                        todo_content.setCompleted(0);


                        todo_content.setSeq_no(++mInt);

                        output1.add(todo_content);
                        adapter.notifyDataSetChanged();


                        SharedPreferences.Editor mEditor = sPrefs.edit();
                        mEditor.putInt("myValue", mInt).apply();


                        databaseHelper.createTODO_content(todo_content);
                        Toast.makeText(Todo_ContentView.this,"Added new description successfully ",Toast.LENGTH_SHORT).show();

                    }

                }).setNegativeButton("Cancel",null).show();
            }
        });
    }

    //Single tap to set the task wo completes
    private void onItemclickChanged(){

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder alertSetCompleted = new AlertDialog.Builder(Todo_ContentView.this,pos);    //Setting the task as accomplished in to-do list
                alertSetCompleted.setTitle("Are you sure");
                alertSetCompleted.setMessage("Press ok to set task accomplished ");

                alertSetCompleted.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {

                        output1.get(pos).setCompleted(1);
                        output1.get(pos).setSeq_no(output1.get(pos).getSeq_no());
                        adapter.notifyDataSetChanged();
                        databaseHelper.updateTODO_content_compltd(output1.get(pos));

                    }
                }).setNegativeButton("Cancel",null);
                alertSetCompleted.create();
                alertSetCompleted.show();

            }
        });

    }

    private void removeOnLongPress(){


        listItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
   //Deleting the to-do entry
                AlertDialog.Builder builder= new AlertDialog.Builder(Todo_ContentView.this,pos);

                builder.setTitle("Delete Content");
                builder.setMessage("Are you sure");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Todo_content todo_content=output1.get(pos);
                        output1.remove(pos);

                        adapter.notifyDataSetChanged();
                        databaseHelper.deleteTODO_content(todo_content);


                    }
                }).setNegativeButton("No",null);

                builder.create();
                builder.show();
                return true;

            }

        });
    }
}
