package com.example.shotgurnquiz.RecyclerViewConfigs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shotgurnquiz.Database.Tables.Quiz;
import com.example.shotgurnquiz.R;

import java.util.ArrayList;

public class QuizCard_RecyclerViewAdapter extends RecyclerView.Adapter<QuizCard_RecyclerViewAdapter.MyViewHolder> {
    public QuizCard_RecyclerViewAdapter(Context context, ArrayList<Quiz> quizCards){
        this.context = context;
        this.quizCards = quizCards;
    }

    @NonNull
    @Override
    public QuizCard_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_quiz_card, parent, false);
        return new QuizCard_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizCard_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewTitle.setText(quizCards.get(position).GetTitle());
    }

    @Override
    public int getItemCount() {
        return quizCards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.recycler_view_quiz_title);
        }
    }
    private Context context;
    private ArrayList<Quiz> quizCards;
}
