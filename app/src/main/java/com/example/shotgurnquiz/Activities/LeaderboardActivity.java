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

        // Récupération de l'ID utilisateur via Intent
        int userId = getIntent().getExtras().getInt("userId");

        // Instanciation du DatabaseHelper (Singleton)
        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        // Récupération des informations de l'utilisateur
        User user = db.FindUserByID(userId);

        // Récupération du Ranking global (tous les utilisateurs)
        ArrayList<SeasonPoints> SeasonPointsList = db.GetRanking();

        // Configuration du recyclerView contenant la liste des classements
        RecyclerView recyclerViewSeasonPoints = (RecyclerView) findViewById(R.id.leaderboard_recycler_view_rankings);

        recyclerViewSeasonPoints.setAdapter(new SeasonPointsCard_RecyclerViewAdapter(this, SeasonPointsList));
        recyclerViewSeasonPoints.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewSeasonPoints.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.VERTICAL));

        // On récupère l'index du Ranking Global correspondant à l'utilisateur
        int index;
        for(index = 0; index <  SeasonPointsList.size(); index++){
            if(SeasonPointsList.get(index).getUsername().equals(user.GetUsername())){
                break;
            }
        }

        // Affichage du classement de l'utilisateur dans un fragment du layout
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