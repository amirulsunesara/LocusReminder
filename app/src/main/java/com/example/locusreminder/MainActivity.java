package com.example.locusreminder;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import com.example.locusreminder.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databselayout);
        Button add = (Button)findViewById(R.id.add);
        Button delete =(Button)findViewById(R.id.delete);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);


        EditText title = (EditText)findViewById(R.id.title);
        EditText detail = (EditText)findViewById(R.id.detail);


    }
    @Override
    public void onClick(View v) {
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "notes", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (v.getId()){
            case R.id.add:
                ContentValues values = new ContentValues();
                values.put("id", 1);
                values.put("Title",R.id.title);
                values.put("Detail",R.id.detail);
                db.insert("notes", null, values);
                break;
            case R.id.delete:
                db.delete("notes", "id=?", new String[]{"1"});
                break;
        }
    }

}
