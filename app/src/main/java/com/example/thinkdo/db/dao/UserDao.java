package com.example.thinkdo.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.thinkdo.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE first_name=\':firstName\'")
    List<User> queryUserByFirstName(String firstName);

    @Delete
    void deleteUsers(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUsers(User... users);

    @Update
    List<Long> updateUsers(User... users);
}
