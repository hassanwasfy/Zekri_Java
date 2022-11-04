package com.hassanwasfy.zekri.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.Repository.Repository;
import com.hassanwasfy.zekri.R;
import com.hassanwasfy.zekri.databinding.ActivityAddZekrScreenBinding;

public class AddZekrScreen extends AppCompatActivity {

    private ActivityAddZekrScreenBinding binding;
    private ZekrModel zekrModel;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddZekrScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(getApplication());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }


    @Override
    protected void onStart() {
        super.onStart();
        binding.btnSaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.enterNewZekrName.getText() == null || binding.enterZekrTarget.getText() == null){
                    Toast.makeText(getBaseContext(), R.string.toastData, Toast.LENGTH_SHORT).show();
                }else {
                    String name = binding.enterNewZekrName.getText().toString();
                    String sTarget = binding.enterZekrTarget.getText().toString();
                    int target = Integer.parseInt(sTarget);
                    zekrModel = new ZekrModel(name,target,0,0);
                    repository.addZekr(zekrModel);
                    Toast.makeText(getBaseContext(), R.string.toastAdded,Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}