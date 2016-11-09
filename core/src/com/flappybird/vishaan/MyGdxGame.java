package com.flappybird.vishaan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.vishaan.screens.GameScreen;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
    GameScreen mGameScreen;

    public static final int WIDTH = 288*2;
    public static final int HEIGHT = 512;
	
	@Override
	public void create () {
        mGameScreen = new GameScreen();
        setScreen(mGameScreen);
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
        super.dispose();
	}
}
