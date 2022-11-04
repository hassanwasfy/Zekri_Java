package com.hassanwasfy.zekri.DataBase.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hassanwasfy.zekri.DataBase.Model.ZekrModel;
import com.hassanwasfy.zekri.DataBase.viewModel.DBViewModel;
import com.hassanwasfy.zekri.R;
import com.hassanwasfy.zekri.databinding.CustomZekrLayoutBinding;
import java.util.List;

public class ZekrModelAdapter extends RecyclerView.Adapter<ZekrModelAdapter.ZekrHolder> {


    private List<ZekrModel> zekrModels;
    private final DBViewModel modelView;
    private final OnZekrClick onZekrClick;

    public ZekrModelAdapter(List<ZekrModel> zekrModels, DBViewModel modelView,Context context) {
        this.zekrModels = zekrModels;
        this.modelView = modelView;
        if (context instanceof OnZekrClick){
            this.onZekrClick = (OnZekrClick) context;
        }else {
            throw new ClassCastException("Interface {OnZekrClick} hasn't been implemented");
        }
    }

    public List<ZekrModel> getZekrModels() {
        return zekrModels;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setZekrModels(List<ZekrModel> zekrModels) {
        this.zekrModels = zekrModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ZekrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ZekrHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_zekr_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ZekrHolder holder, int position) {
        ZekrModel zekrModel = zekrModels.get(position);
        holder.bind(zekrModel,position);
    }

    @Override
    public int getItemCount() {
        if (getZekrModels() != null)
            return getZekrModels().size();
        return 0;
    }

    public class ZekrHolder extends RecyclerView.ViewHolder{
        private final CustomZekrLayoutBinding binding;
        private int position;
        private ZekrModel zekrModel;
        public ZekrHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomZekrLayoutBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onZekrClick.onZekrClicked(zekrModel);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(ZekrModel zekrModel,int position){
            this.position = position;
            this.zekrModel = zekrModel;
            String fac = zekrModel.getZekrName();
            binding.zekrIdAndName.setText(fac);
            binding.zekrProcess.setText(zekrModel.getZekrPercent() + "%");
        }

    }

    public interface OnZekrClick{
        void onZekrClicked(ZekrModel zekrModel);
    }
}
