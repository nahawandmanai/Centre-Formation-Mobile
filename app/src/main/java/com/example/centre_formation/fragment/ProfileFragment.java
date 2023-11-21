package com.example.centre_formation.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.User;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ProfileFragment extends Fragment {
    SharedPreferences myPref;
    AppDataBase database;
    TextView name, role, email, first, last, adresse, phoneNumber;

    Button btnPassword, btnUpdateProfile, btnUpdateImage;
    public static final int PICK_IMAGE_REQUEST = 1;
    ByteArrayOutputStream arrayOutputStream;
    Uri imagePath;
    Bitmap imageToStore;

    ImageView img, imgi;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();

        database = AppDataBase.getAppDatabase(getActivity());

        String userJson = myPref.getString("connectedUser", "null");
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        first = view.findViewById(R.id.firstNameInProfile2);
        last = view.findViewById(R.id.lastNameInProfile);
        email = view.findViewById(R.id.emailInProfile);
        adresse = view.findViewById(R.id.adresseInProfile);
        name = view.findViewById(R.id.nameInProfile);
        role = view.findViewById(R.id.roleInProfile);
        phoneNumber = view.findViewById(R.id.phoneInProfile);
        btnPassword = view.findViewById(R.id.button2);
        btnUpdateImage = view.findViewById(R.id.button5);
        btnUpdateProfile = view.findViewById(R.id.button4);
        imgi = view.findViewById(R.id.imgInProfile);

        first.setText(user.getFirstName());
        last.setText(user.getLastName());
        email.setText(user.getEmail());
        adresse.setText(user.getAdress());
        name.setText(user.getFirstName() + " " + user.getLastName());
        role.setText(user.getRole());
        phoneNumber.setText(String.valueOf(user.getPhoneNumber()));


        byte[] image = database.userDao().getImage(user.getId());
        if(image!=null){
            Bitmap imagebit=BitmapFactory.decodeByteArray(image, 0, image.length);
            imgi.setImageBitmap(imagebit);
        }


        btnPassword.setOnClickListener(e ->

    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new UpdatePasswordFragment())
                .commit();
    });
        btnUpdateProfile.setOnClickListener(e ->

    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new UpdateProfileFragment())
                .commit();
    });


        btnUpdateImage.setOnClickListener(v ->

    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    });


        return view;
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {

            try {
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath);
                img = getView().findViewById(R.id.imgInProfile);
                img.setImageBitmap(imageToStore);

                arrayOutputStream = new ByteArrayOutputStream();
                imageToStore.compress(Bitmap.CompressFormat.JPEG, 40, arrayOutputStream);
                byte[] imageInByte = arrayOutputStream.toByteArray();

                myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
                String userJson = myPref.getString("connectedUser", "null");
                Gson gson = new Gson();
                User user = gson.fromJson(userJson, User.class);

                database.userDao().updateUserWithImage(user.getId(), imageInByte);
                user = database.userDao().getUserByEmail(email.getText().toString()).get();

                String userJson2 = gson.toJson(user);
                SharedPreferences.Editor editor = myPref.edit();
                editor.putString("connectedUser", userJson2);
                editor.commit();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}