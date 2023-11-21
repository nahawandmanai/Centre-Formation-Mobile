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
import android.widget.ListView;
import android.widget.TextView;

import com.example.centre_formation.CoursListAdapter;
import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Cours;

import java.util.List;


public class DetailCours extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    private TextView textViewDetail;
    private Button buttonUpdate;
    private Button buttonDelete;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_cours, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        database = AppDataBase.getAppDatabase(getActivity());

        Cours cours = (Cours) getArguments().getSerializable("cours");
        textViewDetail = view.findViewById(R.id.textViewDetail);
        if (cours != null) {
            textViewDetail.setText(cours.toString());
        }

        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCours updateCoursFragment = new UpdateCours();

                // Passez l'objet Cours actuel au nouveau fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("cours", cours);
                updateCoursFragment.setArguments(bundle);

                // Effectuez la transaction pour afficher le nouveau fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, updateCoursFragment)
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
                        database.coursDao().deleteCours(cours); // Assurez-vous que votre DAO a une méthode delete
                    }
                }).start();

                // Retournez à l'écran précédent
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager().popBackStack();            }
        });

        return view;

    }
}