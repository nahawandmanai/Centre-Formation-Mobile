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
import com.example.centre_formation.entity.Event;

public class UpdateEvent extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    Event events;
    Button buttonSave;
    EditText editTexteventName,editTextdescription,editTextnumberOfPeople,editTextaddress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_update_event, container, false);
        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        database = AppDataBase.getAppDatabase(getActivity());
        events = (Event) getArguments().getSerializable("event");

        editTexteventName = view.findViewById(R.id.editTexteventName);
        editTextdescription = view.findViewById(R.id.editTextdescription);
        editTextnumberOfPeople = view.findViewById(R.id.editTextnumberOfPeople);
        editTextaddress = view.findViewById(R.id.editTextaddress);

        // Populate the spinner with your enum values
        if (events != null) {
            editTexteventName.setText(events.getEventName());
            editTextdescription.setText(events.getDescription());
            editTextnumberOfPeople.setText(events.getNumberOfPeople());
            editTextaddress.setText(events.getAddress());
            // Set the selected item in the spinner
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
        events.setEventName(editTexteventName.getText().toString());
        events.setDescription(editTextdescription.getText().toString());
        events.setNumberOfPeople(editTextnumberOfPeople.getText().toString());
        events.setAddress(editTextaddress.getText().toString());

        // Sauvegardez le cours mis à jour dans la base de données dans un thread séparé
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.eventDao().updateEvent(events); // Votre méthode DAO pour mettre à jour le cours

                // Revenez au thread principal pour les actions UI après la mise à jour
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Par exemple, affichez un Toast ou revenez à l'écran précédent
                        Toast.makeText(getContext(), "Event mis à jour", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        }).start();

    }
}