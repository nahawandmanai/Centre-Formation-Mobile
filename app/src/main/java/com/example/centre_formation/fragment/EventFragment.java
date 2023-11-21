package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.centre_formation.EventAdapter;
import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.dao.EventDao;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.Event;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {
    private EventDao eventDao;
    private ListView listView;
    private EventAdapter eventAdapter;
    SharedPreferences myPref;
    AppDataBase database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(requireContext());
        eventDao = AppDataBase.getAppDatabase(requireContext()).eventDao();
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_event, container, false);

            myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPref.edit();

            database = AppDataBase.getAppDatabase(getActivity());

            listView = view.findViewById(R.id.listView);

            Button addButton = view.findViewById(R.id.buttonAdd);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddFragment();
                }
            });


            List<Event> EventList = database.eventDao().getAllEvents();
            EventAdapter adapter = new EventAdapter(getContext(), R.layout.event_item_layout, EventList);
            listView.setAdapter(adapter);

            return view;
        }

    private void openAddFragment() {
        // Remplacez le fragment actuel par le fragment d'ajout
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new fragment_addEvent());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}