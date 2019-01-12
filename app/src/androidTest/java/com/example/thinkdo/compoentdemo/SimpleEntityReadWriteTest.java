package com.example.thinkdo.compoentdemo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.thinkdo.db.AppDatabase;
import com.example.thinkdo.db.dao.UserDao;
import com.example.thinkdo.db.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    private UserDao mUserDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserDao = mDb.getUserDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = new User();

        user.setFirstName("谢");
        user.setLastName("华");

        mUserDao.insertUsers(user);
        List<User> byName = mUserDao.queryUserByFirstName("谢");
        assertThat(byName.get(0), equalTo(user));
    }

    public void matrixTest(){

    }

}
