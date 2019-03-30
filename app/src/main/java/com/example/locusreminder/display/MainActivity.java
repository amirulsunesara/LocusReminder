package com.example.locusreminder.display;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    EditText textView1;
    TextView location_text;
    Button save_button,cancel_button,delete_button;
    public  String latitue,longitude,selected_place,title,note_text,id,mode;
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
        setTitle(R.string.create_reminders);

        backNavigation();
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
        delete_button=(Button)findViewById(R.id.delete_button);
        Intent intent_get_data = getIntent();
        latitue = intent_get_data.getStringExtra("Latitude");
        longitude = intent_get_data.getStringExtra("Longitude");
        selected_place = intent_get_data.getStringExtra("selected_pace");
        title = intent_get_data.getStringExtra("title");
        note_text = intent_get_data.getStringExtra("NoteText");
        id = intent_get_data.getStringExtra("id");
        mode = intent_get_data.getStringExtra("mode");
        if(mode != null) {
            delete_button.setVisibility(View.VISIBLE);
            save_button.setText("Update");
            text.setText(title);
            textView1.setText(note_text);
            location_text.setText(selected_place);
            btn.setText("Update Location");
            setTitle(R.string.edit_reminders);

        }
       text.setText(title);
        setImage(title);
        textView1.setText(note_text);
        location_text.setText(selected_place);
        dbManager = new DBManager(this);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this,ViewReminders.class);
                        dbManager.updateReminderData(id,text.getText().toString(), textView1.getText().toString(), location_text.getText().toString(), longitude, latitue, "1");
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        final AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to delete reminder?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
        //Toast notification for field validation
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewReminders.class);
                if(textView1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Note text field cannot be empty",Toast.LENGTH_LONG).show();
                }
                else if( text.getText().toString().equals("")){
                     Toast.makeText(getApplicationContext(),"Note Title field cannot be empty",Toast.LENGTH_LONG).show();
                }

                else if(location_text.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Add location to get reminders",Toast.LENGTH_LONG).show();
                }
                else {
                    if(mode != null)
                    {
                        dbManager.updateReminderData(id,text.getText().toString(), textView1.getText().toString(), location_text.getText().toString(), longitude, latitue, "0");
                        startActivity(intent);
                    }
                    else {
                        String strUnqId = randomNumber();
                        dbManager.insertReminderData(strUnqId, text.getText().toString(), textView1.getText().toString(), location_text.getText().toString(), longitude, latitue, "0");
                        startActivity(intent);
                    }
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
                intent.putExtra("selected_pace",selected_place);
                intent.putExtra("Latitude",latitue);
                intent.putExtra("Longitude",longitude);
                intent.putExtra("mode",mode);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                builder.show();

            }
        });
    }

    private  void backNavigation(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ViewReminders.class);
        startActivityForResult(myIntent, 0);
        return true;
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
