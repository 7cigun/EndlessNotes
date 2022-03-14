package ru.gb.endlessnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.endlessnotes.R;
import ru.gb.endlessnotes.repository.CardData;
import ru.gb.endlessnotes.repository.CardSource;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private CardSource cardSource;

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(CardSource cardSource) {
        this.cardSource = cardSource;
        notifyDataSetChanged();
    }

    NotesAdapter(CardSource cardSource){
        this.cardSource = cardSource;
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
        holder.bindContentWithLayout(cardSource.getCardData(position));

    }

    @Override
    public int getItemCount() {
        return cardSource.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView imageView;
        private CheckBox checkBox;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.like);

            /*textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });*/
        }

        public void bindContentWithLayout(CardData content) {
            textViewTitle.setText(content.getTitle());
            textViewDescription.setText(content.getDescription());
            imageView.setImageResource(content.getPicture());
            checkBox.setChecked(content.isLike());

        }
    }
}
