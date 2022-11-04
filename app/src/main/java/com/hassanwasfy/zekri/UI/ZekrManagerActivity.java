package com.hassanwasfy.zekri.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hassanwasfy.zekri.DataBase.Adapter.ZekrModelAdapter;
import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.viewModel.DBViewModel;
import com.hassanwasfy.zekri.R;
import com.hassanwasfy.zekri.databinding.ActivityZekrManagerBinding;

import java.util.List;

public class ZekrManagerActivity extends AppCompatActivity {

    public static final int RESULT_SAVE = 11;
    public static final int RESULT_DELETE = 12;
    public static final String OUTGOING_MODEL = "outgoing_Model";
    public static final String INCOMING_MODEL = "incoming_Model";
    private ActivityZekrManagerBinding binding;
    private DBViewModel viewModel;
    private Intent intent;
    private ZekrModel zekrModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZekrManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        zekrModel = (ZekrModel) intent.getSerializableExtra(INCOMING_MODEL);
        binding.btnSaveZekr.setVisibility(View.INVISIBLE);
        binding.updateZekrName.setEnabled(false);
        binding.updateZekrProcess.setEnabled(false);
        binding.updateZekrTarget.setEnabled(false);
        viewModel = new DBViewModel(getApplication());
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.updateZekrName.setText(zekrModel.getZekrName());
        binding.updateZekrTarget.setText(String.valueOf(zekrModel.getZekrTarget()));
        binding.updateZekrProcess.setText(String.valueOf(zekrModel.getZekrProcess()));

        binding.btnEditZekr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.updateZekrName.setEnabled(true);
                binding.updateZekrProcess.setEnabled(true);
                binding.updateZekrTarget.setEnabled(true);
                binding.btnEditZekr.setVisibility(View.GONE);
                binding.btnDeleteZekr.setVisibility(View.GONE);
                binding.btnSaveZekr.setVisibility(View.VISIBLE);
            }
        });

        binding.btnDeleteZekr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteZekr(zekrModel);
                Toast.makeText(ZekrManagerActivity.this, R.string.deleteToast, Toast.LENGTH_SHORT).show();
                setResult(RESULT_DELETE);
                finish();
            }
        });

        binding.btnSaveZekr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.updateZekrName.getText() != null
                        && binding.updateZekrTarget.getText() != null
                        && binding.updateZekrProcess.getText() != null ){
                    String name = binding.updateZekrName.getText().toString();
                    String sTarget = binding.updateZekrTarget.getText().toString();
                    String sProcess = binding.updateZekrProcess.getText().toString();
                    int target = Integer.parseInt(sTarget);
                    int process = Integer.parseInt(sProcess);

                    zekrModel.setZekrName(name);
                    zekrModel.setZekrTarget(target);
                    zekrModel.setZekrProcess(process);

                    viewModel.updateZekr(zekrModel);
                    Toast.makeText(ZekrManagerActivity.this, R.string.edit_saved, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra(OUTGOING_MODEL,zekrModel);
                    setResult(RESULT_SAVE,i);
                    finish();
                }else {
                    Toast.makeText(ZekrManagerActivity.this, R.string.fillData, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}