package com.example.centre_formation.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "formation_table")

public class Formation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titre")
    private String titre;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "dateDebut")
    private String dateDebut;

    @ColumnInfo(name = "dateFin")
    private String dateFin;

    // Default constructor
    public Formation() {
    }

    // Constructor with all fields
    public Formation(int id, String titre, String description, String dateDebut, String dateFin) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    public Formation( String titre, String description, String dateDebut, String dateFin) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
    @Override
    public String toString() {
        return "Formation" + id +

                ":titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'';
    }


}
