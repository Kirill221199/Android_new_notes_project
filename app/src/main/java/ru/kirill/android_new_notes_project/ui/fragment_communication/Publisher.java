package ru.kirill.android_new_notes_project.ui.fragment_communication;

import java.util.ArrayList;
import java.util.List;

import ru.kirill.android_new_notes_project.repo.CardData;

public class Publisher {

    List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void sendMessage (CardData cardData){
        for(Observer observer:observers){
            observer.receiveMessage(cardData);
        }
    }

    public void subscribe (Observer observer){
        observers.add(observer);
    }

    public void unsubscribe (Observer observer){
        observers.remove(observer);
    }


}
