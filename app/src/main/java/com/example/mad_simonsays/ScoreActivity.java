package com.example.mad_simonsays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.w3c.dom.Comment;

import java.util.List;

public class ScoreActivity extends ListActivity {

    private ScoreDataSource datasource;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        datasource = new ScoreDataSource(this);
        datasource.open();

        List<Score> values = datasource.getAllScores();

        ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}