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

import com.example.centre_formation.entity.Formation;
import com.example.centre_formation.fragment.DetailsFormation;

import java.io.Serializable;
import java.util.List;

public class FormationAdapter extends ArrayAdapter<Formation> {


    private Context context;

    public FormationAdapter(@NonNull Context context, int resource, @NonNull List<Formation> formationList) {
        super(context, resource, formationList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.liste_item_formation, parent, false);
        }

        Formation currentFormation = getItem(position);
        TextView title = listItem.findViewById(R.id.textViewFormationTitle);
        title.setText(currentFormation.getTitre());

        Button detailsButton = listItem.findViewById(R.id.buttonDetails);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsFragment(currentFormation);
            }
        });

        return listItem;
    }

    private void openDetailsFragment(Formation formation) {
        DetailsFormation detailFragment = new DetailsFormation();
        Bundle bundle = new Bundle();
        bundle.putSerializable("formation", (Serializable) formation); // Assurez-vous que Cours est Serializable
        detailFragment.setArguments(bundle);

        AppCompatActivity activity = (AppCompatActivity) context;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
