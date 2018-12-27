package com.wanderingbme.warhammerunderworldsdeckbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.wanderingbme.warhammerunderworldsdeckbuilder.dao.CardDao;
import com.wanderingbme.warhammerunderworldsdeckbuilder.database.AppDatabase;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Card;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.CardListAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);


        CardDao cardDao = AppDatabase.getInstance(this).cardDao();
        List<Card> cards = cardDao.getAll();
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        ListView listView = findViewById(R.id.add_card_list);
        CardListAdapter cardAdapter = new CardListAdapter(this, R.layout.card_row_layout, cards);
        listView.setAdapter(cardAdapter);

        // TODO: Add filtering
    }
}
