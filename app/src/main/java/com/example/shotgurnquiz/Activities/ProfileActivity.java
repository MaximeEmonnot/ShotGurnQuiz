package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;
import com.example.shotgurnquiz.R;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        DatabaseHelper db = DatabaseHelper.GetInstance(this);
        User user = db.FindUserByUsername(username);

        TextView profile = (TextView) findViewById(R.id.profile_text);
        profile.setText(user.toString());
    }
}