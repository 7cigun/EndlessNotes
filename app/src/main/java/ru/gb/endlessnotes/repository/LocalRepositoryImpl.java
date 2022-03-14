package ru.gb.endlessnotes.repository;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import ru.gb.endlessnotes.R;

public class LocalRepositoryImpl implements CardSource {

    private List<CardData> dataSource;
    private Resources resources;

    public LocalRepositoryImpl(Resources resources){
        dataSource = new ArrayList<CardData>();
        this.resources = resources;
    }

    public LocalRepositoryImpl init(){
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(titles[i],descriptions[i], pictures.getResourceId(i,0),false));
        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public List<CardData> getAllCardData() {
        return dataSource;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }
}
