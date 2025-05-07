package com.example.appdev_buksu_it_capstone_archives;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CapstoneAdapter extends RecyclerView.Adapter<CapstoneAdapter.ViewHolder> {

    private List<CapstoneModel> capstoneList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CapstoneModel capstone);
    }

    public CapstoneAdapter(Context context, List<CapstoneModel> capstoneList, OnItemClickListener listener) {
        this.context = context;
        this.capstoneList = capstoneList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_capstone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CapstoneModel model = capstoneList.get(position);
        
        holder.tvTitle.setText(model.getTitle());
        holder.tvAuthors.setText(model.getAuthors());
        holder.tvCategory.setText(model.getCategory());

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(model);
            }
        });

        // Add animation
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .alpha(1f)
                .setDuration(300)
                .setStartDelay(position * 100L)
                .start();
    }

    @Override
    public int getItemCount() {
        return capstoneList != null ? capstoneList.size() : 0;
    }

    public void updateList(List<CapstoneModel> newList) {
        this.capstoneList = newList;
        notifyDataSetChanged();
    }

    public void addItem(CapstoneModel capstone) {
        capstoneList.add(capstone);
        notifyItemInserted(capstoneList.size() - 1);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < capstoneList.size()) {
            capstoneList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clearList() {
        int size = capstoneList.size();
        capstoneList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthors, tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthors);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}
