package ru.kirill.android_new_notes_project.repo;

import java.util.List;

public interface CardSource {
    int size();
    List<CardData> getAllCardsData();
    CardData getCardData(int position);
}
