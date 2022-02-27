package ru.kirill.android_new_notes_project.repo;

import java.util.List;

public interface CardSource {
    int size();
    List<CardData> getAllCardsData();
    CardData getCardData(int position);
    public void ClearAllCardsData();
    public void addCardData(CardData cardData);
    public void deleteCardData (int position);
    public void updateCardData(int position, CardData newCardData);

}
