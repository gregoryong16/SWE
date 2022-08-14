package com.example.cz2006.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cz2006.R;
import com.example.cz2006.classes.Versions;

import java.util.List;

public class VersionsAdapter extends RecyclerView.Adapter<VersionsAdapter.VersionVH> {
    List<Versions> versionsList;

    public VersionsAdapter(List<Versions> versionsList) {
        this.versionsList = versionsList;
    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows_cardtips, parent, false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {

        Versions versions = versionsList.get(position);
        holder.applianceTxt.setText(versions.getApplianceName());
        holder.costTxt.setText(versions.getCost());
        holder.amountTxt.setText(versions.getAmountUsed());
        holder.tipsTxt.setText(versions.getTips());

        if(position == 0)
            holder.linearLayout.setBackgroundResource(R.color.lightBlue);
        else if(position == 1)
            holder.linearLayout.setBackgroundResource(R.color.purple);
        else if(position == 2)
            holder.linearLayout.setBackgroundResource(R.color.teal_700);
        else if(position == 3)
            holder.linearLayout.setBackgroundResource(R.color.gray);
        else if(position == 4)
            holder.linearLayout.setBackgroundResource(R.color.orange);

        boolean isExpandable = versionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return versionsList.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        TextView applianceTxt, costTxt, amountTxt, tipsTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            applianceTxt = itemView.findViewById(R.id.appliance);
            costTxt = itemView.findViewById(R.id.cost);
            amountTxt = itemView.findViewById(R.id.amount);
            tipsTxt = itemView.findViewById(R.id.tips);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Versions versions = versionsList.get(getAdapterPosition());
                    versions.setExpandable(!versions.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
