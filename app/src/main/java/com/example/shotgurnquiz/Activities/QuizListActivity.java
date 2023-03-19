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


        // Remplissage des recyclerView avec la liste de quiz correspondant en base de données
        loadRecyclerView(R.id.recycler_view_popular, db.GetAllPopularQuizzes());
        loadRecyclerView(R.id.recycler_view_most_recents, db.GetAllLatestQuizzes());
        loadRecyclerView(R.id.recycler_view_hard, db.GetAllQuizzesFromDifficulty(2));
        loadRecyclerView(R.id.recycler_view_medium, db.GetAllQuizzesFromDifficulty(1));
        loadRecyclerView(R.id.recycler_view_easy, db.GetAllQuizzesFromDifficulty(0));


        // Remplissage du recyclerView de la barre de recherche avec tous les quiz dans un premier temps
        recyclerViewSearch.setAdapter(new QuizCard_RecyclerViewAdapter(this, db.GetAllQuizzes()));
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSearch.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

        // lors d'un clique sur la barre de recherche ou sur l'icone de recherche le recycler view s'affiche avec une animation
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

        // Lorsque le texte de la recherche change, le recyclerView est mis à jour
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


        // lors d'un appui sur le bouton close, le recycler view disparait avec une animation
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

        // lors d'un appui sur le bouton profil, l'activité correspondante s'ouvre
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // lors d'un appui sur le bouton leaderboard, l'activité correspondante s'ouvre
        leaderboardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, LeaderboardActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // lors d'un appui sur le bouton d'ajout de quiz, l'activité correspondante s'ouvre
        addQuizButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, AddQuizActivity.class);
                startActivity(intent);
            }
        });
    }

    // Methode prenant en paramètre l'id du recyclerView et une liste de quiz, permet le remplissage du recyclerView avec la liste de quiz
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

    // Differentes variables de l'activité
    private int userId;
}