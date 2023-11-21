package com.example.centre_formation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.centre_formation.entity.Formation;

import java.util.List;

@Dao
public interface FormationDao {

    @Insert
    void addFormation(Formation formation);
    @Delete
    void deleteFormation(Formation formation);
    @Query("SELECT * FROM formation_table")
    List<Formation> getAllFormation();

    @Update
    void updateFormation(Formation formation);

}
