package com.wanderingbme.warhammerunderworldsdeckbuilder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by JT on 3/22/2018.
 */

public class Deck implements Serializable {

    private List<Card> objectiveDeck;
    private List<Card> powerDeck;
    private Warband warband;

    public Deck(Warband warband) {
         objectiveDeck = new ArrayList<>();
         powerDeck = new ArrayList<>();
         this.warband = warband;

    }

    public void addObjective(Card objective) {
        if (objectiveDeck.size() < 12 && !objectiveDeck.contains(objective)) {
            objectiveDeck.add(objective);
            objectiveDeck.sort(new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        } else if (objectiveDeck.size() >= 12) {
            throw new IllegalStateException("Too many cards in deck");
        } else if (objectiveDeck.contains(objective)) {
            throw new IllegalArgumentException("Card already in deck");
        }
    }

    public void addPowerCard(Card powerCard) {
        if (!powerDeck.contains(powerCard)) {
            powerDeck.add(powerCard);
            powerDeck.sort(new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        } else if (powerDeck.contains(powerCard)) {
            throw new IllegalArgumentException("Card already in deck");
        }
    }

    public void addCard(Card card) {
        if ("objective".equalsIgnoreCase(card.getType())) {
            addObjective(card);
        } else if ("upgrade".equalsIgnoreCase(card.getType()) || "ploy".equalsIgnoreCase(card.getType())) {
            addPowerCard(card);
        }
    }

    public boolean isLegal() {
        return objectiveDeck.size() == 12 && powerDeck.size() >= 20;
    }

    public List<Card> getObjectiveDeck() {
        return objectiveDeck;
    }

    public List<Card> getPowerDeck() {
        return powerDeck;
    }

    public Warband getWarband() {
        return warband;
    }

    public void setWarband(Warband warband) {
        this.warband = warband;
    }
}
