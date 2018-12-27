package com.wanderingbme.warhammerunderworldsdeckbuilder.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Card;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Warband;

import java.util.List;

/**
 * Created by JT on 3/20/2018.
 */
@Dao
public interface CardDao {

    @Query("select * from Card")
    List<Card> getAll();

    @Query("select * from Card where WarbandId = :warbandId or WarbandId = -1")
    List<Card> getCardsByWarband(int warbandId);
}
