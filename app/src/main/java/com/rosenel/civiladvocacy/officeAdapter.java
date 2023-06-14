package com.rosenel.civiladvocacy;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class officeAdapter extends RecyclerView.Adapter<officeView> {

    private final com.rosenel.civiladvocacy.mainActivity mainActivity;
    private final ArrayList<official> officialArrayList;

    public officeAdapter(com.rosenel.civiladvocacy.mainActivity mainActivity, ArrayList<official> officialArrayList) {
        this.mainActivity = mainActivity;
        this.officialArrayList = officialArrayList;
    }

    @NonNull
    @Override
    public officeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.office_entry, parent, false);
        itemView.setOnClickListener(mainActivity);
        return new officeView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull officeView holder, int position) {
        official official = officialArrayList.get(position);
        holder.officeName.setText(official.getOfficeName());
        holder.officialName.setText(official.getName());
        holder.party.setText("(" + official.getParty() + ")");
        if (official.getPhotoURL() != null) {

            Glide.with(mainActivity)
                    .load(official.getPhotoURL())
                    //.load("https://cdn.britannica.com/33/194733-050-4CF75F31/Girl-with-a-Pearl-Earring-canvas-Johannes-1665.jpg")
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .error(R.drawable.brokenimage)
                    .into(holder.image);

        } else {
            holder.image.setImageResource(R.drawable.missing);
        }
    }


    @Override
    public int getItemCount() {
        return officialArrayList.size();
    }
}
