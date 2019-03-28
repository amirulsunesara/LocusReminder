package com.example.locusreminder.display;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.locusreminder.R;
import com.example.locusreminder.adapter.ReminderAdapter;
import com.example.locusreminder.db.DBManager;
import com.example.locusreminder.db.ReminderData;

import java.util.List;


public class HomeFragment extends Fragment {

    DBManager dbManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton floatingActionButton = ((ViewReminders) getActivity()).getFloatingActionButton();
        floatingActionButton.show();
        ListView reminderList =(ListView)view.findViewById(R.id.listview_reminders);

        dbManager =new DBManager(getContext());


        List<ReminderData> lstReminder = dbManager.getReminderData();

        reminderList.setAdapter(new ReminderAdapter(getActivity(),lstReminder));

        return view;

    }


}
