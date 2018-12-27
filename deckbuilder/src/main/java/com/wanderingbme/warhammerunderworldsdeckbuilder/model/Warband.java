package com.wanderingbme.warhammerunderworldsdeckbuilder.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.wanderingbme.warhammerunderworldsdeckbuilder.database.Converters;

import java.io.Serializable;

/**
 * Created by JT on 2/27/2018.
 */
@Entity
public class Warband implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Icon")
    private String icon;
//
    @ColumnInfo(name = "Released")
    @TypeConverters(Converters.class)
    private boolean released;

//    @Ignore
//    private List<Fighter> fighters = new ArrayList<>();

//    static final String WARBAND_TABLE_NAME = "Warband";
//    static final String WARBAND_COLUMN_ID = "Id";
//    static final String WARBAND_COLUMN_NAME = "Name";
//    static final String WARBAND_COLUMN_IMAGE_FILE = "ImageFile";
//    static final String WARBAND_COLUMN_RELEASED = "Released";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon= icon;
    }

    public boolean getReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

//    public List<Fighter> getFighters() {
//        return fighters;
//    }

//    public void addFighter(Fighter fighter) {
//        fighters.add(fighter);
//    }
}
