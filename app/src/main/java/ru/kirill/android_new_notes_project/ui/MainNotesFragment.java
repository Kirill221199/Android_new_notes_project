package ru.kirill.android_new_notes_project.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.repo.CardData;
import ru.kirill.android_new_notes_project.repo.LocalRepository;

public class MainNotesFragment extends Fragment implements OnItemClickListener {

    NotesAdapter notesAdapter;

    public static MainNotesFragment newInstance() {
        MainNotesFragment fragment = new MainNotesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
    }

    public void initAdapter(){
        notesAdapter = new NotesAdapter();
        LocalRepository localRepository = new LocalRepository(requireContext().getResources());
        notesAdapter.setDataSource(localRepository.init());
        notesAdapter.setOnItemClickListener(MainNotesFragment.this);
    }

    public void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(requireContext(),"Нажали на элемент",Toast.LENGTH_LONG).show();
    }
}