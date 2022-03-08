package ru.kirill.android_new_notes_project.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.repo.CardData;

public class EditNote extends Fragment {

    CardData cardData;
    String day;
    TextView data;

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
        setChanges(view);
        saveChanges(view);
    }

    public void setChanges(View view){
        if (getArguments() != null) {
            cardData = getArguments().getParcelable("cardData");
            ((EditText) view.findViewById(R.id.edit_title)).setText(cardData.getTitle());
            ((EditText) view.findViewById(R.id.edit_content)).setText(cardData.getContent());
            ((TextView) view.findViewById(R.id.edit_date)).setText(cardData.getDate());

        }

        data = view.findViewById(R.id.edit_date);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(
                                    DatePicker view, int year, int monthOfYear,
                                    int dayOfMonth
                            ) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                day = dayOfWeek(calendar);
                                data.setText("Date: " + String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear)
                                        + "." + String.valueOf(year) + ", " + day);
                            }
                        }, yy, mm, dd);
                datePicker.show();
            }
        });
    }

    public void saveChanges(View view){
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View it) {
                cardData.setTitle(((EditText) view.findViewById(R.id.edit_title)).getText().toString());
                cardData.setContent(((EditText) view.findViewById(R.id.edit_content)).getText().toString());
                cardData.setDate(((TextView) view.findViewById(R.id.edit_date)).getText().toString());

                ((MainActivity) requireActivity()).getPublisher().sendMessage(cardData);
                ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();

                hideKeyboard(requireContext());
            }
        });
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null) return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public String dayOfWeek(Calendar calendar) {
        String ww = "day of week";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
            ww = "Monday";
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            ww = "Tuesday";
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            ww = "Wednesday";
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            ww = "Thursday";
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            ww = "Friday";
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            ww = "Saturday";
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            ww = "Sunday";
        }

        return ww;
    }



}