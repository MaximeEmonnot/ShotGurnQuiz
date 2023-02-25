package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.shotgurnquiz.Models.QuizCardModel;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.QuizCard_RecyclerViewAdapter;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_SpacesItemDecoration;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuizListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Bundle bundle = getIntent().getExtras();
        boolean bIsConnected = bundle.getBoolean("bIsConnected");

        RecyclerView recyclerViewPopular = findViewById(R.id.recycler_view_popular);
        RecyclerView recyclerViewMostRecents = findViewById(R.id.recycler_view_most_recents);
        RecyclerView recyclerViewHard = findViewById(R.id.recycler_view_hard);
        RecyclerView recyclerViewMedium = findViewById(R.id.recycler_view_medium);
        RecyclerView recyclerViewEasy = findViewById(R.id.recycler_view_easy);

        RecyclerView recyclerViewSearch = findViewById(R.id.recycler_view_search);
        SearchView searchBar = (SearchView) findViewById(R.id.search_bar);

        BottomAppBar bottomAppBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
        Button profileButton = (Button) findViewById(R.id.profile);
        Button leaderboardButton = (Button) findViewById(R.id.leader_board);
        FloatingActionButton addQuizButton = (FloatingActionButton) findViewById((R.id.add_quiz));

        if (!bIsConnected) {
            bottomAppBar.setVisibility(View.GONE);
            profileButton.setVisibility(View.GONE);
            addQuizButton.setVisibility(View.GONE);
            leaderboardButton.setVisibility(View.GONE);
        }

        ArrayList<QuizCardModel> quizCards = new ArrayList<QuizCardModel>();

        quizCards.add(new QuizCardModel("quiz1"));
        quizCards.add(new QuizCardModel("quiz2"));
        quizCards.add(new QuizCardModel("quiz3"));
        quizCards.add(new QuizCardModel("quiz4"));
        quizCards.add(new QuizCardModel("quiz5"));
        quizCards.add(new QuizCardModel("quiz6"));
        quizCards.add(new QuizCardModel("quiz7"));
        quizCards.add(new QuizCardModel("quiz8"));

        recyclerViewPopular.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopular.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

        recyclerViewMostRecents.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewMostRecents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMostRecents.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

        recyclerViewHard.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewHard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHard.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

        recyclerViewMedium.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewMedium.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMedium.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

        recyclerViewEasy.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewEasy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewEasy.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

        recyclerViewSearch.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSearch.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));


        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setIconified(false);
            }
        });

        searchBar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewSearch.setVisibility(View.VISIBLE);
                Animation showAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.recycler_view_search_show);
                recyclerViewSearch.startAnimation(showAnimation);
            }
        });

        searchBar.setOnCloseListener(new SearchView.OnCloseListener(){
            @Override
            public boolean onClose() {
                Animation showAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.recycler_view_search_hide);
                recyclerViewSearch.startAnimation(showAnimation);

                showAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        recyclerViewSearch.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                return false;
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, ProfileActivity.class);

                startActivity(intent);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, LeaderboardActivity.class);

                startActivity(intent);
            }
        });

        addQuizButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, AddQuizActivity.class);

                startActivity(intent);
            }
        });
    }
}