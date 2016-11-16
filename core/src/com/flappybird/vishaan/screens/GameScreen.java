package com.flappybird.vishaan.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.flappybird.vishaan.GameManager;

/**
 * Created by Vishaan on 8/7/2016.
 */
public class GameScreen implements Screen{

    GameManager gm;

    @Override
    public void show() {
        gm = GameManager.getInstance(this);
    }

    @Override
    public void render(float delta) {
        gm.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
