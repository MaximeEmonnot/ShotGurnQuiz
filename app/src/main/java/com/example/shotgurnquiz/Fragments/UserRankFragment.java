package com.example.shotgurnquiz.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shotgurnquiz.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRankFragment extends Fragment {

    private static final String ARG_PARAM1 = "username";
    private static final String ARG_PARAM2 = "points";
    private static final String ARG_PARAM3 = "rank";

    private String username;
    private int points;
    private String rank;

    public UserRankFragment() {
    }

    public static UserRankFragment newInstance(String username, int points, String rank) {
        UserRankFragment fragment = new UserRankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        args.putInt(ARG_PARAM2, points);
        args.putString(ARG_PARAM3, rank);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            points = getArguments().getInt(ARG_PARAM2);
            rank = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_view_season_points_card, container, false);

        TextView textViewUsername = (TextView) view.findViewById(R.id.recycler_view_season_points_username);
        TextView textViewPointsCount = (TextView) view.findViewById(R.id.recycler_view_season_points_points_count);
        TextView textViewRank = (TextView) view.findViewById(R.id.recycler_view_season_points_rank);

        textViewUsername.setText(username);
        textViewPointsCount.setText(Integer.toString(points));
        textViewRank.setText(rank);

        return view;
    }
}