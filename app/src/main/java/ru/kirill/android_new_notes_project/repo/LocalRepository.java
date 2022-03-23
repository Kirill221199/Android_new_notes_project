package ru.kirill.android_new_notes_project.repo;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.kirill.android_new_notes_project.R;

public class LocalRepository implements CardSource {

    protected List<CardData> dataSource;
    protected Resources resources;

    public LocalRepository(Resources resources) {
        dataSource = new ArrayList<CardData>();
        this.resources = resources;
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

    public LocalRepository init() {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] content = resources.getStringArray(R.array.notesContent);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(titles[i], content[i], myCalendar()));
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
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
    }

    @Override
    public void clearCardData(int position, CardData CardData) {

    }

}
