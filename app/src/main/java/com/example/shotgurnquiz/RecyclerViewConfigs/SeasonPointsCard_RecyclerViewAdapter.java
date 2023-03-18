package com.example.shotgurnquiz.RecyclerViewConfigs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shotgurnquiz.Database.Tables.Quiz;
import com.example.shotgurnquiz.Models.SeasonPoints;
import com.example.shotgurnquiz.R;

import java.util.ArrayList;

public class SeasonPointsCard_RecyclerViewAdapter extends RecyclerView.Adapter<SeasonPointsCard_RecyclerViewAdapter.MyViewHolder> {

    public SeasonPointsCard_RecyclerViewAdapter(Context context, ArrayList<SeasonPoints> seasonPointsCards){
        this.context = context;
        this.seasonPointsCards = seasonPointsCards;
    }

    @NonNull
    @Override
    public SeasonPointsCard_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_season_points_card, parent, false);
        return new SeasonPointsCard_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonPointsCard_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewUsername.setText(seasonPointsCards.get(position).getUsername());
        holder.textViewPointsCount.setText(Integer.toString(seasonPointsCards.get(position).getPoints()));
        holder.textViewRank.setText("#" + Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return seasonPointsCards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewUsername;
        TextView textViewPointsCount;
        TextView textViewRank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.recycler_view_season_points_username);
            textViewPointsCount = itemView.findViewById(R.id.recycler_view_season_points_points_count);
            textViewRank = itemView.findViewById(R.id.recycler_view_season_points_rank);
        }
    }
    private Context context;
    private ArrayList<SeasonPoints> seasonPointsCards;
}
