package com.example.locusreminder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.locusreminder.R;
import com.example.locusreminder.db.ReminderData;

import java.util.List;

public class ReminderAdapter extends BaseAdapter {

    private List<ReminderData> lstReminder;
    private Activity activity;

    public ReminderAdapter(Activity activity, List<ReminderData> reminderModelList){

        this.lstReminder = reminderModelList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return lstReminder.size();
    }

    @Override
    public Object getItem(int i) {
        return lstReminder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewholder;
        if (view == null) {
            view = inflater.inflate(R.layout.listview_reminder_items, viewGroup, false);

            viewholder = new ViewHolder();

            viewholder.note_title = view.findViewById(R.id.note_title);
            viewholder.note_text = view.findViewById(R.id.note_text);

            view.setTag(viewholder);

        } else {
            viewholder = (ViewHolder) view.getTag();
        }

        setReminderData(viewholder,lstReminder.get(i));

        return view;
    }


    private void setReminderData(ViewHolder viewholder, ReminderData reminderModel) {
        viewholder.note_text.setText(reminderModel.getNote());
        viewholder.note_title.setText(reminderModel.getTitle());
    }

    private static class ViewHolder {
        private TextView note_title;
        private TextView note_text;
    }
}
