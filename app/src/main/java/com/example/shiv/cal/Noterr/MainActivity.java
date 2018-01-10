package com.example.shiv.cal.Noterr;

import android.app.Activity;
import android.os.Bundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        // setContentView(R.layout.activity_main);

/*   //Sytaxes for create,update,delete and retieval

        Todo_main firstRecord = new Todo_main();
        firstRecord.setDesc("Description2");
        firstRecord.setPriority("1");

        dbh.createTODO(firstRecord);

        ArrayList<Todo_main> output = dbh.RetrieveTodo_main();

        Todo_content firstRecord1 = new Todo_content();
        firstRecord1.setID(1);
        firstRecord1.setContent("Complete MC assignment");
        firstRecord1.setCompleted(0);

        dbh.createTODO_content(firstRecord1);

        ArrayList<Todo_content> output1 = dbh.RetrieveTodo_content(firstRecord1);



        String datestr = "2017-11-16 10:00:00";
        Date dt = null;
        try {
            dt = getDateTime(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Cal_sched firstRecord2 = new Cal_sched();
        firstRecord2.setName("abc Meeting");
        firstRecord2.setDate_time(dt);
        firstRecord2.setVenue("abc");
        firstRecord2.setLoc("abc");

        dbh.createCal_shed(firstRecord2);

        Cal_sched retrvRecord = new Cal_sched();
        firstRecord.setDate_crtd(dt);
        ArrayList<Cal_sched> output = dbh.RetrieveCal_sched(firstRecord);




        Notes_main firstRecord3 = new Notes_main();
        firstRecord3.setDesc("MC project");
        firstRecord3.setTag("UPS");
        firstRecord3.setBg_color("White");

        dbh.createNotes_main(firstRecord3);

        ArrayList<Notes_main> output3 = dbh.RetrieveNotes_main();



        ArrayList<Notes_main> output1 = dbh.RetrieveNotes_main_date(firstRecord);
        ArrayList<Notes_main> output2 = dbh.RetrieveNotes_main_tag(firstRecord);


       Notes_content firstRecord4 = new Notes_content();
       firstRecord4.setID(1);
       firstRecord4.setCont_type("Audio");
       firstRecord4.setContent("cars.add(toyota);");

       dbh.createNotes_content(firstRecord4);

       ArrayList<Notes_content> output4 = dbh.RetrieveNotes_content(firstRecord4);


        Bin_main firstRecord5 = new Bin_main();
        firstRecord5.setNotes_ID(1);
        firstRecord5.setDesc("USP grad paper");
        firstRecord5.setDate_crtd(dt);
        firstRecord5.setBg_color("Green1");
        firstRecord5.setTag("USP");

        dbh.createBin_main(firstRecord5);


        ArrayList<Bin_main> output5 = dbh.RetrieveBin_main();




        Bin_content firstRecord6 = new Bin_content();
        firstRecord6.setID(1);
        firstRecord6.setCont_type("Text");
        firstRecord6.setContent("cars.add(bentley);");

        dbh.createBin_content(firstRecord6);

        firstRecord.setID(2);

        ArrayList<Bin_content> output6 = dbh.RetrieveBin_content(firstRecord);



        Todo_main firstRecord = new Todo_main();
        firstRecord.setID(2);
        firstRecord.setDesc("ABC grad paper");
        firstRecord.setPriority("4");

        dbh.updateTODO(firstRecord);

       firstRecord.setDate_crtd(dt);
       ArrayList<Todo_main> output1 = dbh.RetrieveTodo_main_date(firstRecord);


        Todo_content firstRecord = new Todo_content();
        firstRecord.setSeq_no(1);
        firstRecord.setContent("Complete Networks gradpaper");

        dbh.updateTODO_content(firstRecord);

        firstRecord.setID(1);
        ArrayList<Todo_content> output = dbh.RetrieveTodo_content(firstRecord);


        Cal_sched firstRecord = new Cal_sched();
        firstRecord.setID(7);
        firstRecord.setName("MC Meeting");
        firstRecord.setDate_time(dt);
        firstRecord.setVenue("DSU building");
        firstRecord.setLoc("DSU Building");

        dbh.updateCal_shed(firstRecord);



        Notes_main firstRecord = new Notes_main();
        firstRecord.setID(2);
        firstRecord.setDesc("m/c  lrning");
        firstRecord.setTag("M/C grad");
        firstRecord.setBg_color("yellow");

        dbh.updateNotes_main(firstRecord);



        Notes_content firstRecord = new Notes_content();
        firstRecord.setSeq_no(1);
        firstRecord.setCont_type("Text");
        firstRecord.setContent("cars.add(Lambo);");

        dbh.updateNotes_content(firstRecord);



        Todo_main firstRecord = new Todo_main();
        firstRecord.setID(1);
        dbh.deleteTODO(firstRecord);

        ArrayList<Todo_main> output = dbh.RetrieveTodo_main();


        Todo_content firstRecord = new Todo_content();
        firstRecord.setSeq_no(2);
        dbh.deleteTODO_content(firstRecord);

        firstRecord.setID(1);
        ArrayList<Todo_content> output = dbh.RetrieveTodo_content(firstRecord);



        Cal_sched firstRecord = new Cal_sched();
        firstRecord.setID(6);
        dbh.deleteCal_shed(firstRecord);




        ArrayList<Notes_main> output = dbh.RetrieveNotes_main();
        Notes_main firstRecord = new Notes_main();
        firstRecord.setID(1);
        dbh.deleteNotes_main(firstRecord);



        Notes_content firstRecord = new Notes_content();
        firstRecord.setSeq_no(2);
        dbh.deleteNotes_content(firstRecord);





        Bin_main firstRecord = new Bin_main();
        firstRecord.setID(2);
        //firstRecord.setNotes_ID(5);

        dbh.deleteBin_main(firstRecord);


*/


    }

}
