package com.wanderingbme.warhammerunderworldsdeckbuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Deck;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Warband;

public class BuildWarband extends AppCompatActivity {

    private SectionPagerAdapter adapter;
    private Deck deck;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private Warband warband;

    // TODO: Add save option
    // TODO: Fix card count after remove

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_warband);
        deck = (Deck) getIntent().getSerializableExtra("deck");
        if (deck != null) {
            warband = deck.getWarband();
        } else {
            warband = (Warband) getIntent().getSerializableExtra("warband");
            deck = new Deck(warband);
        }
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        fragmentManager = getSupportFragmentManager();
        adapter = new SectionPagerAdapter(fragmentManager, warband);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if ("power".equalsIgnoreCase(getIntent().getStringExtra("view"))) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            if (tab != null) {
                tab.select();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
//                .setTitle("Title")
                .setMessage("Do you really leave? Any unsaved changes will be discarded")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent setIntent = new Intent(getApplicationContext(), HomeScreen.class);
                        startActivity(setIntent);
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    public Deck getDeck() {
        return deck;
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        Warband warband;
        ViewDeck objectiveView;
        ViewDeck powerView;

        SectionPagerAdapter(FragmentManager fm, Warband warband) {
            super(fm);
            this.warband = warband;
            objectiveView = ViewDeck.newInstance(warband, "objective");
            powerView = ViewDeck.newInstance(warband, "power");
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return objectiveView;
                case 1:
                default:
                    return powerView;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int count = 0;
            switch (position) {
                case 0:
                    if (deck.getObjectiveDeck() != null) {
                        count = deck.getObjectiveDeck().size();
                    }
                    return getResources().getString(R.string.objective_deck_title) + " (" + count + "/12)";
                case 1:
                default:
                    if (deck.getPowerDeck() != null) {
                        count = deck.getPowerDeck().size();
                    }
                    return getResources().getString(R.string.power_deck_title) + " (" + count + ")";
            }
        }
    }
}
