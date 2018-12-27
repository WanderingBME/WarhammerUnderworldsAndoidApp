package com.wanderingbme.warhammerunderworldsdeckbuilder.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;

import com.wanderingbme.warhammerunderworldsdeckbuilder.R;
import com.wanderingbme.warhammerunderworldsdeckbuilder.dao.CardDao;
import com.wanderingbme.warhammerunderworldsdeckbuilder.dao.WarbandDao;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Card;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Warband;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by JT on 2/27/2018.
 */
@Database(version = 1, entities = {Warband.class, Card.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract WarbandDao warbandDao();

    public abstract CardDao cardDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                loadWarbands(context, db);
                loadCards(context, db);
            }
        };


        // TODO: Remove main thread queries
        return Room.databaseBuilder(context,
                AppDatabase.class, "DeckBuilder.db").addCallback(rdc).allowMainThreadQueries().build();
    }

    private static void loadWarbands(Context context, SupportSQLiteDatabase db) {
        XmlResourceParser parser = context.getResources().getXml(R.xml.warbands);
        int eventType;
        try {
            eventType = parser.getEventType();
            ContentValues values = null;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    if ("warband".equalsIgnoreCase(parser.getName())) {
                        values = new ContentValues();
                        values.put("name", parser.getAttributeValue(null, "name"));
                        values.put("Released", Boolean.valueOf(parser.getAttributeValue(null, "released")));
                        values.put("Icon", parser.getAttributeValue(null, "icon"));
                    }
                }

                if (eventType == XmlResourceParser.END_TAG) {
                    if ("warband".equalsIgnoreCase(parser.getName())) {
                        db.insert("Warband", OnConflictStrategy.IGNORE, values);
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCards(Context context, SupportSQLiteDatabase db) {
        XmlResourceParser parser = context.getResources().getXml(R.xml.cards);
        int eventType;
        try {
            eventType = parser.getEventType();
            ContentValues values = null;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    if ("card".equalsIgnoreCase(parser.getName())) {
                        values = new ContentValues();
                        values.put("Id", parser.getAttributeValue(null, "id"));
                        String title = Html.fromHtml(parser.getAttributeValue(null, "title"), 0).toString();
                        values.put("Title", title);
                        values.put("Expansion", Boolean.valueOf(parser.getAttributeValue(null, "set")));
                        values.put("Type", parser.getAttributeValue(null, "type"));
                        String warbandName = parser.getAttributeValue(null, "warband");
                        if (!"universal".equalsIgnoreCase(warbandName)) {
                            Cursor c = db.query("SELECT Id from Warband where Name = \"" + warbandName + "\"");
                            c.moveToFirst();
                            int warbandId = c.getInt(0);
                            values.put("WarbandId", warbandId);
                        } else {
                            values.put("WarbandId", -1);
                        }
                        values.put("Image", parser.getAttributeValue(null, "image"));
                    }
                }

                if (eventType == XmlResourceParser.END_TAG) {
                    if ("card".equalsIgnoreCase(parser.getName())) {
                        db.insert("Card", OnConflictStrategy.IGNORE, values);
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
