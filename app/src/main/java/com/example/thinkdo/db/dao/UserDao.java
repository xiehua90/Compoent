package com.example.thinkdo.db.dao;


import com.example.thinkdo.db.entity.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE first_name= :firstName")
    List<User> queryUserByFirstName(String firstName);

    @Delete
    int deleteUsers(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUsers(User... users);

    @Update
    int updateUsers(User... users);
}
