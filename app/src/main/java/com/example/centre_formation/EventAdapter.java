package com.example.centre_formation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.centre_formation.entity.Event;
import com.example.centre_formation.fragment.DetailsEvent;

import java.io.Serializable;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;

    public EventAdapter(@NonNull Context context, int resource, @NonNull List<Event> eventList) {
        super(context, resource, eventList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.liste_item_event, parent, false);
        }

        Event currentEvent = getItem(position);
        TextView eventName = listItem.findViewById(R.id.nomEvent);
        eventName.setText(currentEvent.getEventName());

        Button detailsButton = listItem.findViewById(R.id.buttonDetails);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsFragment(currentEvent);
            }
        });

        return listItem;
    }

    private void openDetailsFragment(Event events) {
        DetailsEvent detailFragment = new DetailsEvent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", events);
        detailFragment.setArguments(bundle);

        AppCompatActivity activity = (AppCompatActivity) context;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}