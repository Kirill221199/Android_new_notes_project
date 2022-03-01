package ru.kirill.android_new_notes_project.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.zip.Inflater;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.repo.CardData;
import ru.kirill.android_new_notes_project.repo.CardSource;
import ru.kirill.android_new_notes_project.repo.LocalRepository;
import ru.kirill.android_new_notes_project.ui.fragment_communication.Observer;

public class MainNotesFragment extends Fragment implements OnItemClickListener {

    NotesAdapter notesAdapter;
    RecyclerView recyclerView;
    CardSource data;
    DialogFragmentDeleteAll dialogFragmentDeleteAll;
    CardData cardData;

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
                data.addCardData(new CardData("New note " + (data.size()+1), "Content New note",
                        myCalendar()));
                notesAdapter.notifyItemInserted((data.size()-1));
                recyclerView.smoothScrollToPosition((data.size()-1));
                return true;
            }
            case (R.id.action_clear_all) : {
                dialogFragmentDeleteAll = new DialogFragmentDeleteAll(data, notesAdapter);
                dialogFragmentDeleteAll.show(getActivity().getSupportFragmentManager(), "tag");
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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        requireActivity().getMenuInflater().inflate(R.menu.context_menu_card, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = NotesAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case (R.id.action_delete): {
                data.deleteCardData(menuPosition);
                notesAdapter.notifyItemRemoved(menuPosition);
                return true;
            }
            case (R.id.action_update): {
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(CardData cardData) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        data.updateCardData(menuPosition, cardData);
                        notesAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getSupportFragmentManager().beginTransaction().add(R.id.activity_container, EditNote.newInstance(data.getCardData(menuPosition))).addToBackStack(" ").commit();
                return true;
            }
            case (R.id.action_send): {
                Toast.makeText(requireContext(),"Note "+ (menuPosition +1) +" send...",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    public void initAdapter(){
        notesAdapter = new NotesAdapter(this);
        data = new LocalRepository(requireContext().getResources()).init();
        notesAdapter.setDataSource(data);
        notesAdapter.setOnItemClickListener(this);
    }

    public void initRecycler(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(1000);
        animator.setRemoveDuration(1000);
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(requireContext(),"Нажали на элемент " + position,Toast.LENGTH_SHORT).show();
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

}