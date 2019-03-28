package com.example.locusreminder.display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.locusreminder.R;
import com.example.locusreminder.adapter.ReminderAdapter;
import com.example.locusreminder.model.ReminderModel;

import java.util.ArrayList;
import java.util.List;

public class ViewReminders extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminders);
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewReminders.this, MainActivity.class);
                intent.putExtra("Latitude","");
                intent.putExtra("Longitude","");
                intent.putExtra("selected_pace","");
                intent.putExtra("title","");
                intent.putExtra("NoteText","");
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView reminderList = findViewById(R.id.listview_reminders);
        ReminderModel reminderModel = new ReminderModel("Purchase Book","Borrow alchemist book from library");
        ReminderModel reminderModel2 = new ReminderModel("Study","Study Data warehousing in library");

        List<ReminderModel> lstReminder = new ArrayList<ReminderModel>();
        lstReminder.add(reminderModel);
        lstReminder.add(reminderModel2);

        reminderList.setAdapter(new ReminderAdapter(activity,lstReminder));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.btnHelp) {
            //for help screen

        } else if (id == R.id.btnSettings) {
            //for settings screen
        }
        else if(id== R.id.btnHome){
            //for home screen

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
