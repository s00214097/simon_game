package com.example.mad_simonsays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ScoreDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqLite dbHelper;
    private String[] allColumns = { SqLite.COLUMN_ID,
            SqLite.COLUMN_SCORE };

    public ScoreDataSource(Context context) {
        dbHelper = new SqLite(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Score createScore(String score) {
        ContentValues values = new ContentValues();
        values.put(SqLite.COLUMN_SCORE, score);
        long insertId = database.insert(SqLite.TABLE_SCORES, null,
                values);
        Cursor cursor = database.query(SqLite.TABLE_SCORES,
                allColumns, SqLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Score newScore = cursorToScore(cursor);
        cursor.close();
        return newScore;
    }

    public void deleteScore(Score score) {
        long id = score.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SqLite.TABLE_SCORES, SqLite.COLUMN_ID
                + " = " + id, null);
    }

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<Score>();

        Cursor cursor = database.query(SqLite.TABLE_SCORES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Score score = cursorToScore(cursor);
            scores.add(score);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return scores;
    }

    private Score cursorToScore(Cursor cursor) {
        Score score = new Score();
        score.setId(cursor.getLong(0));
        score.setScore(cursor.getString(1));
        return score;
    }
}
