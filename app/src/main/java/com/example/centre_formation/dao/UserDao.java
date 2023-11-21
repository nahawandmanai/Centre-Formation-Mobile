package com.example.centre_formation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.centre_formation.entity.User;

import java.util.List;
import java.util.Optional;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);
    @Delete
    void deleteUser(User user);
    @Query("SELECT * FROM user_table")
    List<User> getAllUser();
    @Query("SELECT * FROM user_table WHERE email = :email")
    Optional<User> getUserByEmail(String email);
    @Update
    void updateUser(User user);

    @Query("UPDATE user_table SET image = :image WHERE id = :userId")
    void updateUserWithImage(int userId, byte[] image);

    @Query("SELECT image FROM user_table WHERE id = :userId")
    byte[] getImage(int userId);

    @Query("UPDATE user_table SET adress = :newAdress, firstName = :newFirstName, lastName = :newLastName, phoneNumber = :newPhoneNumber WHERE id = :userId")
    void updateUserDetails(int userId, String newAdress, String newFirstName, String newLastName, int newPhoneNumber);

    @Query("UPDATE user_table SET password = :newPassword WHERE id = :userId")
    void updateUserPassword(int userId, String newPassword);

}
