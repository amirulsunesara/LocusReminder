package com.example.locusreminder.db;

import android.os.Parcel;
import android.os.Parcelable;


public class ReminderData implements Parcelable {
    String title,note,id,location;
    String longitude,latitude;
    String IsDeleted;

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }
//Setdata added
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public ReminderData() {

    }

    public ReminderData(String id, String title, String note, String location, String longitude, String latitude,String IsDeleted) {
        this.title = title;
        this.note = note;
        this.id = id;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.IsDeleted = IsDeleted;
    }

    protected ReminderData(Parcel in) {
        title = in.readString();
        note = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(note);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReminderData> CREATOR = new Creator<ReminderData>() {
        @Override
        public ReminderData createFromParcel(Parcel in) {
            return new ReminderData(in);
        }

        @Override
        public ReminderData[] newArray(int size) {
            return new ReminderData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
