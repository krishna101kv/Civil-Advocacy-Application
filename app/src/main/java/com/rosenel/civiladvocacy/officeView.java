package com.rosenel.civiladvocacy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class officeView extends RecyclerView.ViewHolder {

    public TextView officeName;
    public TextView officialName;
    public ImageView image;
    public TextView party;

    public officeView(@NonNull View itemView) {
        super(itemView);
        officeName = itemView.findViewById(R.id.officeNameTV);
        officialName = itemView.findViewById(R.id.officialNameTV);
        party = itemView.findViewById(R.id.officialPartyTV);
        image = itemView.findViewById(R.id.officiaImageIV);

    }
}
