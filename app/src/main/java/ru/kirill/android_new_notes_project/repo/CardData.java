package ru.kirill.android_new_notes_project.repo;

import android.os.Parcel;
import android.os.Parcelable;

public class CardData implements Parcelable {

    protected String title;
    protected String content;
    protected String date;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CardData(String date) {
        this.date = date;
    }

    public CardData(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        content = in.readString();
        date = in.readString();
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(date);
    }
}
