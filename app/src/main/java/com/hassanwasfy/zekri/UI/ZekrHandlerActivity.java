package com.hassanwasfy.zekri.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.viewModel.DBViewModel;
import com.hassanwasfy.zekri.R;
import com.hassanwasfy.zekri.databinding.ActivityZekrHandlerBinding;

public class ZekrHandlerActivity extends AppCompatActivity {

    public static final String HANDLE_PAGE_CODE = "hpc";
    private Intent editIntent;
    private ActivityResultLauncher<Intent> toEditLauncher;
    private Intent intent;
    private ActivityZekrHandlerBinding binding;
    private ZekrModel zekrModel;
    private DBViewModel dbViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZekrHandlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbViewModel = new DBViewModel(getApplication());
        zekrModel = (ZekrModel) intent.getSerializableExtra(HANDLE_PAGE_CODE);
        loadComponents(zekrModel);
        binding.btnZekrClicker.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(zekrModel.getZekrProcess() < zekrModel.getZekrTarget()){
                    zekrModel.setZekrProcess(zekrModel.getZekrProcess()+1);
                    binding.showZekrProcess.setText(String.valueOf(zekrModel.getZekrProcess()));
                    binding.showZekrPercent.setText(zekrModel.getZekrPercent() + "%");
                }else {
                    Toast.makeText(ZekrHandlerActivity.this, R.string.targetAchieved, Toast.LENGTH_SHORT).show();
                }
            }
        });


        toEditLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (editIntent != null){
                            if (result.getResultCode() == ZekrManagerActivity.RESULT_SAVE){
                                Toast.makeText(ZekrHandlerActivity.this, R.string.edit_saved, Toast.LENGTH_SHORT).show();
                                Intent i = getIntent();
                                ZekrModel z = (ZekrModel) i.getSerializableExtra(ZekrManagerActivity.OUTGOING_MODEL);
                                loadComponents(z);
                            }else if (result.getResultCode() == ZekrManagerActivity.RESULT_CANCELED){
                                Toast.makeText(ZekrHandlerActivity.this, R.string.canceled, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("onCreate","Done onCreateOption");
        getMenuInflater().inflate(R.menu.managment_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("OnSelect","before if");
        if (item.getItemId() == R.id.menu_edit_zekr) {
            Log.d("onSelect","inIf");
            editIntent = new Intent(getBaseContext(), ZekrManagerActivity.class);
            editIntent.putExtra(ZekrManagerActivity.INCOMING_MODEL,zekrModel);
            toEditLauncher.launch(editIntent);
            return true;
        }
        else if (item.getItemId() == R.id.menu_reset_zekr){
            zekrModel.setZekrProcess(0);
            zekrModel.setZekrPercent(0);
            binding.showZekrProcess.setText(String.valueOf(zekrModel.getZekrProcess()));
            binding.showZekrPercent.setText(String.valueOf(zekrModel.getZekrPercent()));
            dbViewModel.updateZekr(zekrModel);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbViewModel.updateZekr(zekrModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        zekrModel = null;
        dbViewModel = null;
        finish();
    }



    private void loadComponents(ZekrModel zekrModel){
        binding.showZekrName.setText(zekrModel.getZekrName());
        binding.showZekrProcess.setText(String.valueOf(zekrModel.getZekrProcess()));
        binding.showZekrTarget.setText(String.valueOf(zekrModel.getZekrTarget()));
        binding.showZekrPercent.setText(String.valueOf(zekrModel.getZekrPercent()));
    }
}