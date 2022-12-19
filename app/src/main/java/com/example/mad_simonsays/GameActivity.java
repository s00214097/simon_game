package com.example.mad_simonsays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private final int BLUE = 1;
    private final int YELLOW = 2;
    private final int RED = 3;
    private final int WHITE = 4;

    Integer score;

    Animation anim;

    StringBuilder sb;
    Button btnBlue, btnYellow, btnRed, btnWhite, play;

    int sequenceCount = 4, n = 0;
    static List<Integer> gameSequence = new ArrayList<>();
    int arrayIndex = 0;

    CountDownTimer cdt = new CountDownTimer(6000, 1500) {
        public void onTick(long millisUntilFinished) {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {

            //txtResult.setText(sb.substring(0, sb.length() -2));
            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence.get(i)));

            sequenceCount ++;

            Intent intent  = new Intent(GameActivity.this, PlayActivity.class);
            intent.putExtra("seqResult", sb.substring(0, sb.length() -2));
            intent.putExtra("gameScore", score);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = getIntent().getIntExtra("playScore", 0);

        btnBlue = findViewById(R.id.btnLeft);
        btnYellow = findViewById(R.id.btnUp);
        btnRed = findViewById(R.id.btnRight);
        btnWhite = findViewById(R.id.btnDown);
        play = findViewById(R.id.goBtn);
        //txtResult = findViewById(R.id.tvResult);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPlay();
            }
        });
    }

    private void oneButton() {

        n = getRandom(sequenceCount);
        sb.append(String.valueOf(n) + ", ");

        switch (n) {
            case 1:
                flashButton(btnBlue);
                gameSequence.add(BLUE);
                break;
            case 2:
                flashButton(btnYellow);
                gameSequence.add(YELLOW);
                break;
            case 3:
                flashButton(btnRed);
                gameSequence.add(RED);
                break;
            case 4:
                flashButton(btnWhite);
                gameSequence.add(WHITE);
                break;
            default:
                break;
        }
    }

    private void flashButton(Button button) {

        anim = new AlphaAnimation(1,0);
        anim.setDuration(500); //You can manage the blinking time with this parameter

        anim.setRepeatCount(0);
        button.startAnimation(anim);
    }

    //
    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    public void doPlay() {
        sb = new StringBuilder("");
        cdt.start();
    }

}