package ru.kirill.android_new_notes_project.repo;

public class CardData {

    protected String title;
    protected String content;

    public CardData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
