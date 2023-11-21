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

import com.example.centre_formation.entity.Cours;
import com.example.centre_formation.fragment.DetailCours;

import java.io.Serializable;
import java.util.List;

public class CoursListAdapter extends ArrayAdapter<Cours> {

    private Context context;

    public CoursListAdapter(@NonNull Context context, int resource, @NonNull List<Cours> coursList) {
        super(context, resource, coursList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.liste_item_cours, parent, false);
        }

        Cours currentCours = getItem(position);
        TextView title = listItem.findViewById(R.id.textViewCourseTitle);
        title.setText(currentCours.getTitre());

        Button detailsButton = listItem.findViewById(R.id.buttonDetails);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsFragment(currentCours);
            }
        });

        return listItem;
    }

    private void openDetailsFragment(Cours cours) {
        DetailCours detailFragment = new DetailCours();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cours", (Serializable) cours); // Assurez-vous que Cours est Serializable
        detailFragment.setArguments(bundle);

        AppCompatActivity activity = (AppCompatActivity) context;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
