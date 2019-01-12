package com.example.thinkdo.db.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.thinkdo.db.entity.Book;

import java.util.List;

public interface BookDao {
    @Insert
    List<Long> insertBooks(Book... books);

    @Delete
    List<Long> deleteBooks(Book... books);

    @Update
    List<Long> updateBooks(Book... books);

    @Query("SELECT * FROM book")
    List<Book> loadAllBooks();

    @Query("SELECT * FROM book WHERE userId = \':userId\'")
    List<Book> queryBooksByUserId(String userId);
}
