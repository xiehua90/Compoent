package com.example.thinkdo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId"))
public class Book {

    @PrimaryKey(autoGenerate = true)
    public long bookId;

    public String title;

    public int userId;
}
