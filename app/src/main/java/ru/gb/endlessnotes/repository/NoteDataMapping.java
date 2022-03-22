package ru.gb.endlessnotes.repository;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields{
        public final static String PICTURE = "picture";
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String LIKE = "like";
    }

    public static NoteData toNoteData(String id, Map<String, Object> doc) {
        long indexPic = (long) doc.get(Fields.PICTURE);
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        NoteData answer = new NoteData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                PictureIndexConverter.getPictureByIndex((int) indexPic),
                (boolean) doc.get(Fields.LIKE),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoteData noteData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, noteData.getTitle());
        answer.put(Fields.DESCRIPTION, noteData.getDescription());
        answer.put(Fields.PICTURE, PictureIndexConverter.getIndexByPicture(noteData.getPicture()));
        answer.put(Fields.LIKE, noteData.isLike());
        answer.put(Fields.DATE, noteData.getDate());
        return answer;
    }
}
