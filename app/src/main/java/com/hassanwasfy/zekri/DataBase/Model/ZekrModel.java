package com.hassanwasfy.zekri.DataBase.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.io.Serializable;

@Entity
public class ZekrModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int zekrId;
    @NonNull
    private String zekrName;
    @ColumnInfo(defaultValue = "100")
    private int zekrTarget;
    @ColumnInfo(defaultValue = "0")
    private int zekrProcess;
    @ColumnInfo(defaultValue = "0")
    private int zekrPercent;


    public ZekrModel(@NonNull String zekrName, int zekrTarget, int zekrProcess, int zekrPercent) {
        this.zekrName = zekrName;
        this.zekrTarget = zekrTarget;
        this.zekrProcess = zekrProcess;
        this.zekrPercent = zekrPercent;
    }

    @Ignore
    public ZekrModel(int zekrId, @NonNull String zekrName, int zekrTarget, int zekrProcess, int zekrPercent) {
        this.zekrId = zekrId;
        this.zekrName = zekrName;
        this.zekrTarget = zekrTarget;
        this.zekrProcess = zekrProcess;
        this.zekrPercent = zekrPercent;
    }

    public int getZekrId() {
        return zekrId;
    }

    public void setZekrId(int zekrId) {
        this.zekrId = zekrId;
    }

    @NonNull
    public String getZekrName() {
        return zekrName;
    }

    public void setZekrName(@NonNull String zekrName) {
        this.zekrName = zekrName;
    }

    public int getZekrTarget() {
        return zekrTarget;
    }

    public void setZekrTarget(int zekrTarget) {
        this.zekrTarget = zekrTarget;
        adjustPercent();
    }

    public int getZekrProcess() {
        return zekrProcess;
    }

    public void setZekrProcess(int zekrProcess) {
        this.zekrProcess = zekrProcess;
        adjustPercent();
    }

    public int getZekrPercent() {
        return zekrPercent;
    }

    public void setZekrPercent(int zekrPercent) {
        this.zekrPercent = zekrPercent;
    }

    private void adjustPercent(){
        int result = ((100 * getZekrProcess()) / getZekrTarget());
        setZekrPercent(result);
    }

}
