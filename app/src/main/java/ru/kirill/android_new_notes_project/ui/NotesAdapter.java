package ru.kirill.android_new_notes_project.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.kirill.android_new_notes_project.R;
import ru.kirill.android_new_notes_project.repo.CardData;
import ru.kirill.android_new_notes_project.repo.CardSource;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    protected CardSource cardSource;
    OnItemClickListener onItemClickListener;
    MainNotesFragment fragment;
    protected static int menuPosition;

    public static int getMenuPosition() {
        return menuPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this. onItemClickListener = onItemClickListener;
    }

    public void setDataSource(CardSource cardSource) {
        this.cardSource = cardSource;
        notifyDataSetChanged();
    }

    public NotesAdapter(CardSource cardSource) {
        this.cardSource = cardSource;
    }

    public NotesAdapter(MainNotesFragment fragment) {
        this.fragment = fragment;
    }

    public NotesAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater.inflate(R.layout.note_maket,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindContentWithLayout(cardSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return cardSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView content;
        protected TextView date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_note);
            content = (TextView) itemView.findViewById(R.id.content_note);
            date =  (TextView) itemView.findViewById(R.id.date_note);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getLayoutPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    return false;
                }
            });
            fragment.registerForContextMenu(itemView);
        }

        public void bindContentWithLayout(CardData data){
            title.setText(data.getTitle());
            content.setText(data.getContent());
            date.setText("data");
        }
    }
}
