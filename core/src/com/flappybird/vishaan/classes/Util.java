package com.flappybird.vishaan.classes;

import com.badlogic.gdx.Gdx;
import com.flappybird.vishaan.MyGdxGame;

/**
 * Created by Vishaan on 11/14/2016.
 */

public class Util {
    public static final void log(String str, String tag) {
        if(MyGdxGame.DEBUG) {
            Gdx.app.debug(tag, str);
        }
    }
}
