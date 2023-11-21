package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Formation;

public class UpdateFormation extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    private Formation formation;
    EditText editTextTitre,editTextDescription,editTextDateD,editTextDateF;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_update_formation, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        database = AppDataBase.getAppDatabase(getActivity());
        formation = (Formation) getArguments().getSerializable("formation");

        editTextTitre = view.findViewById(R.id.editTextTitre);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextDateD = view.findViewById(R.id.editTextDateD);
        editTextDateF = view.findViewById(R.id.editTextDateF);
        // ... initialisez d'autres champs ...

        if (formation != null) {
            editTextTitre.setText(formation.getTitre());
            editTextDescription.setText(formation.getDescription());
            editTextDateD.setText(formation.getDateDebut());
            editTextDateF.setText(formation.getDateFin());
            // ... mettez à jour d'autres champs ...
        }

        Button buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdatedCours();
            }
        });

        return view;
    }

    private void saveUpdatedCours() {
        // Mettez à jour l'objet cours avec de nouvelles valeurs
        formation.setTitre(editTextTitre.getText().toString());
        formation.setDescription(editTextDescription.getText().toString());
        formation.setDateDebut(editTextDateD.getText().toString());
        formation.setDateFin(editTextDateF.getText().toString());
        // ... autres mises à jour ...

        // Sauvegardez le cours mis à jour dans la base de données dans un thread séparé
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.formationDao().updateFormation(formation); // Votre méthode DAO pour mettre à jour le cours

                // Revenez au thread principal pour les actions UI après la mise à jour
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Par exemple, affichez un Toast ou revenez à l'écran précédent
                        Toast.makeText(getContext(), "Formation mis à jour", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        }).start();
    }
}