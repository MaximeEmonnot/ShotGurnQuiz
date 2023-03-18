package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.Score;
import com.example.shotgurnquiz.Database.Tables.User;
import com.example.shotgurnquiz.Fragments.UserRankFragment;
import com.example.shotgurnquiz.Models.SeasonPoints;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.QuizCard_RecyclerViewAdapter;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_SpacesItemDecoration;
import com.example.shotgurnquiz.RecyclerViewConfigs.SeasonPointsCard_RecyclerViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        int userId = getIntent().getExtras().getInt("userId");

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        User user = db.FindUserByID(userId);
        ArrayList<SeasonPoints> SeasonPointsList = db.GetRanking();

        RecyclerView recyclerViewSeasonPoints = (RecyclerView) findViewById(R.id.leaderboard_recycler_view_rankings);

        recyclerViewSeasonPoints.setAdapter(new SeasonPointsCard_RecyclerViewAdapter(this, SeasonPointsList));
        recyclerViewSeasonPoints.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewSeasonPoints.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.VERTICAL));


        int index;
        for(index = 0; index <  SeasonPointsList.size(); index++){
            if(SeasonPointsList.get(index).getUsername().equals(user.GetUsername())){
                break;
            }
        }



        Fragment userRankFragment;

        if(index < SeasonPointsList.size())
            userRankFragment = UserRankFragment.newInstance(user.GetUsername(), SeasonPointsList.get(index).getPoints(), "#" + Integer.toString(index + 1));
        else
            userRankFragment = UserRankFragment.newInstance(user.GetUsername(), 1, "Not ranked");


        getSupportFragmentManager().beginTransaction()
                .add(R.id.leaderboard_user_rank, userRankFragment)
                .commit();





    }
}