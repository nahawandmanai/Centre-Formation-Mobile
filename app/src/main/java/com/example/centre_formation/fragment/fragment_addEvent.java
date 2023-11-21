package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.dao.EventDao;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Event;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class fragment_addEvent extends Fragment {
    private EventDao eventDao;
    private EditText eventNameEditText, descriptionEditText, numberOfPeopleEditText, addressEditText;

    SharedPreferences myPref;

    AppDataBase database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
        eventDao = AppDataBase.getAppDatabase(requireContext()).eventDao();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();

        database = AppDataBase.getAppDatabase(getActivity());

        EditText eventNameEditText = view.findViewById(R.id.eventNameEditText);
        EditText descriptionEditText = view.findViewById(R.id.descriptionEditText);
        EditText numberOfPeopleEditText = view.findViewById(R.id.numberOfPeopleEditText);
        EditText addressEditText = view.findViewById(R.id.addressEditText);
        Button sendButton = view.findViewById(R.id.sendButton);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eventName = eventNameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String numberOfPeople = numberOfPeopleEditText.getText().toString();
                String address = addressEditText.getText().toString();

                Event event = new Event(eventName, description, numberOfPeople, address);

                database.eventDao().addEvent(event);


                Toast.makeText(getActivity(), "Event added successfully", Toast.LENGTH_SHORT).show();

                eventNameEditText.setText("");
                descriptionEditText.setText("");
                numberOfPeopleEditText.setText("");
                addressEditText.setText("");
            }
        });



        return view;
    }



}
