package com.flappybird.vishaan.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flappybird.vishaan.GameManager;
import com.flappybird.vishaan.MyGdxGame;

/**
 * Created by Vishaan on 8/7/2016.
 */
public class GameScreen implements Screen{

    GameManager gm;
    private SpriteBatch mBatch;
    private BitmapFont mScoreFont;
    private OrthographicCamera mCamera;
    Texture mFont;

    @Override
    public void show() {
        mCamera = new OrthographicCamera(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        gm = GameManager.getInstance(this);
        mBatch = new SpriteBatch();
        mFont = new Texture(Gdx.files.internal("default.png"));
        mFont.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mScoreFont = new BitmapFont(Gdx.files.internal("default.fnt"), new TextureRegion(mFont), false);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.setProjectionMatrix(mCamera.combined);

        gm.render(delta);
        mBatch.begin();
        mScoreFont.setColor(255.0f, 255.0f, 255.0f, 255.0f);

        mScoreFont.draw(mBatch, "score: " + gm.getScore(), MyGdxGame.WIDTH/6, MyGdxGame.HEIGHT/2);
        mBatch.end();
    }

    @Override
    public void dispose()
    {
        mBatch.dispose();
        mScoreFont.dispose();
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

//    @Override
//    public void dispose() {
//
//    }

}
