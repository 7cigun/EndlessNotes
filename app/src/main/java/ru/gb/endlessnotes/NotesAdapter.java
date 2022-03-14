package ru.gb.endlessnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private String[] data;

    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    NotesAdapter(String[] data){
        this.data = data;
    }

    NotesAdapter(){
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NotesViewHolder(layoutInflater.inflate(R.layout.fragment_notes_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bindContentWithLayout(data[position]);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public void bindContentWithLayout(String content) {
            textView.setText(content);
        }
    }
}
