package com.wanderingbme.warhammerunderworldsdeckbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wanderingbme.warhammerunderworldsdeckbuilder.model.CardListAdapter;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Deck;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Warband;

public class ViewDeck extends Fragment {
    private static final String ARG_TYPE = "deck_type";
    private static final String ARG_WARBAND = "warband";

    private CardListAdapter cardAdapter;
    private Warband warband;
    private String deck_type;
    FloatingActionButton fab;

    public ViewDeck() {
        // Required empty public constructor
    }

    public static ViewDeck newInstance(Warband warband, String deck_type) {
        ViewDeck fragment = new ViewDeck();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WARBAND, warband);
        args.putString(ARG_TYPE, deck_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            warband = (Warband) getArguments().getSerializable(ARG_WARBAND);
            deck_type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_deck, container, false);
        Deck deck = ((BuildWarband) getActivity()).getDeck();
        ListView listView = rootView.findViewById(R.id.card_list);
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddCardActivity.class);
                intent.putExtra("deck_type", deck_type);
                intent.putExtra("deck", ((BuildWarband) getActivity()).getDeck());
                startActivity(intent);
            }
        });
        if ("objective".equalsIgnoreCase(deck_type)) {
            cardAdapter = new CardListAdapter(getActivity(), R.layout.card_row_layout, deck.getObjectiveDeck(), true);
            listView.setAdapter(cardAdapter);
        } else if ("power".equalsIgnoreCase(deck_type)) {
            cardAdapter = new CardListAdapter(getActivity(), R.layout.card_row_layout, deck.getPowerDeck(), true);
            listView.setAdapter(cardAdapter);
        }

        return rootView;
    }

}
