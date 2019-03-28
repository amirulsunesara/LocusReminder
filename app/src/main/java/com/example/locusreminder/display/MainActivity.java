package com.example.locusreminder.display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locusreminder.MapsActivity;
import com.example.locusreminder.R;
import com.example.locusreminder.db.DBManager;
import com.example.locusreminder.db.ReminderData;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //EditText textView,textView1;
    EditText textView1;
    TextView location_text;
    Button save_button,cancel_button;
    public  String latitue,longitude,selected_place,title,note_text;
    DBManager dbManager;
    ReminderData reminderData;
    AutoCompleteTextView text;
    ImageView image;
    //List used for title
    String[] list1={"Library","Restaurant","Movie","Train","Bus",
            "Flight","Grocery","Books","Shopping","Hospital","Office",
            "Work","Temple","Church","Zoo","Home","Museum","Apartment"};

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewReminders.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        text = (AutoCompleteTextView)findViewById(R.id.textView);

        image=findViewById(R.id.image);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list1);
        text.setAdapter(adapter);
        text.setThreshold(1);
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String list = (String) parent.getItemAtPosition(position);
                setImage(list);
            }


        });

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
        text.setText(title);
        setImage(title);
        textView1.setText(note_text);
        location_text.setText(selected_place);
        dbManager = new DBManager(this);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewReminders.class);
                if( text.getText().toString().equals("")){
                     Toast.makeText(getApplicationContext(),"Note Title field cannot be empty",Toast.LENGTH_LONG).show();
                }
                else if(textView1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Note text field cannot be empty",Toast.LENGTH_LONG).show();
                }
                else if(location_text.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Add location to get reminders",Toast.LENGTH_LONG).show();
                }
                else {

                    String strUnqId =randomNumber();
                    dbManager.insertReminderData(strUnqId,text.getText().toString(),textView1.getText().toString(),location_text.getText().toString(),longitude,latitue,"0");
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
                intent.putExtra("Title",text.getText().toString());
                intent.putExtra("NoteText",textView1.getText().toString());
                startActivity(intent);
            }
        });
    }
    //Generate id for each notification
    private String randomNumber(){
        UUID unqId = UUID.randomUUID();
        String strUnqId = unqId.toString();
        return strUnqId;
    }
    //code to hide key board
    private void hideSoftkeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    //Code to set image for title
    public void setImage(String list){
        if(list.equals("Restaurant")){
            image.setImageResource(R.drawable.ic_local_cafe_black_24dp);
        }
        if(list.equals("Library")){
            image.setImageResource(R.drawable.ic_library_books_black_24dp);
        }
        if(list.equals("Train")){
            image.setImageResource(R.drawable.ic_train_black_24dp);
        }
        if(list.equals("Bus")){
            image.setImageResource(R.drawable.ic_directions_bus_black_24dp);
        }
        if(list.equals("Movie")){
            image.setImageResource(R.drawable.ic_movie_black_24dp);
        }
        if(list.equals("Flight")){
            image.setImageResource(R.drawable.ic_flight_black_24dp);
        }
        if(list.equals("Grocery")){
            image.setImageResource(R.drawable.ic_new_releases_black_24dp);
        }
        if(list.equals("Books")){
            image.setImageResource(R.drawable.ic_library_books_black_24dp);
        }
        if(list.equals("Shopping")){
            image.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
        }
        if(list.equals("Hospital")){
            image.setImageResource(R.drawable.ic_new_releases_black_24dp);
        }
        if(list.equals("Office")){
            image.setImageResource(R.drawable.ic_new_releases_black_24dp);
        }
    }
}
