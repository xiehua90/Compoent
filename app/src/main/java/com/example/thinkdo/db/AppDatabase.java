package com.example.thinkdo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.thinkdo.db.dao.BookDao;
import com.example.thinkdo.db.dao.UserDao;
import com.example.thinkdo.db.entity.Book;
import com.example.thinkdo.db.entity.User;

@Database(entities = {User.class, Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract BookDao getBookDao();

}
