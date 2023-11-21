package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.User;
import com.google.gson.Gson;


public class UpdateProfileFragment extends Fragment {

    Button btn,cancel;
    SharedPreferences myPref;
    AppDataBase database;
    TextView name,role,email;
    EditText first,last,adresse,phoneNumber;

    ImageView img, imgi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_profile, container, false);

        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();

        database = AppDataBase.getAppDatabase(getActivity());

        String userJson = myPref.getString("connectedUser", "null");
        Gson gson = new Gson();
        User connectedUser = gson.fromJson(userJson, User.class);

        first=view.findViewById(R.id.firstNameInProfile2);
        last=view.findViewById(R.id.lastNameInProfile);
        email=view.findViewById(R.id.emailInProfile);
        adresse=view.findViewById(R.id.adresseInProfile);
        name=view.findViewById(R.id.nameInProfile);
        role=view.findViewById(R.id.roleInProfile);
        phoneNumber=view.findViewById(R.id.phoneInProfile);
        btn=view.findViewById(R.id.button2);
        cancel=view.findViewById(R.id.button5);
        imgi = view.findViewById(R.id.imageViewInProfile);

        first.setText(connectedUser.getFirstName());
        last.setText(connectedUser.getLastName());
        email.setText(connectedUser.getEmail());
        adresse.setText(connectedUser.getAdress());
        name.setText(connectedUser.getFirstName()+" "+connectedUser.getLastName());
        role.setText(connectedUser.getRole());
        phoneNumber.setText(String.valueOf(connectedUser.getPhoneNumber()));

        String errorInvalidFirstName = getString(R.string.errorInvalidFirstName);
        String errorInvalidLastName = getString(R.string.errorInvalidLastName);
        String errorInvalidPhoneNumber = getString(R.string.errorInvalidPhoneNumber);


        User user = gson.fromJson(userJson, User.class);
        byte[] image = database.userDao().getImage(user.getId());
        if(image!=null){
            Bitmap imagebit= BitmapFactory.decodeByteArray(image, 0, image.length);
            imgi.setImageBitmap(imagebit);
        }

        cancel.setOnClickListener(e->{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .commit();
        });

        btn.setOnClickListener(e -> {

            first.setError(null);
            last.setError(null);
            phoneNumber.setError(null);
            email.setError(null);
            boolean hasError = false;

            if (TextUtils.isEmpty(first.getText()) || first.getText().length() < 3 || containsDigit(first.getText().toString())) {
                first.setError(errorInvalidFirstName);
                hasError = true;
            }

            if (TextUtils.isEmpty(last.getText()) || last.getText().length() < 3 || containsDigit(last.getText().toString())) {
                last.setError(errorInvalidLastName);
                hasError = true;
            }

            if (TextUtils.isEmpty(phoneNumber.getText()) || phoneNumber.getText().length() != 8 || !TextUtils.isDigitsOnly(phoneNumber.getText())) {
                phoneNumber.setError(errorInvalidPhoneNumber);
                hasError = true;
            }

            if (!hasError) {

                database.userDao().updateUserDetails(user.getId(), adresse.getText().toString()
                        , first.getText().toString(), last.getText().toString(),
                        Integer.valueOf(phoneNumber.getText().toString()));

                User userAfterUpdate = database.userDao().getUserByEmail(email.getText().toString()).get();

                String userJson2 = gson.toJson(userAfterUpdate);
                editor.putString("connectedUser", userJson2);
                editor.commit();

                Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .commit();
            }
        });

        return view;
    }

    private boolean containsDigit(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}