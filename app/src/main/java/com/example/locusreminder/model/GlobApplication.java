package com.example.locusreminder.model;

import android.app.Application;

import com.example.locusreminder.db.ReminderData;

import java.util.ArrayList;
import java.util.List;

public class GlobApplication extends Application {

    public List<ReminderData> getLstDispatchedReminder() {
        return lstDispatchedReminder;
    }

    public void setLstDispatchedReminder(ReminderData rd) {
        this.lstDispatchedReminder.add(rd);
    }

    private List<ReminderData> lstDispatchedReminder=new ArrayList<ReminderData>();


}
