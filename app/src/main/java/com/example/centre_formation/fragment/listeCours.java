package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.centre_formation.CoursListAdapter;
import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Cours;

import java.io.Serializable;
import java.util.List;


public class listeCours extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    private Button addButton;
    private TextView textViewTitle;
    private Button detailsButton;

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liste_cours, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        database = AppDataBase.getAppDatabase(getActivity());
        listView = view.findViewById(R.id.listView);
        Button addButton = view.findViewById(R.id.addButton);
        if (listView == null) {
            throw new IllegalStateException("ListView not found in layout");
        }

        List<Cours> coursList = database.coursDao().getAllCours();
        CoursListAdapter adapter = new CoursListAdapter(getContext(), R.layout.liste_item_cours, coursList);
        listView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFragment();
            }
        });


        return view;
    }
    private void openAddFragment() {
        // Remplacez le fragment actuel par le fragment d'ajout
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CoursFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


}