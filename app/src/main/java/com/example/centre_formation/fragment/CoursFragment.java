package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Cours;
import com.example.centre_formation.entity.User;
import com.google.android.material.button.MaterialButton;


public class CoursFragment extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    MaterialButton add;

    TextView titre,contenu;
    Spinner matiereSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cours, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        database = AppDataBase.getAppDatabase(getActivity());
        titre = view.findViewById(R.id.titre);
        contenu = view.findViewById(R.id.contenu);
        matiereSpinner = view.findViewById(R.id.matiere);

        add = view.findViewById(R.id.btnCourse);
        ArrayAdapter<Cours.Matiere> matiereAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                Cours.Matiere.values()
        );

        matiereAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        matiereSpinner.setAdapter(matiereAdapter);

        add.setOnClickListener(e -> {
            Cours.Matiere matiereEnum = (Cours.Matiere) matiereSpinner.getSelectedItem();
            Cours cours = new Cours(
                    titre.getText().toString(),
                    contenu.getText().toString(),
                    matiereEnum
            );

            database.coursDao().addCours(cours);

            Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_LONG).show();
        });


        return view ;
    }
}
