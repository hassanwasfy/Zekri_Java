package com.hassanwasfy.zekri.DataBase.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.Repository.Repository;
import com.hassanwasfy.zekri.DataBase.dbManage.DataBase;

import java.util.List;
import java.util.Objects;

public class DBViewModel extends AndroidViewModel {

    private final Repository repository;
    private final LiveData<List<ZekrModel>> allZekrModel;



    public DBViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.allZekrModel = repository.getAllZekrModel();
    }

    public LiveData<List<ZekrModel>> getAllZekrModel() {
        return allZekrModel;
    }

    public void insertZekr(ZekrModel zekrModel){
        repository.addZekr(zekrModel);
    }

    public void deleteZekr(ZekrModel zekrModel){
        repository.deleteZekr(zekrModel);
    }

    public void updateZekr(ZekrModel zekrModel){
        repository.updateZekr(zekrModel);
    }

    public ZekrModel getZekr(int id){
        return repository.getZekr(id);
    }
}
