package com.technotalkative.wearablelistviewdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;

/**
 * Created by mgujare on 1/25/16.
 */
public class DataObject implements Parcelable {

    private int id;
    private String name;

    public DataObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<DataObject> getItemList() {
        ArrayList<DataObject> items = new ArrayList<>();
        items.add(new DataObject(1, "First"));
        items.add(new DataObject(2, "Second"));
        items.add(new DataObject(3, "Third"));
        items.add(new DataObject(4, "Fourth"));
        items.add(new DataObject(5, "Fifth"));
        items.add(new DataObject(1, "Sixth"));
        items.add(new DataObject(2, "Seventh"));
        items.add(new DataObject(3, "Eigth"));
        items.add(new DataObject(4, "Ninth"));
        items.add(new DataObject(5, "Tenth"));
        items.add(new DataObject(4, "Eleventh"));
        items.add(new DataObject(5, "Twelth"));

        return items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataObject(DataMap map) {
        this(map.getInt("id"),
                map.getString("name")
        );
    }

    public DataMap putToDataMap(DataMap map) {
        map.putInt("id", id);
        map.putString("name", name);
        return map;
    }

    //Parcelable methods.

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        public DataObject createFromParcel(Parcel in) {
            return new DataObject(in);
        }

        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };

    private DataObject(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
