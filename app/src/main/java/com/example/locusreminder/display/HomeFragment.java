package com.example.locusreminder.display;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.locusreminder.R;
import com.example.locusreminder.adapter.ReminderAdapter;
import com.example.locusreminder.db.DBManager;
import com.example.locusreminder.db.ReminderData;

import java.util.List;


public class HomeFragment extends Fragment {

    DBManager dbManager;
    private ListView reminderList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton floatingActionButton = ((ViewReminders) getActivity()).getFloatingActionButton();
        floatingActionButton.show();
        reminderList =(ListView)view.findViewById(R.id.listview_reminders);

        dbManager =new DBManager(getContext());


        final List<ReminderData> lstReminder = dbManager.getReminderData();

        reminderList.setAdapter(new ReminderAdapter(getActivity(),lstReminder));

        reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReminderData reminderData =(ReminderData) lstReminder.get(i);
                Intent intent = new Intent( getActivity(), MainActivity.class);
                intent.putExtra("Latitude",reminderData.getLatitude());
                intent.putExtra("Longitude",reminderData.getLongitude());
                intent.putExtra("selected_pace",reminderData.getLocation());
                intent.putExtra("title",reminderData.getTitle());
                intent.putExtra("NoteText",reminderData.getNote());
                intent.putExtra("mode","update");
                intent.putExtra("id",reminderData.getId());

                startActivity(intent);

            }
        });

        return view;

    }


}
