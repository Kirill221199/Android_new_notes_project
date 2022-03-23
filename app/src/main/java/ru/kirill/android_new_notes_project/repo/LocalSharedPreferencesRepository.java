package ru.kirill.android_new_notes_project.repo;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocalSharedPreferencesRepository implements CardSource{
    protected List<CardData> dataSource;
    protected SharedPreferences sharedPreferences;

    public LocalSharedPreferencesRepository(SharedPreferences sharedPreferences) {
        dataSource = new ArrayList<CardData>();
        this.sharedPreferences = sharedPreferences;
    }

    static final String KEY_CELL_R = "cell";
    public static final String KEY_SP_R = "key_sp";

    public LocalSharedPreferencesRepository init() {
        String savedCard = sharedPreferences.getString(KEY_CELL_R, null);
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
        editor.putString(KEY_CELL_R, null);
        editor.apply();
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_R, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_R, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_R, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void clearCardData(int position, CardData CardData) {

    }

}
