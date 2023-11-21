package com.example.centre_formation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.User;
import com.example.centre_formation.fragment.ConnectedHomeFragment;
import com.example.centre_formation.fragment.EventFragment;
import com.example.centre_formation.fragment.ProfileFragment;
import com.example.centre_formation.fragment.fragment_addEvent;
import com.example.centre_formation.fragment.updateEvent_Fragment;
import com.example.centre_formation.fragment.CoursFragment;
import com.example.centre_formation.fragment.FormationFragment;

import com.example.centre_formation.fragment.ListFormation;

import com.example.centre_formation.fragment.ProfileFragment;
import com.example.centre_formation.fragment.listeCours;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private AppDataBase database;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static final String PREF = "pref";
    SharedPreferences shared;
    TextView userEmailInHeader, userNameInHeader;
    Button logout;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        database = AppDataBase.getAppDatabase(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ConnectedHomeFragment())
                .commit();

        NavigationView navigationView = findViewById(R.id.navviewInMain);
        View headerView = navigationView.getHeaderView(0);

        userNameInHeader = headerView.findViewById(R.id.userNameInHeader);
        userEmailInHeader = headerView.findViewById(R.id.userEmailInHeader);

        String userJson = shared.getString("connectedUser", "null");
        Gson gson = new Gson();
        User userJS = gson.fromJson(userJson, User.class);
        userNameInHeader.setText(userJS.getFirstName()+" "+userJS.getLastName());
        userEmailInHeader.setText(userJS.getEmail());


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navviewInMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeInMenu) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, new ConnectedHomeFragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.profileInMenu) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ProfileFragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (item.getItemId() == R.id.eventInMenu) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new EventFragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }




                if (item.getItemId() == R.id.formationInMenu) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ListFormation()).commit();


                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.coursInMenu) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new listeCours()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.logoutInMenu) {
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Portail.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
}