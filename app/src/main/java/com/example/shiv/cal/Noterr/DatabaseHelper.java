package com.example.shiv.cal.Noterr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vinod on 13/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Noterr.db";
    public static final String TABLE_NAME1 = "Todo_main";
    public static final String TABLE_NAME2 = "Todo_content";
    public static final String TABLE_NAME3 = "Cal_sched";
    public static final String TABLE_NAME4 = "Notes_main";
    public static final String TABLE_NAME5 = "Notes_content";
    public static final String COL_11 = "ID";
    public static final String COL_12 = "Desc";
    public static final String COL_13 = "Priority";
    public static final String COL_14 = "Date_crtd";
    public static final String COL_21 = "Seq_no";
    public static final String COL_22 = "ID";
    public static final String COL_23 = "Content";
    public static final String COL_24 = "Completed";
    public static final String COL_31 = "ID";
    public static final String COL_32 = "Name";
    public static final String COL_33 = "Datetime";
    public static final String COL_34 = "Venue";
    public static final String COL_36 = "Date_crtd";
    public static final String COL_41 = "ID";
    public static final String COL_42 = "Desc";
    public static final String COL_43 = "Date_crtd";
    public static final String COL_44 = "Tag";
    public static final String COL_45 = "Bg_color";
    public static final String COL_51 = "Seq_no";
    public static final String COL_52 = "ID";
    public static final String COL_53 = "Cont_type";
    public static final String COL_54 = "Content";
    public static final String COL_61 = "ID";
    public static final String COL_62 = "Notes_ID";
    public static final String COL_63 = "Desc";
    public static final String COL_64 = "Date_crtd";
    public static final String COL_65 = "Tag";
    public static final String COL_66 = "Bg_color";
    public static final String COL_67 = "Date_deltd";
    public static final String COL_71 = "Seq_no";
    public static final String COL_72 = "ID";
    public static final String COL_73 = "Cont_type";
    public static final String COL_74 = "Content";


    SQLiteDatabase database=null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 +"("+COL_11+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + COL_12 +" TEXT NOT NULL," +
                COL_13+" TEXT,"+COL_14+" DATETIME NOT NULL);");
        db.execSQL("create table " + TABLE_NAME2 +"("+COL_21+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_22+" INTEGER NOT NULL,"+COL_23+" TEXT NOT NULL,"
                +COL_24+" INTEGER NOT NULL,"+"FOREIGN KEY ("+COL_22+") REFERENCES "+TABLE_NAME1+"("+COL_11+"));");
        db.execSQL("create table " + TABLE_NAME3 +"("+COL_31+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_32+" TEXT NOT NULL," +
                COL_33+" DATETIME NOT NULL,"+COL_34+" TEXT,"+COL_36+" DATETIME);");
        db.execSQL("create table " + TABLE_NAME4 +"("+COL_41+" INTEGER PRIMARY KEY AUTOINCREMENT," + COL_42 +" TEXT NOT NULL,"+COL_43+" DATETIME NOT NULL," +
                COL_44+" TEXT,"+COL_45+" TEXT NOT NULL);");
        db.execSQL("create table " + TABLE_NAME5 +"("+COL_51+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_52+" INTEGER NOT NULL,"+ COL_53+" TEXT NOT NULL,"
                +COL_54+" TEXT NOT NULL,"+"FOREIGN KEY ("+COL_52+") REFERENCES "+TABLE_NAME4+"("+COL_41+"));");

        database = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);

        onCreate(db);
    }
    //********** Retrieval of records **********

    //Retieve all the records from To-Do main table
    public ArrayList<Todo_main> RetrieveTodo_main()
    {
        ArrayList<Todo_main> todom = new ArrayList<Todo_main>();
        try
        {
            database = this.getReadableDatabase();
            String retrieveQuery = "SELECT * FROM " + TABLE_NAME1;
            Cursor cur = database.rawQuery(retrieveQuery, null);
            if(cur.moveToFirst())
            {
                do{
                    Todo_main tdm = new Todo_main();
                    tdm.setID(cur.getInt(cur.getColumnIndex(COL_11)));
                    tdm.setDesc(cur.getString(cur.getColumnIndex(COL_12)));
                    tdm.setPriority(cur.getString(cur.getColumnIndex(COL_13)));
                    tdm.setDate_crtd(getDateTime(cur.getString(cur.getColumnIndex(COL_14))));
                    todom.add(tdm);
                } while (cur.moveToNext());
            }

        }
        catch(Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }


        return todom;
    }

    //Retieve To-Do data for a particular day or a period
    public ArrayList<Todo_main> RetrieveTodo_main_date(Notes_main_date input)
    {
        ArrayList<Todo_main> todom = new ArrayList<Todo_main>();
        try
        {
            database = this.getReadableDatabase();
            String retrieveQuery = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COL_14 + " >= '"+ getDateTime(input.getStart_date())+"'"+" AND "+ COL_14 + " <= '"+ getDateTime(input.getEnd_date())+"'" ;
            Cursor cur = database.rawQuery(retrieveQuery, null);
            if(cur.moveToFirst())
            {
                do{
                    Todo_main tdm = new Todo_main();
                    tdm.setID(cur.getInt(cur.getColumnIndex(COL_11)));
                    tdm.setDesc(cur.getString(cur.getColumnIndex(COL_12)));
                    tdm.setPriority(cur.getString(cur.getColumnIndex(COL_13)));
                    tdm.setDate_crtd(getDateTime(cur.getString(cur.getColumnIndex(COL_14))));
                    todom.add(tdm);
                } while (cur.moveToNext());
            }

        }
        catch(Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }


        return todom;
    }

    //Retieveing To-Do lists
    public ArrayList<Todo_content> RetrieveTodo_content(Todo_content input)
    {
        ArrayList<Todo_content> todoc = new ArrayList<Todo_content>();
        SQLiteDatabase db = this.getReadableDatabase();
        String retrieveQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + COL_22 + " = " + input.getID();
        Cursor cur = db.rawQuery(retrieveQuery, null);
        if (cur.moveToFirst())
        {
            do{
                Todo_content tdc = new Todo_content();
                tdc.setSeq_no(cur.getInt(cur.getColumnIndex(COL_21)));
                tdc.setID(cur.getInt(cur.getColumnIndex(COL_22)));
                tdc.setContent(cur.getString(cur.getColumnIndex(COL_23)));
                tdc.setCompleted(cur.getInt(cur.getColumnIndex(COL_24)));

                todoc.add(tdc);
            }while(cur.moveToNext());
        }
        return todoc;

    }


    //Retrieve Schedules for a day or for a period(week or month)

    public ArrayList<Cal_sched> RetrieveCal_sched(Cal_shed_retrival input)
    {
        ArrayList<Cal_sched> cshed = new ArrayList<Cal_sched>();
        try
        {
            database = this.getReadableDatabase();
            String retrieveQuery = "SELECT * FROM " + TABLE_NAME3 + " WHERE " + COL_33 + ">= '"+ getDateTime(input.getStart_Datetime())+"'"+" AND "+ COL_33 + "<= '"+ getDateTime(input.getEnd_Datetime())+"'" ;
            Cursor cur = database.rawQuery(retrieveQuery, null);
            if(cur.moveToFirst())
            {
                do{
                    Cal_sched cs = new Cal_sched();
                    cs.setID(cur.getLong(cur.getColumnIndex(COL_31)));
                    cs.setName(cur.getString(cur.getColumnIndex(COL_32)));
                    cs.setDate_time(getDateTime(cur.getString(cur.getColumnIndex(COL_33))));
                    cs.setVenue(cur.getString(cur.getColumnIndex(COL_34)));
                    cs.setDate_crtd(getDateTime(cur.getString(cur.getColumnIndex(COL_36))));
                    cshed.add(cs);
                } while (cur.moveToNext());
            }

        }
        catch(Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }
        return cshed;


    }

    //Retreive all the  Notes

    public ArrayList<Notes_main> RetrieveNotes_main()
    {
        ArrayList<Notes_main> nts_mn = new ArrayList<Notes_main>();

        try{

            database = this.getReadableDatabase();
            String retrieveQuery = "SELECT * FROM " + TABLE_NAME4 ;
            Cursor cur = database.rawQuery(retrieveQuery, null);
            if(cur.moveToFirst())
            {
                do{
                    Notes_main nm = new Notes_main();
                    nm.setID(cur.getLong(cur.getColumnIndex(COL_41)));
                    nm.setDesc(cur.getString(cur.getColumnIndex(COL_42)));
                    nm.setDate_crtd(getDateTime(cur.getString(cur.getColumnIndex(COL_43))));
                    nm.setTag(cur.getString(cur.getColumnIndex(COL_44)));
                    nm.setBg_color(cur.getString(cur.getColumnIndex(COL_45)));
                    nts_mn.add(nm);
                }while (cur.moveToNext());
            }
        }catch(Exception ex)
        {
            Log.d("Exception",ex.getMessage());
        }

        return nts_mn;
    }

    //Retrieving notes for a particular day
    public ArrayList<Notes_main> RetrieveNotes_main_date(Notes_main_date input)
    {
        ArrayList<Notes_main> nts_mn = new ArrayList<Notes_main>();
        try
        {
            database = this.getReadableDatabase();
            String retrieveQuery = "SELECT * FROM " + TABLE_NAME4 +" WHERE "+ COL_43 + " >= '"+ getDateTime(input.getStart_date())+"'"+" AND "+ COL_43 + " <= '"+ getDateTime(input.getEnd_date())+"'"  ;
            Cursor cur = database.rawQuery(retrieveQuery, null);
            if(cur.moveToFirst())
            {
                do{
                    Notes_main nm = new Notes_main();
                    //nm.setID(cur.getInt(cur.getColumnIndex(COL_41)));
                    nm.setID(cur.getLong(cur.getColumnIndex(COL_41)));
                    nm.setDesc(cur.getString(cur.getColumnIndex(COL_42)));
                    nm.setDate_crtd(getDateTime(cur.getString(cur.getColumnIndex(COL_43))));
                    nm.setTag(cur.getString(cur.getColumnIndex(COL_44)));
                    nm.setBg_color(cur.getString(cur.getColumnIndex(COL_45)));
                    nts_mn.add(nm);
                }while (cur.moveToNext());
            }

        }
        catch(Exception ex)
        {
            Log.d("Exception",ex.getMessage());

        }

        return nts_mn;
    }


    //Retrieving Notes content, file name,attachment paths
    public ArrayList<Notes_content> RetrieveNotes_content(Notes_content input) // Retrieving notes content
    {
        ArrayList<Notes_content> nts_cnt = new ArrayList<Notes_content>();
        database = this.getReadableDatabase();
        String retrieveQuery = "SELECT * FROM " + TABLE_NAME5 +" WHERE "+ COL_52 + " = " + input.getID() ;
        Cursor cur = database.rawQuery(retrieveQuery, null);
        if(cur.moveToFirst())
        {
            do{
                Notes_content nc = new Notes_content();
                nc.setSeq_no(cur.getInt(cur.getColumnIndex(COL_51)));
                //nc.setID(cur.getInt(cur.getColumnIndex(COL_52)));
                nc.setID(cur.getLong(cur.getColumnIndex(COL_52)));
                nc.setCont_type(cur.getString(cur.getColumnIndex(COL_53)));
                nc.setContent(cur.getString(cur.getColumnIndex(COL_54)));
                nts_cnt.add(nc);
            }while (cur.moveToNext());
        }
        return nts_cnt;
    }

    // ********* Create records **********


    //Creating a main To-Do
    public long createTODO(Todo_main input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_12,input.getDesc());
        data.put(COL_13,input.getPriority());
        data.put(COL_14,getCurrentdatetime());

        long id = database.insert(TABLE_NAME1,null,data);

        return id;

    }
    //Creating To-Do list
    public long createTODO_content(Todo_content input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_22,input.getID());
        data.put(COL_23,input.getContent());
        data.put(COL_24,input.getCompleted());

        long id = database.insert(TABLE_NAME2,null,data);

        return id;

    }

    //Creating Calendar Schedules
    public long createCal_shed(Cal_sched input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_32,input.getName());
        data.put(COL_33,getDateTime(input.getDate_time()));
        data.put(COL_34,input.getVenue());
        data.put(COL_36,getCurrentdatetime());

        long id = database.insert(TABLE_NAME3,null,data);

        return id;
    }

    //Creating Main data of Notes
    public long createNotes_main(Notes_main input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_42,input.getDesc());
        data.put(COL_43,getCurrentdatetime());
        data.put(COL_44,input.getTag());
        data.put(COL_45,input.getBg_color());

        long id = database.insert(TABLE_NAME4,null,data);

        return id;
    }

    //Creating actual contents, storing file names, attachment paths
    public long createNotes_content(Notes_content input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_52,input.getID());
        data.put(COL_53,input.getCont_type());
        data.put(COL_54,input.getContent());

        long id = database.insert(TABLE_NAME5,null,data);

        return id;
    }

    // ********* Update records **********


    //Update Main To-Do
    public long updateTODO(Todo_main input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_12,input.getDesc());
        data.put(COL_13,input.getPriority());

        long id = database.update(TABLE_NAME1,data,COL_11 + " = " +input.getID(),null);

        return id;

    }

    //Update To-Do list
    public long updateTODO_content(Todo_content input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_23,input.getContent());

        long id = database.update(TABLE_NAME2,data,COL_21 + " = " +input.getSeq_no(),null);

        return id;

    }
    //update To-Do content if the task is accomplished
    public long updateTODO_content_compltd(Todo_content input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_24,input.getCompleted());

        long id = database.update(TABLE_NAME2,data,COL_21 + " = " +input.getSeq_no(),null);

        return id;

    }

    //Update calendar events
    public long updateCal_shed(Cal_sched input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_32,input.getName());
        data.put(COL_33, String.valueOf(input.getDate_time()));
        data.put(COL_34, input.getVenue());
        //data.put(COL_35,input.getLoc());

        long id = database.update(TABLE_NAME3,data,COL_31 +" = " +input.getID(),null);

        return id;
    }

    //Update Notes main Data
    public long updateNotes_main(Notes_main input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_42,input.getDesc());
        data.put(COL_44,input.getTag());
        data.put(COL_45,input.getBg_color());

        long id = database.update(TABLE_NAME4,data,COL_41 +" = " +input.getID(),null);

        return id;
    }

    //Update contents of Notes
    public long updateNotes_content(Notes_content input)
    {
        database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COL_54, input.getContent());

        long id = database.update(TABLE_NAME5,data,COL_51 +" = " +input.getSeq_no(),null);

        return id;
    }



    // ************Delete records*****************


    //Delete To-Do main and To-Do lists together
    public boolean deleteTODO(Todo_main input)
    {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME2,COL_22 + " = " + input.getID(),null); //child table
        database.delete(TABLE_NAME1,COL_11 + " = " +input.getID(),null); // parent table

        return true;

    }
    //Delete To-Do list
    public boolean deleteTODO_content(Todo_content input)
    {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME2,COL_21 + " = " +input.getSeq_no(),null);

        return true;

    }

    //Delete Calendar events
    public boolean deleteCal_shed(Cal_sched input)
    {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME3,COL_31 +" = " +input.getID(),null);

        return true;
    }

    //Delete Notes main data along with contents like file,attachments
    public boolean deleteNotes_main(Notes_main input)
    {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME5,COL_52 +" = "+ input.getID(),null); // Child table
        database.delete(TABLE_NAME4,COL_41 +" = " +input.getID(),null); // parent table

        return true;
    }

    //Delete attachments
    public boolean deleteNotes_content(Notes_content input)
    {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME5,COL_51 +" = " +input.getSeq_no(),null);

        return true;
    }



// ********** raw Query **********

    public Cursor rawQuery(String query,boolean writable)
    {
        if (writable) {

            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery(query, null);
        }
        else
        {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery(query, null);

        }
    }


    // ********** Date logic **********

    public Date getDateTime(String input) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.parse(input);
    }
    public String getDateTime(Date input)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(input);
    }

    public String getCurrentdatetime()  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date dt = new Date();
        return dateFormat.format(dt);
    }
    public Date newDateformat(String input) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = dateFormat.parse(input);

        return date;
    }

}
