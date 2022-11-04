package com.hassanwasfy.zekri.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hassanwasfy.zekri.DataBase.Adapter.ZekrModelAdapter;
import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.Repository.Repository;
import com.hassanwasfy.zekri.DataBase.viewModel.DBViewModel;
import com.hassanwasfy.zekri.R;
import com.hassanwasfy.zekri.databinding.ActivityStartBinding;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements ZekrModelAdapter.OnZekrClick {

    private ActivityStartBinding binding;
    private ActivityResultLauncher<Intent> toAddLauncher,toHandlePage;
    private Intent addIntent,handelIntent;
    private ZekrModelAdapter adapter;
    private Repository repository;
    private DBViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(DBViewModel.class);
        repository = new Repository(getApplication());
        adapter = new ZekrModelAdapter(repository.getAllZekrModel().getValue()
                ,viewModel
                ,this);


        binding.mainRecycler.setAdapter(adapter);
        binding.mainRecycler.setHasFixedSize(true);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getAllZekrModel().observe(this, new Observer<List<ZekrModel>>() {
            @Override
            public void onChanged(List<ZekrModel> zekrModels) {
                adapter.setZekrModels(zekrModels);
            }
        });
        Log.d("8050","ONCreateOut");
    }

    @Override
    protected void onStart() {
        super.onStart();

        toAddLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onActivityResult(ActivityResult result) {
                if (addIntent != null && result.getResultCode() == RESULT_OK){
                    adapter.notifyDataSetChanged();

                }else if (result.getResultCode() == RESULT_CANCELED){
                    viewModel.getAllZekrModel().observe(StartActivity.this, new Observer<List<ZekrModel>>() {
                        @Override
                        public void onChanged(List<ZekrModel> zekrModels) {
                            adapter.setZekrModels(zekrModels);
                        }
                    });
                }
            }
        });

        toHandlePage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.addZekrFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIntent = new Intent(getBaseContext(),AddZekrScreen.class);
                toAddLauncher.launch(addIntent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }



    @Override
    public void onZekrClicked(ZekrModel zekrModel) {
        handelIntent = new Intent(this,ZekrHandlerActivity.class);
        handelIntent.putExtra(ZekrHandlerActivity.HANDLE_PAGE_CODE,zekrModel);
        toHandlePage.launch(handelIntent);
    }
}