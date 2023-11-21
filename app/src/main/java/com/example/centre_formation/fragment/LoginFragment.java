package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.PasswordUtils;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.User;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    EditText email, password;
    TextView register;
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();

        database = AppDataBase.getAppDatabase(getActivity());

        email = view.findViewById(R.id.emailInLoginFrag);
        password = view.findViewById(R.id.passwordInLoginFrag);
        btnLogin = view.findViewById(R.id.btnloginInLoginFrag);
        register = view.findViewById(R.id.goToRegisterInLoginFrag);

        btnLogin.setOnClickListener(e -> {
            if (database.userDao().getUserByEmail(email.getText().toString()).isPresent()) {
                User user = database.userDao().getUserByEmail(email.getText().toString()).get();
                if (database.userDao().getUserByEmail(email.getText().toString()).isPresent() && PasswordUtils.checkPassword(password.getText().toString(), user.getPassword())) {
                    Gson gson = new Gson();
                    String userJson = gson.toJson(user);

                    editor.putString("connectedUser", userJson);
                    editor.commit();

                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            } else
                Toast.makeText(getActivity(), "Wrong credentials", Toast.LENGTH_LONG).show();
        } );


        register.setOnClickListener(e -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameInPortail, new RegistrationFragment())
                    .commit();
        });

        return view;
    }
}