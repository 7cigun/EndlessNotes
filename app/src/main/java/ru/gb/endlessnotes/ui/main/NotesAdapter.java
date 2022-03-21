package ru.gb.endlessnotes.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.gb.endlessnotes.R;
import ru.gb.endlessnotes.repository.NoteData;
import ru.gb.endlessnotes.repository.NotesSource;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private NotesSource notesSource;

    OnItemClickListener onItemClickListener;

    Fragment fragment;

    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(NotesSource notesSource) {
        this.notesSource = notesSource;
        notifyDataSetChanged();
    }

    NotesAdapter(NotesSource notesSource){
        this.notesSource = notesSource;
    }

    NotesAdapter(){
    }

    NotesAdapter(Fragment fragment){
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NotesViewHolder(layoutInflater.inflate(R.layout.fragment_notes_cardview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bindContentWithLayout(notesSource.getNoteData(position));

    }

    @Override
    public int getItemCount() {
        return notesSource.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final ImageView imageView;
        private final ToggleButton like;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            like = (ToggleButton) itemView.findViewById(R.id.like);

            fragment.registerForContextMenu(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    return false;
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    //view.showContextMenu();
                    return false;
                }
            });

         }

        public void bindContentWithLayout(NoteData content) {
            textViewTitle.setText(content.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ", Locale.getDefault());
            String noteDate = dateFormat.format(content.getDate());
            textViewDescription.setText(content.getDescription() + " " + noteDate );
            imageView.setImageResource(content.getPicture());
            like.setChecked(content.isLike());

        }
    }
}
