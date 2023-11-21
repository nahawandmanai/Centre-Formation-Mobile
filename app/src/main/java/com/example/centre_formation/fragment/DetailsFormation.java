package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.centre_formation.FormationAdapter;
import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Formation;


public class DetailsFormation extends Fragment {

    SharedPreferences myPref;

    AppDataBase database;
    private TextView textViewDetail;
    private Button buttonUpdate;
    private Button buttonDelete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_details_formation, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        database = AppDataBase.getAppDatabase(getActivity());
        Formation formation = (Formation) getArguments().getSerializable("formation");
        textViewDetail = view.findViewById(R.id.textViewDetail);
        if (formation != null) {
            textViewDetail.setText(formation.toString());
        }

        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
         buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                UpdateFormation updateFragment = new UpdateFormation();

                // Passez l'objet Cours actuel au nouveau fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("formation", formation);
                updateFragment.setArguments(bundle);

                // Effectuez la transaction pour afficher le nouveau fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, updateFragment)
                        .addToBackStack(null)
                        .commit() ;
            }
        });

        Button buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database.formationDao().deleteFormation(formation); // Assurez-vous que votre DAO a une méthode delete
                    }
                }).start();

                // Retournez à l'écran précédent
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager().popBackStack();            }
        });

        return view;

    }
}