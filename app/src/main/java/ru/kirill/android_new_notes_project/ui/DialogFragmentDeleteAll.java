package ru.kirill.android_new_notes_project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.kirill.android_new_notes_project.repo.CardSource;

public class DialogFragmentDeleteAll extends DialogFragment {

    CardSource data;
    NotesAdapter notesAdapter;

    public DialogFragmentDeleteAll(CardSource data, NotesAdapter notesAdapter) {
        this.data = data;
        this.notesAdapter = notesAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Sure you want to delete all notes?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    data.ClearAllCardsData();
                    notesAdapter.notifyDataSetChanged();
                    showToast("Notes delete!");
                })
                .setNegativeButton("No", (dialog, which) -> {
                    showToast("Notes not deleted.");
                })
                .show();

    }

    protected void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
