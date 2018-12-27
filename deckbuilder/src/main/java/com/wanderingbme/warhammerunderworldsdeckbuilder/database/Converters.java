package com.wanderingbme.warhammerunderworldsdeckbuilder.database;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by JT on 3/4/2018.
 */

public class Converters {

    @TypeConverter
    public boolean fromInt(Integer value) {
        return value == 1;
    }

    @TypeConverter
    public Integer releasedToInt(boolean value) {
        return value ? 1 : 0;
    }
}
