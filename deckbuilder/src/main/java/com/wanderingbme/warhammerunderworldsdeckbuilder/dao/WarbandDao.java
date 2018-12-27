package com.wanderingbme.warhammerunderworldsdeckbuilder.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Warband;

import java.util.List;

/**
 * Created by JT on 2/27/2018.
 */
@Dao
public interface WarbandDao {
    @Query("select * from Warband")
    List<Warband> getAll();

    @Query("select * from Warband where Id = :id")
    Warband getWarbandById(int id);

    @Query("select * from Warband where Name = :name")
    Warband getWarbandByName(String name);
}
