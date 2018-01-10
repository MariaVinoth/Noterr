package com.example.shiv.cal.Noterr;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Noteshome extends AppCompatActivity {


    private ListView noteslist;
    public String bg_color;
    private DatabaseHelper dbhelper = new DatabaseHelper(this);
    private Notes_Adapter noteadapter;
    ArrayList<Notes_main> notes_items;
    private MenuItem menuItem;
    TextView nonotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteshome);

        noteslist = (ListView) findViewById(R.id.noteslist);
        nonotes = (TextView)findViewById(R.id.Nonotes);

        //Floating button to add notes
        FloatingActionButton float_create = (FloatingActionButton) findViewById(R.id.notes_create);
        float_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = (LayoutInflater.from(Noteshome.this)).inflate(R.layout.notes_title_create,null);
                final TextView title_hdr = (TextView) view.findViewById(R.id.create_hdr);
                final EditText notes_title = (EditText) view.findViewById(R.id.notes_title);
                final EditText notes_tag = (EditText) view.findViewById(R.id.notes_tag);
                final Spinner colorBGSpinner = (Spinner) view.findViewById(R.id.color_bg_spinner);
                final ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.color_list,android.R.layout.simple_spinner_item);
                spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                colorBGSpinner.setAdapter(spin_adapter);

                // provision to select color for the background of notes from a dropdown list
                colorBGSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        bg_color = colorBGSpinner.getItemAtPosition(i).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //Interactive dialog box to get the title and tag details for creating the notes

                AlertDialog.Builder title_inp_getter = new AlertDialog.Builder(Noteshome.this);
                title_inp_getter.setView(view);
                title_inp_getter.setCancelable(true);
                title_inp_getter.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //attempting to create a note  with no details given will throw a message prompting the user for details
                        if(notes_title.getText().toString().isEmpty() || notes_tag.getText().toString().isEmpty())
                        {
                            Toast.makeText(Noteshome.this,"Please enter Title and Tag",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                            create_newnote(notes_title.getText().toString(),notes_tag.getText().toString(),bg_color);

                    }
                });
                title_inp_getter.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;  // on cancel will return to the screen in which it was before
                    }
                });
                AlertDialog dlg = title_inp_getter.create();
                dlg.show();

                //To make all the pages consistent in color, the dialog box button colors are assigned blue

                Button positivebutton = dlg.getButton(DialogInterface.BUTTON_POSITIVE);
                positivebutton.setTextColor(ContextCompat.getColor(Noteshome.this,R.color.Theme_blue));

                Button negativebutton = dlg.getButton(DialogInterface.BUTTON_NEGATIVE);
                negativebutton.setTextColor(ContextCompat.getColor(Noteshome.this,R.color.Theme_blue));

            }
        });

    }



    //activating search option in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu_Inflater = getMenuInflater();
        menu_Inflater.inflate(R.menu.notes_home_searchbar,menu);
        menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //filter the list view based on the search

                ArrayList<Notes_main> localArray= new ArrayList<Notes_main>();
                for(int counter=0;counter<notes_items.size();counter++)
                {
                    Notes_main currentItem = notes_items.get(counter);

                    if(currentItem.getTag().toLowerCase().startsWith(newText.toLowerCase()))
                    {
                        localArray.add(currentItem);
                    }
                }
                noteadapter = new Notes_Adapter(Noteshome.this, R.layout.notes_item,localArray);
                noteslist.setAdapter(noteadapter);
                noteadapter.notifyDataSetChanged();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        noteslist.setAdapter(null);


        notes_items = dbhelper.RetrieveNotes_main();

        //checking if there are any notes created earlier and are available in database. If yes populate them as a list view, else display a message to create one

        if( !notes_items.isEmpty())
        {

            nonotes.setVisibility(TextView.INVISIBLE);
            noteadapter = new Notes_Adapter(this, R.layout.notes_item,notes_items);
            noteslist.setAdapter(noteadapter);

            noteslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    long id = ((Notes_main)noteslist.getItemAtPosition(i)).getID();
                    String title = ((Notes_main)noteslist.getItemAtPosition(i)).getDesc();
                    String tag = ((Notes_main)noteslist.getItemAtPosition(i)).getTag();
                    String bg_color = ((Notes_main)noteslist.getItemAtPosition(i)).getBg_color();

                    Notes_content nts_cont = new Notes_content();
                    nts_cont.setID(id);

                    ArrayList<Notes_content> notes_cont = dbhelper.RetrieveNotes_content(nts_cont);

                    String file = notes_cont.get(0).getContent();

                    // On selecting a note, call the next page where the corresponding content will be displayed. To open a next page with corresponding
                    // details , the actual details of the selected notes are sent to the page
                    Intent vwnote = new Intent(getApplicationContext(),CreateNotes.class);
                    vwnote.putExtra("ID",id);
                    vwnote.putExtra("title",title);
                    vwnote.putExtra("tag",tag);
                    vwnote.putExtra("color",bg_color);
                    vwnote.putExtra("NOTE FILE",file);
                    startActivity(vwnote);


                    return;

                }
            });

        }
        else
            nonotes.setVisibility(TextView.VISIBLE);


    }


    // function which opens create new note page
    public void create_newnote(String title,String tag,String color)
    {
        Intent createnewnote = new Intent(this,CreateNotes.class);
        createnewnote.putExtra("title",title);
        createnewnote.putExtra("tag",tag);
        createnewnote.putExtra("color",color);

        startActivity(createnewnote);
    }


}
