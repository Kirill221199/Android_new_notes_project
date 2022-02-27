package ru.kirill.android_new_notes_project.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.zip.Inflater;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.repo.CardData;
import ru.kirill.android_new_notes_project.repo.CardSource;
import ru.kirill.android_new_notes_project.repo.LocalRepository;

public class MainNotesFragment extends Fragment implements OnItemClickListener {

    NotesAdapter notesAdapter;
    CardSource data;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_add_note) : {
                data.addCardData(new CardData("New note " + (data.size()+1), "Content New note"));
                notesAdapter.notifyItemInserted((data.size()-1));
                return true;
            }
            case (R.id.action_clear_all) : {
                data.ClearAllCardsData();
                notesAdapter.notifyDataSetChanged();
                return true;
            }
            case (R.id.action_about_developer) : {
                DeveloperInfo developer_info = new DeveloperInfo();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_container,
                        developer_info).addToBackStack(" ").commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void initAdapter(){
        notesAdapter = new NotesAdapter();
        data = new LocalRepository(requireContext().getResources()).init();
        notesAdapter.setDataSource(data);
        notesAdapter.setOnItemClickListener(this);

    }

    public void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(requireContext(),"Нажали на элемент " + position,Toast.LENGTH_SHORT).show();
    }
}