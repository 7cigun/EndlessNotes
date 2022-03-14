package ru.gb.endlessnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.endlessnotes.R;
import ru.gb.endlessnotes.repository.NoteData;
import ru.gb.endlessnotes.repository.NoteSource;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private NoteSource noteSource;

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(NoteSource noteSource) {
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }

    NotesAdapter(NoteSource noteSource){
        this.noteSource = noteSource;
    }

    NotesAdapter(){
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NotesViewHolder(layoutInflater.inflate(R.layout.fragment_notes_cardview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bindContentWithLayout(noteSource.getCardData(position));

    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView imageView;
        private ToggleButton like;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            like = (ToggleButton) itemView.findViewById(R.id.like);

            /*textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });*/
        }

        public void bindContentWithLayout(NoteData content) {
            textViewTitle.setText(content.getTitle());
            textViewDescription.setText(content.getDescription());
            imageView.setImageResource(content.getPicture());
            like.setChecked(content.isLike());

        }
    }
}
