package com.flappybird.vishaan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.flappybird.vishaan.classes.Bird;
import com.flappybird.vishaan.classes.Column;
import com.flappybird.vishaan.classes.Column.DoubleColumn;
import com.flappybird.vishaan.classes.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishaan on 8/7/2016.
 */
public class GameManager {
    private static GameManager mInstance;
    private boolean mGameOver;
    private boolean mGameStarted;
    private Game mGame;
    private Screen mGameScreen;

    public static GameManager getInstance(Screen screen) {
        if (mInstance == null) {
            mInstance = new GameManager(screen);
        }
        return mInstance;
    }

    private GameManager(Screen screen) {
        init(screen);
    }

    private Stage mStage;
    private Image[] mBackground;
    private Bird mBird;
    private List<DoubleColumn> mColumns;
    private int mScore;
    private int mCurrentColumnIndex;

    private void init(Screen screen) {
        mGame = MyGdxGame.GAME;
        mGameScreen = screen;
        mStage = new Stage(new StretchViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT));
        Gdx.input.setInputProcessor(mStage);
        mBackground = new Image[2];
        mBackground[0] = new Image(new Texture(Gdx.files.internal("bg.png")));
        mBackground[1] = new Image(new Texture(Gdx.files.internal("bg.png")));
        mBackground[1].setPosition(mBackground[0].getImageX() + mBackground[0].getWidth(), mBackground[1].getImageY());
        mColumns = new ArrayList<DoubleColumn>();
        mCurrentColumnIndex = 0;
        mScore = 0;
        mGameOver = false;
        mGameStarted = false;

        TextureRegion[] regions = new TextureRegion[]{
                new TextureRegion(new Texture(Gdx.files.internal("bird.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("bird2.png"))),
        };
        mBird = new Bird(new Animation(Bird.FRAME_DURATION, regions));
        mStage.addActor(mBackground[0]);
        mStage.addActor(mBackground[1]);
        addColumn();
        mStage.addActor(mBird);

        mStage.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Util.log("touched up");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    /**
     * Remove column from screen when passed to left of screen
     */
    public void removeColumn() {
        if (mColumns != null && !mColumns.isEmpty()) {
            DoubleColumn column = mColumns.get(0);
            if (column != null && DoubleColumn.getHorizontalSpacing(column) < 0) {
                Util.log("Removing column");
                mColumns.remove(column);
                Util.log("Column count: " + mColumns.size());
            }
        }
    }

    private void addColumn() {
        DoubleColumn endColumn = (mColumns.size() > 0) ? mColumns.get(mColumns.size() - 1) : null;
        if (mColumns.isEmpty() || endColumn != null && DoubleColumn.getHorizontalSpacingWithMargin(endColumn) < MyGdxGame.WIDTH) {
            Column newColumn = new Column(new TextureRegion(new Texture(Gdx.files.internal("toptube.png"))));
            mStage.addActor(newColumn);

            Column newColumn2 = new Column(new TextureRegion(new Texture(Gdx.files.internal("bottomtube.png"))));
            mStage.addActor(newColumn2);

            DoubleColumn doubleColumn = new DoubleColumn(newColumn, newColumn2);
            mColumns.add(doubleColumn);
        }
    }

    private void moveColumns() {
        for (DoubleColumn doubleColumn : mColumns) {
            doubleColumn.moveBy(doubleColumn.mVelocity, 0);
        }
    }

    private void fall() {
        mBird.fall();
    }

    private void trackScore() {
        if (mBird.getRightX() > mColumns.get(mCurrentColumnIndex).getRightX()) {
            mScore++;
            Util.log("Score: " + mScore + " " + "Column # " + mCurrentColumnIndex);
            mCurrentColumnIndex++;
        }
    }

    private void detectGameOver() {
        if (detectCollision() || detectCollideWithBorder()) {
            mGameOver = true;
            Util.log("GAME OVER");
            showGameOver();
        }
    }

    private boolean detectCollideWithBorder() {
        return (mBird.getY() + mBird.getHeight() > MyGdxGame.HEIGHT || mBird.getY() < 0 || mBird.getX() + mBird.getX() > MyGdxGame.WIDTH || mBird.getX() < 0);
    }

    private boolean detectCollision() {
        for (DoubleColumn doubleColumn : mColumns) {
            if (mBird.detectCollision(doubleColumn.mTop) || mBird.detectCollision(doubleColumn.mBottom)) {
                Util.log("COLLIDED");
                return true;
            }
        }
        return false;
    }

    private void showGameOver() {

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!mGameOver) {
            if(mGameStarted) {
                if (Gdx.input.justTouched()) {
                    mBird.jump();
                }
                fall();
                if (mStage.getActors().contains(mBird, true)) {
                    mStage.getActors().get(mStage.getActors().indexOf(mBird, true)).remove();
                    mStage.addActor(mBird);
                }

                trackScore();
                detectGameOver();

                removeColumn();
                addColumn();
                moveColumns();
            } else if(Gdx.input.justTouched()) {
                mGameStarted = true;
            }

            getStage().act();
        } else if (Gdx.input.justTouched()) {
            mGameOver = false;
            mGameStarted = false;
            init(mGameScreen);
            mGame.setScreen(mGameScreen);
        }

        getStage().draw();

    }

    public Stage getStage() {
        return mStage;
    }

}
