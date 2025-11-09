package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Module2Data;
import java.util.List;

public class Module2DataAdapter extends RecyclerView.Adapter<Module2DataAdapter.Module2DataViewHolder> {

    private List<Module2Data> module2DataList;

    public Module2DataAdapter(List<Module2Data> module2DataList) {
        this.module2DataList = module2DataList;
    }

    @NonNull
    @Override
    public Module2DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module2_data, parent, false);
        return new Module2DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Module2DataViewHolder holder, int position) {
        Module2Data data = module2DataList.get(position);
        holder.tvmethod.setText("Phương thức: " + data.getMethod());
        holder.tvuser_score.setText("Điểm đạt được: " + data.getUser_score());
        if(data.getMethod().equals("THPT")){
            holder.tvcombination.setText("Tổ hợp: " + data.getTohop());
        }
        holder.tvBenmark.setText("Điểm chuẩn: " + data.getBenmark());
        holder.tvuniversityId.setText("Mã trường: " + data.getUniversityId());
        holder.tvmajor_name.setText("Tên ngành - Tên trường: " + data.getMajor_name());
        holder.tvsuitable_rate.setText("Độ phù hợp: " + data.getSuitable_rate()+"%");
    }

    @Override
    public int getItemCount() {
        return module2DataList.size();
    }

    public void updateData(List<Module2Data> newData) {
        this.module2DataList = newData;
        notifyDataSetChanged();
    }

    static class Module2DataViewHolder extends RecyclerView.ViewHolder {
        TextView  tvBenmark,tvcombination,tvmajor_name,tvmethod,tvsuitable_rate,tvuniversityId,tvuser_score;

        public Module2DataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmethod = itemView.findViewById(R.id.tvMethod);
            tvBenmark = itemView.findViewById(R.id.tvBenmark);
            tvuser_score = itemView.findViewById(R.id.tvUserScore);

            tvuniversityId = itemView.findViewById(R.id.tvUniversityId);

            tvmajor_name = itemView.findViewById(R.id.tvMajorName);
            tvsuitable_rate = itemView.findViewById(R.id.tvSuitableRate);


        }
    }
}