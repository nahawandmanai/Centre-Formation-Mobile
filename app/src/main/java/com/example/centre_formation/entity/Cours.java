package com.example.centre_formation.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cours_table")

public class Cours implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "titre")
    private String titre;
    @ColumnInfo(name = "contenu")
    private String contenu;
    @ColumnInfo(name = "matiere")
    private Matiere matiere;

    // Constructeurs, getters, setters, etc.
    public Cours() {
    }

    // Constructeur avec tous les champs
    public Cours(int id, String titre, String contenu, Matiere matiere) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.matiere = matiere;
    }
    public Cours(String titre, String contenu, Matiere matiere) {
        this.titre = titre;
        this.contenu = contenu;
        this.matiere = matiere;
    }

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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public enum Matiere {
        Math,
        Francais,
        Geo,
        Histoire
    }
    @Override
    public String toString() {
        return "Titre: " + titre + "\nContenu: " + contenu + "\nMatière: " + matiere.toString();
    }

}
