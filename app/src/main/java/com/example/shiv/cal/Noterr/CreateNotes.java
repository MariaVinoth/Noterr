package com.example.shiv.cal.Noterr;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CreateNotes extends AppCompatActivity {


    private EditText Content;
    private String Note_File;
    private Notes_cont_stored cont_obtned;
    public String notes_title;
    public String notes_tag;
    public String notes_bg_color;
    public static long id_notes;
    public static DatabaseHelper dbh;
    public static final String F_Exn = ".txt";
    public static int MY_PERMISSION_TO_READ = 12;
    public int check_permission;
    public Uri cameraimgUri;
    public static int item_selected;
    public  static final int gallery = 0;
    public static final int audio = 1;
    public static final int camera = 2;
    String attach_file_ary [] = new String[20];
    String attach_ftype_ary [] = new String[20];
    int attach_file_cntr = 0;
    int attach_ftype_cntr = 0;
    private ListView attachmentlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        check_permission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        attachmentlist = (ListView) findViewById(R.id.attachmentslist);

         // to go back to  Notes home screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = new DatabaseHelper(this);


        Content = (EditText) findViewById(R.id.content); // Initializing the notes body field

         //get the values passed from the Notes home screen
        Intent createnotes = getIntent();
        notes_title = createnotes.getStringExtra("title");
        notes_tag = createnotes.getStringExtra("tag");
        notes_bg_color = createnotes.getStringExtra("color");
        id_notes = createnotes.getLongExtra("ID",0);

        Note_File = createnotes.getStringExtra("NOTE FILE"); // file name of the already existing note


        //Checking whether to open as a new note for creation or already existing note
        if(Note_File != null && !Note_File.isEmpty())
        {

            cont_obtned = obtainSelectedNote(this,Note_File);
            if (cont_obtned != null)
            {

                Content.setText(cont_obtned.getContent());
            }

        }


        switch(notes_bg_color)
        {
            case "Yellow":
                Content.setBackgroundColor(ContextCompat.getColor(this,R.color.Yellow));
                break;
            case "Blue":
                Content.setBackgroundColor(ContextCompat.getColor(this,R.color.Blue));
                break;
            case "Green":
                Content.setBackgroundColor(ContextCompat.getColor(this,R.color.Green));
                break;
            case "Creamy":
                Content.setBackgroundColor(ContextCompat.getColor(this,R.color.Creamy));
                break;
            case "Celestial silver":
                Content.setBackgroundColor(ContextCompat.getColor(this,R.color.Celestial_silver));
                break;
        }


    }


    //checking permission to open the content from the internal storage of the device
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSION_TO_READ)
            if((grantResults.length>0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
            {
                switch (item_selected)
                {
                    case gallery:
                        attachGalleryimage();
                        break;
                    case audio:
                        attachAudio();
                        break;
                    case camera:
                        attachCameraimage();
                        break;

                }
            }
    }

    //populate the menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_create_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        if(item.getItemId() == R.id.save) {
            if(Content.getText().toString().isEmpty())  // attempting to save without adding any contents will throw a message prompting to enter content
                Toast.makeText(this, "Please enter some content", Toast.LENGTH_SHORT).show();
            else
                saveNote();
        }
        else if(item.getItemId() == R.id.delete)
            delete_Note(); //to delete the note
        else if(item.getItemId() == R.id.picture) // to attach an image from gallery

        {
            if(check_permission != PackageManager.PERMISSION_GRANTED) //check whether app has permission to open the gallery
            {
                item_selected = gallery;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
            }else
            {
                attachGalleryimage();
            }
        }
        else if(item.getItemId() == R.id.audio) // to attach audio file
        {
            if(check_permission != PackageManager.PERMISSION_GRANTED)
            {
                item_selected = audio;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
            }else
            {
                attachAudio();
            }
        }

        else if(item.getItemId() == R.id.prev_attachments)  // to open previous attachments to the notes
        {

            if(id_notes != 0)
            {
                Intent noteattach = new Intent(getApplicationContext(),Notes_Attachments.class);
                noteattach.putExtra("ID",id_notes);
                startActivity(noteattach);
            }
        }
        else if(item.getItemId() == R.id.camera) // to open camera to take a snap and attach to the notes

        {
            if(check_permission != PackageManager.PERMISSION_GRANTED)
            {
                item_selected = camera;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_TO_READ);
            }else
            {
                attachCameraimage();
            }

        }
        else if (item.getItemId() == R.id.share) // to share the notes through other apps
        {
            if(id_notes != 0)
                sharenotes(CreateNotes.this, Note_File);
        }
        else if (item.getItemId() == android.R.id.home) // to get back to the notes home page
            finish();

        return true;
    }

    private void attachCameraimage() // to open the camera,take a snap and attach
    {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Camera Image location");
        cameraimgUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camera_intnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        camera_intnt.putExtra(MediaStore.EXTRA_OUTPUT,cameraimgUri);
        startActivityForResult(camera_intnt,camera);

    }

    private void attachGalleryimage() //to open gallery and pick an image to attach
    {
        Intent img_intnt = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(img_intnt,gallery);

    }
    private void attachAudio() // to open audio gallery and pick an audio to attach
    {

        Intent audio_intnt = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(audio_intnt,audio);

    }


    //open the respective internal device feature, to attach the media, based on the call from above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == gallery && resultCode == RESULT_OK && data != null) //to open image gallery
        {
            Uri imageSelected = data.getData();
            String[] img_filePathcol = {MediaStore.Images.Media.DATA};
            Cursor cur = getContentResolver().query(imageSelected,img_filePathcol,null,null,null);
            cur.moveToFirst();
            int colIndex = cur.getColumnIndex(img_filePathcol[0]);
            String picturePath = cur.getString(colIndex);
            cur.close();

            attach_file_ary[attach_file_cntr] = picturePath;
            attach_file_cntr = attach_file_cntr + 1;
            attach_ftype_ary[attach_ftype_cntr] = "Image";
            attach_ftype_cntr = attach_ftype_cntr + 1;
        }

        if(requestCode == audio && resultCode == RESULT_OK && data != null) // to open audio gallery
        {

            Uri audioSelected = data.getData();
            String[] audio_filePathcol = {MediaStore.Audio.Media.DATA};
            Cursor cur = getContentResolver().query(audioSelected,audio_filePathcol,null,null,null);
            cur.moveToFirst();
            int colIndex = cur.getColumnIndex(audio_filePathcol[0]);
            String audioPath = cur.getString(colIndex);
            cur.close();
            attach_file_ary[attach_file_cntr] = audioPath;
            attach_file_cntr = attach_file_cntr + 1;
            attach_ftype_ary[attach_ftype_cntr] = "Audio";
            attach_ftype_cntr = attach_ftype_cntr + 1;

        }


        if(requestCode == camera) // to open camera
        {

            String[] img_filePathcol = {MediaStore.Images.Media.DATA};
            Cursor cur = getContentResolver().query(cameraimgUri,img_filePathcol,null,null,null);
            int colIndex = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            cur.moveToLast();
            String picturePath = cur.getString(colIndex);
            cur.close();
            attach_file_ary[attach_file_cntr] = picturePath;
            attach_file_cntr = attach_file_cntr + 1;
            attach_ftype_ary[attach_ftype_cntr] = "Image";
            attach_ftype_cntr = attach_ftype_cntr + 1;
        }
    }


    //Saving the notes on click of a save button
    private void saveNote()
    {
        String noteFileName = null;

        Notes_cont_stored notes_cont_stored;

        //new note
        if(cont_obtned == null) {

            notes_cont_stored = new Notes_cont_stored(Content.getText().toString());

        }
        else  //existing note
        {

            notes_cont_stored = new Notes_cont_stored(Content.getText().toString());
            noteFileName = Note_File;

        }
        String result[] = save(this,notes_cont_stored,noteFileName);

        //populate appropriate message telling whether it was a save or update
        if (result[0] == "S") //save
        {

            Toast.makeText(this,notes_title+ " notes saved!",Toast.LENGTH_SHORT).show();
            saveNotestoDB(result[1]);

        } else if (result[0] == "U")  //update
        {
            Toast.makeText(this, "Notes updated !", Toast.LENGTH_SHORT).show();

            Notes_content insertRecordContent = new Notes_content();
            long content_id = 0;
            int ary_length = attach_file_cntr - 1;

            for(int i = 0;i<=ary_length;i++)
            {
                insertRecordContent.setID(id_notes);
                insertRecordContent.setCont_type(attach_ftype_ary[i]);
                insertRecordContent.setContent(attach_file_ary[i]);

                content_id = dbh.createNotes_content(insertRecordContent);

            }
            if (content_id != 0)
                Toast.makeText(this, "Notes was updated!", Toast.LENGTH_SHORT).show();


        } else
            {
            Toast.makeText(this, "Sorry,notes was not saved!", Toast.LENGTH_SHORT).show();

        }
        finish();// go back to the last activity

    }


    //function to delete notes from the internal storage of the device
    private void delete_Note() {


        Notes_main notes_main = new Notes_main();
        if(cont_obtned == null) // if its creating a new note phase and trying to delete before saving, will simply go back to the noteshome page
            finish();
        else
            {
                //Alert to confirm the deletion
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Delete Notes");
            dialog.setMessage("Do you really want to delete '"+notes_title+"' ?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            deleteNote(getApplicationContext(), Note_File); //passing the file name in the internal storage to be deleted
                            Toast.makeText(getApplicationContext(),"Notes '" +notes_title+"' deleted",Toast.LENGTH_SHORT).show();
                            finish();

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

            Button positivebutton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE); //color change for button for consistency
            positivebutton.setTextColor(ContextCompat.getColor(this,R.color.Theme_blue));

            Button negativebutton = dialog1.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativebutton.setTextColor(ContextCompat.getColor(this,R.color.Theme_blue));

        }
    }
    //storing the details of the notes to database
    private void saveNotestoDB(String file)
    {
        long id;

        if(file == " ")
        {
            Toast.makeText(this,"File was not saved properly",Toast.LENGTH_SHORT).show();
            return;

        }
        //passing the values to the database query through model class
        Notes_main insertRecordMain = new Notes_main();
        insertRecordMain.setDesc(notes_title);
        insertRecordMain.setTag(notes_tag);
        insertRecordMain.setBg_color(notes_bg_color);

        id = dbh.createNotes_main(insertRecordMain);

        long notes_id = id; //id that has been stored in notes_main database

        Notes_content insertRecordContent = new Notes_content();
        insertRecordContent.setID(notes_id);
        insertRecordContent.setCont_type("Text");
        insertRecordContent.setContent(file);

        long content_id = dbh.createNotes_content(insertRecordContent);

        //Indicating success or failure of the insertion into database activity
        if(content_id == 0)
        {
            Toast.makeText(this,"Sorry Note was not saved successfully",Toast.LENGTH_SHORT).show();
            return;

        }


        int ary_length = attach_file_cntr - 1;

        for(int i = 0;i<=ary_length;i++)
        {
            insertRecordContent.setID(notes_id);
            insertRecordContent.setCont_type(attach_ftype_ary[i]);
            insertRecordContent.setContent(attach_file_ary[i]);

            content_id = dbh.createNotes_content(insertRecordContent);
        }

    }

    //Saving the notes as a text file in the internal storage of the device
    public static String[] save(Context context, Notes_cont_stored note,String existing_file)
    {
        String success = "S";
        String failure = "F";
        String update = "U";
        String result;
        String no_file = " ";
        String file_name;



        dbh = new DatabaseHelper(context);
        if (existing_file == null) {
            file_name = dbh.getCurrentdatetime() + F_Exn;
            result = success;
        }
        else {
            file_name = existing_file;
            result = update;
        }


        //file io operations
        FileOutputStream file_out;
        ObjectOutputStream obj_out;

        try{
            file_out = context.openFileOutput(file_name, context.MODE_PRIVATE);
            obj_out = new ObjectOutputStream(file_out);
            obj_out.writeObject(note.getContent());
            obj_out.close();
            file_out.close();



        }catch (IOException e)
        {
            e.printStackTrace();
            return new String[] {failure,no_file};

        }


        return new String[] {result,file_name};

    }

    //Obtaining the contents from the text file stored in the internal storage
    public static Notes_cont_stored obtainSelectedNote(Context context, String file_name)
    {
        File file = new File(context.getFilesDir(),file_name);
        Notes_cont_stored notes_content;

        if(file.exists())
        {
            //file io operations
            FileInputStream file_inp;
            ObjectInputStream obj_inp;

            try
            {
                file_inp = context.openFileInput(file_name);
                obj_inp = new ObjectInputStream(file_inp);


                notes_content = new Notes_cont_stored((String) obj_inp.readObject());

                file_inp.close();
                obj_inp.close();

            } catch(IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }
            return notes_content;

        }

        return  null;

    }

    //function to delete the text file from the local storage of the device and from the database
    public static void deleteNote(Context context, String file_name) {

        Notes_main notes_main = new Notes_main();

        File f_dir = context.getFilesDir();
        File file = new File(f_dir,file_name);
        if(file.exists())
            file.delete();
        notes_main.setID(id_notes);

        dbh.deleteNotes_main(notes_main); //deleting the data from the database
    }

    //function to share the file through other applications

    public void sharenotes(Context context,String file_name)
    {
        File file = new File(context.getFilesDir(),file_name);

        Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Uri uri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID,file);

        shareintent.setDataAndType(uri,"text/*");
        shareintent.putExtra(Intent.EXTRA_STREAM,uri);
        startActivity(Intent.createChooser(shareintent,"Share via"));

    }


}
