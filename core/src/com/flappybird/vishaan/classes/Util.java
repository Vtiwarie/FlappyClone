package com.flappybird.vishaan.classes;

import com.flappybird.vishaan.MyGdxGame;

/**
 * Created by Vishaan on 11/14/2016.
 */

public class Util {
    public static final void log(String str) {
        if(MyGdxGame.DEBUG) {
            System.out.println(str);
        }
    }
}
