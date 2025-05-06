package com.example.appdev_buksu_it_capstone_archives;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CapstoneAdapter extends RecyclerView.Adapter<CapstoneAdapter.ViewHolder> {

    private List<CapstoneModel> capstoneList;

    public CapstoneAdapter(List<CapstoneModel> capstoneList) {
        this.capstoneList = capstoneList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_capstone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CapstoneModel model = capstoneList.get(position);
        holder.tvTitle.setText(model.getTitle());
        holder.tvAuthors.setText(model.getAuthors());
        holder.tvCategory.setText(model.getCategory());
    }

    @Override
    public int getItemCount() {
        return capstoneList.size();
    }

    public void updateList(List<CapstoneModel> newList) {
        capstoneList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthors, tvCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthors);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}
