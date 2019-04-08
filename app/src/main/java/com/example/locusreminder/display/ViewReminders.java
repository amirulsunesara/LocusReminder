package com.example.locusreminder.display;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.locusreminder.R;
import com.example.locusreminder.adapter.ReminderAdapter;
import com.example.locusreminder.db.DBManager;
import com.example.locusreminder.db.ReminderData;
import com.example.locusreminder.model.GlobApplication;
import com.example.locusreminder.model.ReminderModel;
import com.example.locusreminder.service.LocationService;

import java.util.ArrayList;
import java.util.List;

public class ViewReminders extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
    FloatingActionButton fab;

    DBManager dbManager;
    private BroadcastReceiver broadcastReceiver;


    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminders);
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.view_reminders);

     //   lstDispatchedReminder = new ArrayList<ReminderData>();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        dbManager = new DBManager(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
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

        View headerView = navigationView.getHeaderView(0);
        TextView tvPhoneName = headerView.findViewById(R.id.tvPhoneName);
        tvPhoneName.setText(Build.MODEL);

        reqPermission();

        Intent i =new Intent(getApplicationContext(), LocationService.class);
        startService(i);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private boolean reqPermission() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            }else {
                reqPermission();
            }
        }
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

  //navigation on settings and help and documentation
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.btnSettings) {
             setTitle(R.string.action_settings);
             getSupportFragmentManager()
                     .beginTransaction()
                     .replace(R.id.fragment_container, new SettingsFragment())
                     .commit();
        }
        else if (id == R.id.btnHelp) {
             setTitle(R.string.help);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HelpFragment()).commit();

        }
        else if(id== R.id.btnHome){
             setTitle(R.string.view_reminders);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createNotification(int id,ReminderData reminderData)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Latitude",reminderData.getLatitude());
        intent.putExtra("Longitude",reminderData.getLongitude());
        intent.putExtra("selected_pace",reminderData.getLocation());
        intent.putExtra("title",reminderData.getTitle());
        intent.putExtra("NoteText",reminderData.getNote());
        intent.putExtra("mode","update");
        intent.putExtra("id",reminderData.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Channel")
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("Reminder alert: "+reminderData.getTitle())
                .setContentText(reminderData.getNote())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(reminderData.getNote()))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Channel";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "description",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notificationVibrate = sharedPref.getBoolean("notificationVibrate", true);
        boolean notificationSound = sharedPref.getBoolean("notificationSound",true);


        //trigger tone
        if(notificationSound) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);
        }


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, builder.build());


        //vibrate phone
        if(notificationVibrate)
        VibratePhone();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Double longitude1 = Double.parseDouble(intent.getExtras().get("longitudeCoordinate").toString());
                    Double latitude2 = Double.parseDouble(intent.getExtras().get("latitudeCoordinate").toString());
                    Location location1= new Location("");
                    location1.setLatitude(latitude2);
                    location1.setLongitude(longitude1);
                    List<ReminderData> lstReminder = dbManager.getReminderData();

                    int i=0;
                    for (ReminderData rd: lstReminder) {

                        if(rd.getIsDeleted().equals("0")){
                            Location location2 = new Location("");
                            location2.setLatitude(Double.parseDouble(rd.getLatitude()));
                            location2.setLongitude(Double.parseDouble(rd.getLongitude()));

                            float distance = location1.distanceTo(location2);

                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            String range = sharedPref.getString("lstRange","100");
                            if(distance <= Integer.parseInt(range) && !isNotifationDispatched(rd))
                            {
                                createNotification(i,rd);
                                dispatchNotification(rd);
                            }

                        }
                        i++;
                    }




                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("locationCoordinates"));
    }
    //to check notification is displayed
    public boolean isNotifationDispatched(ReminderData rd)
    {

        List<ReminderData> reminders = ((GlobApplication) this.getApplication()).getLstDispatchedReminder();
        boolean isDispatched = false;
        for (ReminderData r:reminders) {
            if(r.getId().equals(rd.getId()))
            {
                isDispatched = true;
            }
        }
        return  isDispatched;

    }
    public void VibratePhone(){

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(1000);
        }
    }
    public void dispatchNotification(ReminderData rd)
    {

        ((GlobApplication)this.getApplication()).setLstDispatchedReminder(rd);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }


}



