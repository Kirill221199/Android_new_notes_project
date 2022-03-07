package ru.kirill.android_new_notes_project.repo;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.kirill.android_new_notes_project.R;

public class LocalSharedPreferencesRepository implements CardSource{
    protected List<CardData> dataSource;
    protected SharedPreferences sharedPreferences;
    protected String date;

    public LocalSharedPreferencesRepository(SharedPreferences sharedPreferences) {
        dataSource = new ArrayList<CardData>();
        this.sharedPreferences = sharedPreferences;
    }

    public String myCalendar() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        String ww = "day of week";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 1){ww = "Monday";}
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 2){ww = "Tuesday";}
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 3){ww = "Wednesday";}
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 4){ww = "Thursday";}
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 5){ww = "Friday";}
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 6){ww = "Saturday";}
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 7){ww = "Sunday";}

        String date = "Date: " + dd + "." + mm + "." + yy + ", " + ww;
        return date;
    }

    static final String KEY_CELL = "key";
    static final String KEY_SP = "key_sp";

    public LocalSharedPreferencesRepository init() {
        String savedCard = sharedPreferences.getString(KEY_CELL, null);
        if (savedCard !=null){
            Type type = new TypeToken<ArrayList<CardData>>(){}.getType();
            dataSource = (new GsonBuilder().create().fromJson(savedCard, type));

        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public List<CardData> getAllCardsData() {
        return dataSource;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public void ClearAllCardsData() {
        dataSource.clear();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL, null);
        editor.apply();
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }
}
