package com.hassanwasfy.zekri.DataBase.dbManage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hassanwasfy.zekri.DataBase.Dao.ZekrModelDao;
import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ZekrModel.class},version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public abstract ZekrModelDao zekrModelDao();

    private static volatile DataBase dataBaseInstance;
    private final static int THREAD_NUMBER = 5;

    public static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);

    public static DataBase getInstance(final Context context){
        if (dataBaseInstance == null){
            synchronized (DataBase.class){
                if (dataBaseInstance == null){
                    dataBaseInstance
                            = Room.databaseBuilder(context,DataBase.class,"zekrDB")
                            .build();
                }
            }
        }
        return dataBaseInstance;
    }
}
