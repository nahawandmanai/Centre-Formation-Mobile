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

import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.fragment.HomeFragment;
import com.example.centre_formation.fragment.LoginFragment;
import com.example.centre_formation.fragment.RegistrationFragment;
import com.google.android.material.navigation.NavigationView;

public class Portail extends AppCompatActivity {
    AppDataBase database;

    SharedPreferences shared;
    public static final String PREF = "pref";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portail);

        database = AppDataBase.getAppDatabase(this);

        database.userDao().getAllUser();

        shared = getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameInPortail, new HomeFragment())
                .commit();

        drawerLayout = findViewById(R.id.drawerPortail);
        navigationView = findViewById(R.id.navigationViewPortail);
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
                if (item.getItemId() == R.id.loginInMenuPortail) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameInPortail, new LoginFragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.registerInMenuPortail) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameInPortail, new RegistrationFragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (item.getItemId() == R.id.homeInMenuPortail) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameInPortail, new HomeFragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

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
}