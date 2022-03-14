package ru.gb.endlessnotes.repository;

import java.util.List;

public interface CardSource {
    int size();
    List<CardData> getAllCardData();
    CardData getCardData(int position);
}
