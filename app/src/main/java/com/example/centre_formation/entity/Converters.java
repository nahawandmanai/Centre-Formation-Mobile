package com.example.centre_formation.entity;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {
    private static final String DATE_FORMAT = "yyyy-MM-dd"; // Adjust the format based on your requirements

    @TypeConverter
    public static Cours.Matiere fromString(String value) {
        return value == null ? null : Cours.Matiere.valueOf(value);
    }

    @TypeConverter
    public static String matiereToString(Cours.Matiere matiere) {
        return matiere == null ? null : matiere.name();
    }
    @TypeConverter
    public static Date toDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            return sdf.format(date);
        }
        return null;
    }
}
