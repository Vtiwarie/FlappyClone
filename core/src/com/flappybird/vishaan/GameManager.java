package com.flappybird.vishaan;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.flappybird.vishaan.classes.Bird;
import com.flappybird.vishaan.classes.Column;
import com.flappybird.vishaan.classes.Column.DoubleColumn;
import com.flappybird.vishaan.classes.Entity;
import com.flappybird.vishaan.classes.Util;

import java.util.ArrayList;
import java.util.List;

//--TODO Remove entities when deleting column
//--TODO Fix points system
//TODO Add rotations
//TODO Add game over message and restart
//TODO add score GUI
//TODO Add Sound
//TODO Add add platform
//TODO Add social media integration
//TODO Remove debugging logs and draw debugging
//TODO Hire Freelancer and replace graphics
//TODO Test on android

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
    private DoubleColumn mColumnCollided;

    private void init(Screen screen) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
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
        mColumnCollided = null;
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
    }

    /**
     * Remove column from screen when passed to left of screen
     */
    public void removeColumn() {
        if (mColumns != null && !mColumns.isEmpty()) {
            DoubleColumn column = mColumns.get(0);
            if (column != null && DoubleColumn.getHorizontalSpacing(column) < 0) {
                mColumns.set(0, null);
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

//    private void moveColumns() {
//        for (DoubleColumn doubleColumn : mColumns) {
//            if(doubleColumn == null) {
//                continue;
//            }
//            doubleColumn.moveBy(doubleColumn.mVelocity, 0);
//        }
//    }

    private void trackScore() {
        if (mColumns == null || mColumns.isEmpty() || mColumns.get(mCurrentColumnIndex) == null) {
            return;
        }
        if (mColumns.get(mCurrentColumnIndex).getMid() < mBird.getX()) {
                mScore++;
                Util.log("Score: " + mScore + " " + "Column #" + mCurrentColumnIndex, "trackscore");
                mCurrentColumnIndex++;
        }
    }

    private void detectGameOver() {
        if (detectCollision() || detectCollideWithBorder()) {
            mGameOver = true;
            showGameOver();
        }
    }

    private boolean detectCollideWithBorder() {
        return (mBird.getY() > MyGdxGame.HEIGHT || mBird.getY() < 0 || mBird.getX()  > MyGdxGame.WIDTH || mBird.getX() < 0);
    }

    private boolean detectCollision() {
        for (DoubleColumn doubleColumn : mColumns) {
            if(doubleColumn == null) {
                continue;
            }
            if (mBird.detectCollision(doubleColumn.mTop) || mBird.detectCollision(doubleColumn.mBottom)) {
                Util.log("Collided with column", "collision");
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
                Entity.updateAll();
                if (mStage.getActors().contains(mBird, true)) {
                    mStage.getActors().get(mStage.getActors().indexOf(mBird, true)).remove();
                    mStage.addActor(mBird);
                }

                trackScore();
                detectGameOver();
                addColumn();
                removeColumn();
                getStage().act();
            } else if(Gdx.input.justTouched()) {
                mGameStarted = true;
            }

        } else if (Gdx.input.justTouched()) {
            init(mGameScreen);
            mGame.setScreen(mGameScreen);
        }

        getStage().draw();

    }

    public Stage getStage() {
        return mStage;
    }

}
