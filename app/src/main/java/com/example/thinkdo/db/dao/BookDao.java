package com.example.thinkdo.db.dao;



import com.example.thinkdo.db.entity.Book;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDao {
    @Insert
    List<Long> insertBooks(Book... books);

    @Delete
    int deleteBooks(Book... books);

    @Update
    int updateBooks(Book... books);

    @Query("SELECT * FROM book")
    List<Book> loadAllBooks();

    @Query("SELECT * FROM book WHERE userId = :userId")
    List<Book> queryBooksByUserId(String userId);
}
