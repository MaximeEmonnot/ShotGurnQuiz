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

        Bundle bundle = getIntent().getExtras();
        boolean bIsConnected = bundle.getBoolean("bIsConnected");

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
        else {
            username = bundle.getString("username");
        }

        DatabaseHelper db = DatabaseHelper.GetInstance(this);
        ArrayList<Quiz> quizList = db.GetAllQuizzes();

        loadRecyclerView(R.id.recycler_view_popular, db.GetAllPopularQuizzes());
        loadRecyclerView(R.id.recycler_view_most_recents, db.GetAllLatestQuizzes());
        loadRecyclerView(R.id.recycler_view_hard, db.GetAllQuizzesFromDifficulty("hard"));
        loadRecyclerView(R.id.recycler_view_medium, db.GetAllQuizzesFromDifficulty("medium"));
        loadRecyclerView(R.id.recycler_view_easy, db.GetAllQuizzesFromDifficulty("easy"));

        recyclerViewSearch.setAdapter(new QuizCard_RecyclerViewAdapter(this, new ArrayList<Quiz>()));
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
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, LeaderboardActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        addQuizButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, AddQuizActivity.class);
                intent.putExtra("username", username);
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
                QuizInfoDialogFragment quizInfoDialogFragment = new QuizInfoDialogFragment(quizList.get(position));
                quizInfoDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        }));
    }

    private String username;
}