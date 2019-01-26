package com.example.thinkdo.db;


import com.example.thinkdo.db.dao.BookDao;
import com.example.thinkdo.db.dao.UserDao;
import com.example.thinkdo.db.entity.Book;
import com.example.thinkdo.db.entity.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract BookDao getBookDao();

}
