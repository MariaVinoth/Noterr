package com.example.shiv.cal.Noterr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Month_view extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int SelectedID = item.getItemId();
        String Date =getIntent().getStringExtra("Day");
        String Month = getIntent().getStringExtra("Month");
        String Year = getIntent().getStringExtra("Year");
        switch(SelectedID) {    //Handling the menu option on click event
            case R.id.Day:
                Toast.makeText(getBaseContext(),"Day View",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Day_view.class);
                i.putExtra("Day", Date);
                i.putExtra("Month",Month);
                i.putExtra("Year",Year);
                startActivity(i);
                finish();
                break;
            case R.id.Week:
                Toast.makeText(getBaseContext(),"Week View",Toast.LENGTH_SHORT).show();
                Intent j = new Intent(getApplicationContext(), Week_view.class);
                j.putExtra("Day", Date);
                j.putExtra("Month",Month);
                j.putExtra("Year",Year);
                startActivity(j);
                finish();
                break;
            case R.id.Month:
                Toast.makeText(getBaseContext(),"Month View",Toast.LENGTH_SHORT).show();
                Intent k = new Intent(getApplicationContext(), Month_view.class);
                k.putExtra("Day", Date);
                k.putExtra("Month",Month);
                k.putExtra("Year",Year);
                startActivity(k);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            day= getIntent().getStringExtra("Day");
            month = getIntent().getStringExtra("Month");
            year = getIntent().getStringExtra("Year");

            Bundle bundle = new Bundle();
            bundle.putString("Date", day);
            bundle.putString("Month", month);
            bundle.putString("Year", year);

            switch (position) {
                case 0:
                    Month_View_Events tab1 = new Month_View_Events();
                    tab1.setArguments(bundle);
                    return tab1;
                case 1:
                    Month_View_Notes tab2 = new Month_View_Notes();
                    tab2.setArguments(bundle);
                    return tab2;
                case 2:
                    Month_View_To_dos tab3 = new Month_View_To_dos();
                    tab3.setArguments(bundle);
                    return tab3;
                default:
                    return  null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {      //Setting the names for tabs
                case 0:
                    return "Events";
                case 1:
                    return "Notes";
                case 2:
                    return "To Do's";
            }
            return null;
        }
    }
}
