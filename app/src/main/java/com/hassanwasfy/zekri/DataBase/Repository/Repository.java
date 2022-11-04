package com.hassanwasfy.zekri.DataBase.Repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.hassanwasfy.zekri.DataBase.Dao.ZekrModelDao;
import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.dbManage.DataBase;

import java.util.List;
import java.util.Objects;

public class Repository {

    private final ZekrModelDao zekrModelDao;
    private final LiveData<List<ZekrModel>> getAllZekrModel;

    public Repository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        zekrModelDao = dataBase.zekrModelDao();
        getAllZekrModel = zekrModelDao.getAllZekr();
    }

    public LiveData<List<ZekrModel>> getAllZekrModel() {
        return getAllZekrModel;
    }

    public void addZekr(ZekrModel zekrModel){
        DataBase.executorService.execute(() ->{
            zekrModelDao.insertZekr(zekrModel);
        });
    }

    public void deleteZekr(ZekrModel zekrModel){
        DataBase.executorService.execute(() -> {
            zekrModelDao.deleteZekr(zekrModel);
        });
    }

    public void updateZekr(ZekrModel zekrModel){
        DataBase.executorService.execute(() -> {
            zekrModelDao.updateZekr(zekrModel);
        });
    }


    public ZekrModel getZekr(int id){
        return Objects.requireNonNull(getAllZekrModel.getValue()).get(id);
    }
}
