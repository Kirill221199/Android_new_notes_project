package ru.kirill.android_new_notes_project.repo;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ru.kirill.android_new_notes_project.R;

public class LocalRepository implements CardSource{

    protected List<CardData> dataSource;
    protected Resources resources;

    public LocalRepository(Resources resources){
        dataSource = new ArrayList<CardData>();
        this.resources = resources;
    }

    public LocalRepository init(){
        String[] titles = resources.getStringArray(R.array.notes);
        String[] content = resources.getStringArray(R.array.notesContent);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(titles[i],content[i]));
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
}
