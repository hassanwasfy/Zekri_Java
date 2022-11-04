package com.hassanwasfy.zekri.DataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;

import java.util.List;

@Dao
public interface ZekrModelDao {
    @Insert
    void insertZekr(ZekrModel zekrModel);
    @Update
    void updateZekr(ZekrModel zekrModel);
    @Delete
    void deleteZekr(ZekrModel zekrModel);
    @Query("SELECT * FROM zekrmodel WHERE zekrId =:id")
    ZekrModel getZekr(int id);
    @Query("SELECT * FROM zekrmodel")
    LiveData<List<ZekrModel>> getAllZekr();
}
