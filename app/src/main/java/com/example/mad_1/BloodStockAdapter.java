package com.example.mad_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BloodStockAdapter extends RecyclerView.Adapter<BloodStockAdapter.ViewHolder> {

    private List<BloodStockItem> bloodStockList;

    public BloodStockAdapter(List<BloodStockItem> bloodStockList) {
        this.bloodStockList = bloodStockList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blood_stock, parent, false); // Ensure this layout file exists
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodStockItem item = bloodStockList.get(position);

        // Bind the new fields along with existing fields
        holder.tvBloodType.setText("Blood Type: " + item.getBloodType());
        holder.tvQuantity.setText("Quantity: " + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return bloodStockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBloodType, tvQuantity, tvDonorName, tvContactNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBloodType = itemView.findViewById(R.id.tvBloodType);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDonorName = itemView.findViewById(R.id.tvDonorName);
            tvContactNumber = itemView.findViewById(R.id.tvContactNumber);
        }
    }
}