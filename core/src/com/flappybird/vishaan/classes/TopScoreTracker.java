package com.flappybird.vishaan.classes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Vishaan on 12/25/2016.
 */

public class TopScoreTracker {
    private static TopScoreTracker sInstance;
    private Preferences mPrefs;
    private static final String HIGH_SCORE_PREFS = "high_score_prefs";
    private static final String SCORE_KEY = "score_key";

    private TopScoreTracker() {
        mPrefs = Gdx.app.getPreferences(HIGH_SCORE_PREFS);
    }

    public static TopScoreTracker getInstance() {
        if(sInstance == null) {
            sInstance = new TopScoreTracker();
        }
        return sInstance;
    }

    public int getCurrentHigh() {
        return mPrefs.getInteger(SCORE_KEY);
    }

    public int putCurrentHigh(int high) {
        mPrefs.putInteger(SCORE_KEY, high);
        mPrefs.flush();
        return 0;
    }

}
