package com.example.appdev_buksu_it_capstone_archives;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {
    private final IntroPage[] introPages;
    private final Context context;

    public IntroAdapter(Context context, IntroPage[] introPages) {
        this.context = context;
        this.introPages = introPages;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_intro, parent, false);
        return new IntroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        try {
            IntroPage page = introPages[position];
            holder.bind(page);
            
            // Add entrance animation
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        } catch (Exception e) {
            // Handle any binding errors gracefully
            holder.bindDefault();
        }
    }

    @Override
    public int getItemCount() {
        return introPages != null ? introPages.length : 0;
    }

    static class IntroViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final ImageView buksuLogo;
        private final TextView titleText;
        private final TextView descriptionText;

        public IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.introImage);
            buksuLogo = itemView.findViewById(R.id.buksuLogo);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }

        public void bind(IntroPage page) {
            try {
                imageView.setImageResource(page.getImageResId());
            } catch (Exception e) {
                imageView.setImageResource(IntroPage.getDefaultImage());
            }

            titleText.setText(page.getTitle());
            descriptionText.setText(page.getDescription());
            
            // Add fade-in animation for text
            titleText.setAlpha(0f);
            descriptionText.setAlpha(0f);
            titleText.animate().alpha(1f).setDuration(500).start();
            descriptionText.animate().alpha(1f).setDuration(500).setStartDelay(200).start();
        }

        public void bindDefault() {
            imageView.setImageResource(IntroPage.getDefaultImage());
            titleText.setText("Welcome");
            descriptionText.setText("Loading content...");
        }
    }
} 