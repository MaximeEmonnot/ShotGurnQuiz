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

import com.example.shotgurnquiz.Fragments.AddQuestionDialogFragment;
import com.example.shotgurnquiz.Fragments.QuizInfoDialogFragment;
import com.example.shotgurnquiz.Models.QuestionModel;
import com.example.shotgurnquiz.Models.QuizCardModel;
import com.example.shotgurnquiz.Models.QuizModel;
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
        else {
            username = bundle.getString("username");
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

        //Pour le test
        ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
        questions.add(new QuestionModel("Le plus long trajet de train est parcouru par le Transsibérien. ", "Vrai", "Faux", false));
        questions.add(new QuestionModel("Le chemin de fer de la mort en Thaïlande n’est en fait pas si mortel.", "Vrai", "Faux", false));
        questions.add(new QuestionModel("L’assassinat d’Abraham Lincoln a contribué à la popularisation des voyages en train à travers les États-Unis. ", "Vrai", "Faux", true));
        QuizModel quiz = new QuizModel("les trains", "Transport", "Intermediaire", questions);

        recyclerViewPopular.setAdapter(new QuizCard_RecyclerViewAdapter(this, quizCards));
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopular.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));
        recyclerViewPopular.addOnItemTouchListener(new RecyclerView_ItemClickListener(new RecyclerView_ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                QuizInfoDialogFragment quizInfoDialogFragment = new QuizInfoDialogFragment(quiz);
                quizInfoDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        }));

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

    private String username;
}