package com.wanderingbme.warhammerunderworldsdeckbuilder.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by JT on 3/20/2018.
 */
@Entity//( foreignKeys = @ForeignKey(entity = Warband.class, parentColumns = "Id", childColumns = "WarbandId"))
public class Card implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "Id")
    int id;

    @ColumnInfo(name = "Title")
    String title;

    @ColumnInfo(name = "WarbandId")
    int warbandId;

    @ColumnInfo(name = "Type")
    String type;

    @ColumnInfo(name = "Image")
    String image;

    @ColumnInfo(name = "Expansion")
    String expansion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWarbandId() {
        return warbandId;
    }

    public void setWarbandId(int warbandId) {
        this.warbandId = warbandId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    @Override
    public boolean equals(Object otherCard) {
        if (otherCard instanceof Card) {
            return this.id == ((Card) otherCard).getId();
        }
        return false;
    }
}
