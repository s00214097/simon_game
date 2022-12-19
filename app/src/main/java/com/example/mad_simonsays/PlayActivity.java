package com.example.mad_simonsays;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements SensorEventListener {

    private ScoreDataSource datasource;

    private final double xUp = 8.0;     // upper mag limit
    private final double xUpB = 6.0;
    private final double xDown = -4.0;     // upper mag limit
    private final double xDownB = -2.0;
    private final double yRight = 8.0;
    private final double yRightB = 6.0;
    private final double yLeft = -4.0;
    private final double yLeftB = -2.0;// lower mag limit

    boolean limitUp = false;
    boolean limitDown = false;
    boolean limitLeft = false;
    boolean limitRight = false;

    private final int BLUE = 1;
    private final int YELLOW = 2;
    private final int RED = 3;
    private final int WHITE = 4;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    Button btnBlue, btnYellow, btnRed, btnWhite;
    TextView txtResult;
    Animation anim;
    List list = new ArrayList<>();

    Integer score;

    String seqResult;
    String compareTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        datasource = new ScoreDataSource(this);
        datasource.open();

        //selects accelerometer sensor
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btnBlue = findViewById(R.id.btnLeft);
        btnYellow = findViewById(R.id.btnUp);
        btnRed = findViewById(R.id.btnRight);
        btnWhite = findViewById(R.id.btnDown);
        txtResult = findViewById(R.id.tvResult);

        score = getIntent().getIntExtra("gameScore", 0);

        seqResult = getIntent().getStringExtra("seqResult");
        compareTo = "[" + seqResult + "]";
        //Toast.makeText(this, compareTo, Toast.LENGTH_SHORT).show();

            //click blue
            btnBlue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flashButton(btnBlue);
                    list.add(BLUE);
                    //txtResult.setText(list.toString());
                    compare(list, compareTo, score);
                }
            });

            //click yellow
            btnYellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flashButton(btnYellow);
                    list.add(YELLOW);
                    //txtResult.setText(list.toString());
                    compare(list, compareTo, score);
                }
            });

            //click red
            btnRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flashButton(btnRed);
                    list.add(RED);
                    //txtResult.setText(list.toString());
                    compare(list, compareTo, score);
                }
            });

            //click white
            btnWhite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flashButton(btnWhite);
                    list.add(WHITE);
                    //txtResult.setText(list.toString());
                    compare(list, compareTo, score);
                }
            });
    }
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }

    private void flashButton(Button button) {

        anim = new AlphaAnimation(1,0);
        anim.setDuration(500); //You can manage the blinking time with this parameter

        anim.setRepeatCount(0);
        button.startAnimation(anim);
    }

    private void compare(List mySeq, String gameSeq, int score) {

        String myValues = mySeq.toString();

        if (myValues.equals(gameSeq) && mySeq.size() == 4)
        {
            score ++;
            Toast.makeText(this, "Nice! Point Added! " + score, Toast.LENGTH_SHORT).show();
            mySeq.clear();

            Intent intent  = new Intent(PlayActivity.this, GameActivity.class);
            intent.putExtra("playScore", score);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //startActivity(new Intent(PlayActivity.this,GameActivity.class));
        }
        else if (mySeq.size() == 4)
        {
            Toast.makeText(this, "Sorry! You Lose! " + score, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PlayActivity.this,MainActivity.class));

            ArrayAdapter<Score> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1);

            Score myScore = null;

            String inStr = Integer.toString(score);
            myScore = datasource.createScore("Your Score Is: " + inStr);
            adapter.add(myScore);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if ((x > xUp) && (limitUp == false)) {
                limitUp = true;
            }
            if ((x < xUpB) && (limitUp == true)) {
                flashButton(btnWhite);
                list.add(WHITE);
                //txtResult.setText(list.toString());
                compare(list, compareTo, score);
                limitUp = false;
            }

            if ((x < xDown) && (limitDown == false)) {
                limitDown = true;
            }
            if ((x > xDownB) && (limitDown == true)) {
                flashButton(btnYellow);
                list.add(YELLOW);
                //txtResult.setText(list.toString());
                compare(list, compareTo, score);
                limitDown = false;
            }
            if ((y < yLeft) && (limitLeft == false)) {
                limitLeft = true;
            }
            if ((y > yLeftB) && (limitLeft == true)) {
                flashButton(btnBlue);
                list.add(BLUE);
                //txtResult.setText(list.toString());
                compare(list, compareTo, score);
                limitLeft = false;
            }

            if ((y > yRight) && (limitRight == false)) {
                limitRight = true;
            }
            if ((y < yRightB) && (limitRight == true)) {
                flashButton(btnRed);
                list.add(RED);
                //txtResult.setText(list.toString());
                compare(list, compareTo, score);
                limitRight = false;
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}