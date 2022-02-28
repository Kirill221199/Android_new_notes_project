package ru.kirill.android_new_notes_project.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.repo.CardData;

public class EditNote extends Fragment {

    CardData cardData;
    EditText title;
    EditText content;
    EditText date;

    public static EditNote newInstance(CardData cardData) {
        EditNote fragment = new EditNote();
        Bundle args = new Bundle();
        args.putParcelable("cardData", cardData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable("cardData");
            ((EditText)view.findViewById(R.id.edit_title)).setText(cardData.getTitle());
            ((EditText)view.findViewById(R.id.edit_content)).setText(cardData.getContent());
            ((EditText)view.findViewById(R.id.edit_date)).setText(cardData.getDate());

        }

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View it) {
                cardData.setTitle(((EditText)view.findViewById(R.id.edit_title)).getText().toString());
                cardData.setContent(((EditText)view.findViewById(R.id.edit_content)).getText().toString());
                cardData.setDate(((EditText)view.findViewById(R.id.edit_date)).getText().toString());

                ((MainActivity) requireActivity()).getPublisher().sendMessage(cardData);
                ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();

            }
        });
    }
}