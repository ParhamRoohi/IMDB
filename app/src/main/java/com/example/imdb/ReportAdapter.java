package com.example.imdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.ReportAdapter.ViewHolder;
import com.example.imdb.databinding.ItemReportBinding;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Report> reportList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ReportAdapter(List<Report> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReportBinding binding = ItemReportBinding.inflate(layoutInflater, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
    public void updateReports(List<Report> newReports) {
        this.reportList.clear();
        this.reportList.addAll(newReports);
        notifyDataSetChanged();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private ItemReportBinding binding;

        public ViewHoler(@NonNull ItemReportBinding binding) {
            super(binding.getRoot());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReportBinding binding;
        private int position;

        public ViewHolder(ItemReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(int position) {
            this.position = position;
            Report report = reportList.get(position);
            binding.reportContent.setText(report.getContent());
            binding.reportTitle.setText(report.getTitle());

        }
    }

}
