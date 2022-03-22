package ru.kirill.android_new_notes_project.repo;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {

    public static class Fields {
        public final static String TITLE = "title";
        public final static String CONTENT = "content";
        public final static String DATE = "date";
    }

    public static CardData toCardData(String id, Map<String, Object> doc) {
        CardData answer = new CardData(
                ((String)doc.get(Fields.TITLE)),
                ((String)doc.get(Fields.CONTENT)),
                ((String)doc.get(Fields.DATE))
        );
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(CardData cardData){
        Map<String,Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.CONTENT, cardData.getContent());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }

}
