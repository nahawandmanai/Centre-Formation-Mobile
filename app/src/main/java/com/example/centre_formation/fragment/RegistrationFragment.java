package com.example.centre_formation.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.centre_formation.PasswordUtils;
import com.example.centre_formation.R;
import com.example.centre_formation.database.AppDataBase;
import com.example.centre_formation.entity.User;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {
    MaterialButton register;
    TextView email, firstName, lastName, password, repassword, btnLogin, phoneNumber;
    AppDataBase database;
    RadioGroup radioGroup;
    static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDataBase.getAppDatabase(getActivity());
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

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        email = view.findViewById(R.id.emailInRegistrationfrag);
        firstName = view.findViewById(R.id.firstNameInRegistrationfrag);
        lastName = view.findViewById(R.id.lastNameInRegistrationfrag);
        password = view.findViewById(R.id.passwordInRegistrationfrag);
        repassword = view.findViewById(R.id.repasswordInRegistrationfrag);
        phoneNumber = view.findViewById(R.id.phoneNumberInRegistrationfrag);
        radioGroup = view.findViewById(R.id.radioButton);

        btnLogin = view.findViewById(R.id.goToLoginInRegistrationfrag);
        btnLogin.setOnClickListener(e -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameInPortail, new LoginFragment())
                    .commit();
        });

        String errorInvalidEmail = getString(R.string.error_invalid_email);
        String errorPasswordMismatch = getString(R.string.error_password_mismatch);
        String errorInvalidFirstName = getString(R.string.errorInvalidFirstName);
        String errorInvalidLastName = getString(R.string.errorInvalidLastName);
        String errorInvalidPhoneNumber = getString(R.string.errorInvalidPhoneNumber);
        String errorInvalidPassword = getString(R.string.errorInvalidPassword);

        register = view.findViewById(R.id.btnRegisterInRegistrationfrag);
        register.setOnClickListener(e -> {

            firstName.setError(null);
            lastName.setError(null);
            phoneNumber.setError(null);
            email.setError(null);
            password.setError(null);
            repassword.setError(null);
            boolean hasError = false;

            if (TextUtils.isEmpty(firstName.getText()) || firstName.getText().length() < 3 || containsDigit(firstName.getText().toString())) {
                firstName.setError(errorInvalidFirstName);
                hasError = true;
            }

            if (TextUtils.isEmpty(lastName.getText()) || lastName.getText().length() < 3 || containsDigit(lastName.getText().toString())) {
                lastName.setError(errorInvalidLastName);
                hasError = true;
            }

            if (TextUtils.isEmpty(phoneNumber.getText()) || phoneNumber.getText().length() != 8 || !TextUtils.isDigitsOnly(phoneNumber.getText())) {
                phoneNumber.setError(errorInvalidPhoneNumber);
                hasError = true;
            }

            if (TextUtils.isEmpty(email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                email.setError(errorInvalidEmail);
                hasError = true;
            }
            if (TextUtils.isEmpty(password.getText()) || password.getText().length() < 4 || !containsUppercase(password.getText().toString()) ||
                    !containsLowercase(password.getText().toString()) || !containsDigit(password.getText().toString())) {
                password.setError(errorInvalidPassword);
                hasError = true;
            }

            if (!password.getText().toString().equals(repassword.getText().toString())) {
                repassword.setError(errorPasswordMismatch);
                hasError = true;
            }

            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (!hasError) {
                User user = new User(firstName.getText().toString(), lastName.getText().toString(), "adresse",
                        "true", "classe", email.getText().toString(), Integer.parseInt(phoneNumber.getText().toString())
                        , "STUDENT", PasswordUtils.hashPassword(password.getText().toString()),null);
                if (checkedRadioButtonId == R.id.radioButtonStudentInRegistrationfrag) {
                    user.setRole("Student");
                } else
                    user.setRole("Teacher");

                database.userDao().addUser(user);

                Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameInPortail, new LoginFragment())
                        .commit();
            }
        });

        return view;
    }
}