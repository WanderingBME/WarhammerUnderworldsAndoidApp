package com.wanderingbme.warhammerunderworldsdeckbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wanderingbme.warhammerunderworldsdeckbuilder.dao.CardDao;
import com.wanderingbme.warhammerunderworldsdeckbuilder.database.AppDatabase;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Card;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.CardListAdapter;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Deck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddCardActivity extends AppCompatActivity {

    private static final String ARG_TYPE = "deck_type";
    private static final String ARG_DECK = "deck";

    private String deckType;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        deckType = getIntent().getStringExtra(ARG_TYPE);
        deck = (Deck) getIntent().getSerializableExtra(ARG_DECK);

        CardDao cardDao = AppDatabase.getInstance(this).cardDao();
        List<Card> cards = cardDao.getCardsByWarband(deck.getWarband().getId());
        List<Card> cardsToRemove = new ArrayList<>();
        for (Card c : cards) {
            if (deckType.equalsIgnoreCase("power")) {
                if (!("ploy".equalsIgnoreCase(c.getType()) || "upgrade".equalsIgnoreCase(c.getType()))) {
                    cardsToRemove.add(c);
                }
            } else {
                if (!deckType.equalsIgnoreCase(c.getType())) {
                    cardsToRemove.add(c);
                }
            }
        }
        cards.removeAll(cardsToRemove);
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        ListView listView = findViewById(R.id.add_card_list);
        CardListAdapter cardAdapter = new CardListAdapter(this, R.layout.card_row_layout, cards);
        listView.setAdapter(cardAdapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    deck.addCard((Card) parent.getItemAtPosition(position));//
                    Intent intent = new Intent(parent.getContext(), BuildWarband.class);
                    intent.putExtra("deck", deck);
                    intent.putExtra("view", deckType);
                    startActivity(intent);
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "Objective Deck is full!", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "Card already in list!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
