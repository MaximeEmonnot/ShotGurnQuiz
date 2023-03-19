package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.Quiz;
import com.example.shotgurnquiz.Fragments.QuizInfoDialogFragment;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.QuizCard_RecyclerViewAdapter;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_ItemClickListener;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_SpacesItemDecoration;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuizListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        // Récupération de l'état connecté via Intent
        Bundle bundle = getIntent().getExtras();
        boolean bIsConnected = bundle.getBoolean("bIsConnected");

        // Références aux éléments du layout
        RecyclerView recyclerViewSearch = findViewById(R.id.recycler_view_search);
        SearchView searchBar = (SearchView) findViewById(R.id.search_bar);
        BottomAppBar bottomAppBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
        Button profileButton = (Button) findViewById(R.id.profile);
        Button leaderboardButton = (Button) findViewById(R.id.leader_board);
        FloatingActionButton addQuizButton = (FloatingActionButton) findViewById((R.id.add_quiz));

        // Selon l'état connecté, on définit l'affichage de certains éléments. De ce fait, par exemple, impossible d'accéder au profil si nous ne sommes pas connectés
        if (!bIsConnected) {
            bottomAppBar.setVisibility(View.GONE);
            profileButton.setVisibility(View.GONE);
            addQuizButton.setVisibility(View.GONE);
            leaderboardButton.setVisibility(View.GONE);
        }
        else {
            userId = bundle.getInt("userId");
        }

        // Instanciation du DatabaseHelper (Singleton)
        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        ArrayList<Quiz> quizList = db.GetAllQuizzes();
        loadRecyclerView(R.id.recycler_view_popular, db.GetAllPopularQuizzes());
        loadRecyclerView(R.id.recycler_view_most_recents, db.GetAllLatestQuizzes());
        loadRecyclerView(R.id.recycler_view_hard, db.GetAllQuizzesFromDifficulty(2));
        loadRecyclerView(R.id.recycler_view_medium, db.GetAllQuizzesFromDifficulty(1));
        loadRecyclerView(R.id.recycler_view_easy, db.GetAllQuizzesFromDifficulty(0));

        recyclerViewSearch.setAdapter(new QuizCard_RecyclerViewAdapter(this, db.GetAllQuizzes()));
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

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewSearch.setAdapter(new QuizCard_RecyclerViewAdapter(getBaseContext(), db.GetMatchingQuizzes(searchBar.getQuery().toString())));
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
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
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, LeaderboardActivity.class);
                intent.putExtra("userId", userId);
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

    private void loadRecyclerView(int id, ArrayList<Quiz> quizList){

        RecyclerView recyclerView = findViewById(id);

        recyclerView.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new RecyclerView_ItemClickListener(new RecyclerView_ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                QuizInfoDialogFragment quizInfoDialogFragment = QuizInfoDialogFragment.newInstance(quizList.get(position), userId);
                quizInfoDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        }));
    }

    private int userId;
}