package ru.kirill.android_new_notes_project.repo;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import ru.kirill.android_new_notes_project.R;

public class FireStoreRepository implements CardSource{

    protected List<CardData> dataSource;
    private static final String CARDS_COLLECTION = "cards";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection(CARDS_COLLECTION);

    public FireStoreRepository () {
        dataSource = new ArrayList<CardData>();
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

    public FireStoreRepository init(FireStoreResponse fireStoreResponse) {
        collectionReference.orderBy(CardDataMapping.Fields.DATE, Query.Direction.ASCENDING ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    dataSource = new ArrayList <CardData>();
                    for (QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                        Map<String, Object> document = queryDocumentSnapshot.getData();
                        String id = queryDocumentSnapshot.getId();
                        dataSource.add(CardDataMapping.toCardData(id,document));
                    }
                }
                fireStoreResponse.initialized(FireStoreRepository.this);
            }
        });
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
        for (CardData cardData: dataSource) {
            collectionReference.document(cardData.getId()).delete();
        }
        dataSource = new ArrayList<CardData>();
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
        collectionReference.document(dataSource.get(position).getId()).delete();
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
        collectionReference.add(CardDataMapping.toDocument(newCardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                newCardData.setId(documentReference.getId());
            }
        });
    }

}