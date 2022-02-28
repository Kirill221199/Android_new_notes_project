package ru.kirill.android_new_notes_project.ui.fragment_communication;

import ru.kirill.android_new_notes_project.repo.CardData;

public interface Observer {

    void receiveMessage (CardData cardData);

}
