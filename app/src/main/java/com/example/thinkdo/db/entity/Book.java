package com.example.thinkdo.db.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("userId")}, foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId"))
public class Book {

    @PrimaryKey(autoGenerate = true)
    public long bookId;

    public String title;

    public int userId;
}
