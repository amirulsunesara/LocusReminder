package com.example.locusreminder.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locusreminder.MapsActivity;
import com.example.locusreminder.R;
import com.example.locusreminder.db.DBManager;
import com.example.locusreminder.db.ReminderData;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText textView,textView1;
    TextView location_text;
    Button save_button,cancel_button;
    public  String latitue,longitude,selected_place,title,note_text;
    DBManager dbManager;
    ReminderData reminderData;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewReminders.class));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        textView = (EditText)findViewById(R.id.textView);
        textView1=(EditText)findViewById(R.id.textView1);
        Button btn = findViewById(R.id.AddLocationButton);
        location_text=(TextView)findViewById(R.id.location_text);
        save_button = (Button)findViewById(R.id.save_button);
        cancel_button=(Button)findViewById(R.id.cancel_button);
        Intent intent_get_data = getIntent();
        latitue = intent_get_data.getStringExtra("Latitude");
        longitude = intent_get_data.getStringExtra("Longitude");
        selected_place = intent_get_data.getStringExtra("selected_pace");
        title = intent_get_data.getStringExtra("title");
        note_text = intent_get_data.getStringExtra("NoteText");
        textView.setText(title);
        textView1.setText(note_text);
        location_text.setText(selected_place);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewReminders.class);
                if( textView.getText().toString().equals("")){
                     Toast.makeText(getApplicationContext(),"Note Title field cannot be empty",Toast.LENGTH_LONG).show();
                }
                else if(textView1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Note text field cannot be empty",Toast.LENGTH_LONG).show();
                }
                else if(location_text.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Add location to get reminders",Toast.LENGTH_LONG).show();
                }
                else {

                    UUID unqId = UUID.randomUUID();
                    String strUnqId = unqId.toString();

                    dbManager.insertReminderData(strUnqId,textView.getText().toString(),textView1.getText().toString(),location_text.getText().toString(),longitude,latitue,"0");

                    startActivity(intent);
                }
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewReminders.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("Title",textView.getText().toString());
                intent.putExtra("NoteText",textView1.getText().toString());
                startActivity(intent);
            }
        });
    }
    private void hideSoftkeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
