package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Formation;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FormationFragment extends Fragment {
    SharedPreferences myPref;
    TextView titre, description, dateD, dateF;
    MaterialButton add;
    AppDataBase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formation, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        database = AppDataBase.getAppDatabase(getActivity());
        titre = view.findViewById(R.id.titre);
        description = view.findViewById(R.id.description);
        dateD = view.findViewById(R.id.dateD);
        dateF = view.findViewById(R.id.dateF);

        add = view.findViewById(R.id.btnFormation);


        add.setOnClickListener(e -> {
            String title = titre.getText().toString();
            String descriptionText = description.getText().toString();
            String startDateString = dateD.getText().toString();
            String endDateString = dateF.getText().toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());


            Formation formation = new Formation(title, descriptionText, startDateString, endDateString);

            database.formationDao().addFormation(formation);

            Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_LONG).show();
        });

        return view;
    }
}
