package com.example.centre_formation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.centre_formation.MainActivity;
import com.example.centre_formation.PasswordUtils;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.User;
import com.google.gson.Gson;


public class UpdatePasswordFragment extends Fragment {

    SharedPreferences myPref;
    AppDataBase database;
    TextView name,role,email;
    EditText ancien,newpass,retype;
    Button btn,cancel;
    ImageView imgi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_password, container, false);



        myPref = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();

        database = AppDataBase.getAppDatabase(getActivity());

        String userJson = myPref.getString("connectedUser", "null");
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        email=view.findViewById(R.id.emailInUpdatePass);
        name=view.findViewById(R.id.nameInUpdatePass);
        role=view.findViewById(R.id.roleInUpdatePass);
        ancien=view.findViewById(R.id.ancientPasswordInUpdatePass);
        newpass=view.findViewById(R.id.newPasswordInUpdatePass);
        retype=view.findViewById(R.id.retypeInUpdatePass);
        btn=view.findViewById(R.id.btnUpdateInUpdatePassword);
        cancel=view.findViewById(R.id.btnCancelInupdatePassword);
        imgi = view.findViewById(R.id.imageViewInProfile);

        name.setText(user.getFirstName()+" "+user.getLastName());
        role.setText(user.getRole());
        email.setText(user.getEmail());

        String errorPasswordMismatch = getString(R.string.error_password_mismatch);
        String passwordEmpty = getString(R.string.passwordEmpty);
        String errorInvalidPassword = getString(R.string.errorInvalidPassword);
        String wrongPassword = getString(R.string.wrongpassword);


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

        btn.setOnClickListener(e->{

            retype.setError(null);
            newpass.setError(null);
            ancien.setError(null);
            boolean hasError = false;

            if (!PasswordUtils.checkPassword(ancien.getText().toString(),user.getPassword())) {
                ancien.setError(wrongPassword);
                hasError = true;
            }
            if (TextUtils.isEmpty(ancien.getText())) {
                ancien.setError(passwordEmpty);
                hasError = true;
            }
            if (TextUtils.isEmpty(newpass.getText()) || newpass.getText().length() < 4 || !containsUppercase(newpass.getText().toString()) ||
                    !containsLowercase(newpass.getText().toString()) || !containsDigit(newpass.getText().toString())) {
                newpass.setError(errorInvalidPassword);
                hasError = true;
            }
            if (!newpass.getText().toString().equals(retype.getText().toString())) {
                retype.setError(errorPasswordMismatch);
                hasError = true;
            }
            if (!hasError) {
                database.userDao().updateUserPassword(user.getId(),PasswordUtils.hashPassword(
                        newpass.getText().toString()));

                User userAfterUpdate = database.userDao().getUserByEmail(email.getText().toString()).get();

                String userJson2 = gson.toJson(userAfterUpdate);
                editor.putString("connectedUser", userJson2);
                editor.commit();

                Toast.makeText(getActivity(), "Password modified successfully", Toast.LENGTH_SHORT).show();
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

    private boolean containsUppercase(String str) {
        return !str.equals(str.toLowerCase());
    }

    private boolean containsLowercase(String str) {
        return !str.equals(str.toUpperCase());
    }

}