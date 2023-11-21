package com.example.centre_formation.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.centre_formation.dao.EventDao;
import com.example.centre_formation.entity.Event;

import androidx.room.TypeConverters;

import com.example.centre_formation.dao.CoursDao;
import com.example.centre_formation.dao.FormationDao;
import com.example.centre_formation.dao.UserDao;
import com.example.centre_formation.entity.Converters;
import com.example.centre_formation.entity.Cours;
import com.example.centre_formation.entity.Formation;
import com.example.centre_formation.entity.User;

@Database(entities = {User.class, Cours.class, Event.class, Formation.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;
    public abstract UserDao userDao();
    public abstract CoursDao coursDao();
    public abstract FormationDao formationDao();
    public abstract EventDao eventDao();



    public static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "centre_formation_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
