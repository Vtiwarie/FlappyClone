package com.flappybird.vishaan;

import com.badlogic.gdx.Gdx;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishaan on 8/7/2016.
 */
public class GameManager {
    private static GameManager mInstance = new GameManager();

    public static GameManager getInstance() {
        if(mInstance == null) {
            mInstance = new GameManager();
        }
        return mInstance;
    }

    private GameManager() {
        init();
    }

    private Stage mStage;
    private Image[] mBackground = new Image[2];
    private Bird mBird;
    private List<DoubleColumn> mColumns = new ArrayList<DoubleColumn>();
    private int mScore = 0;
    private int mCurrentColumnIndex = 0;

    private void init() {
        mStage = new Stage(new StretchViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT));
        Gdx.input.setInputProcessor(mStage);
        mBackground[0] = new Image(new Texture(Gdx.files.internal("bg.png")));
        mBackground[1] = new Image(new Texture(Gdx.files.internal("bg.png")));
        mBackground[1].setPosition(mBackground[0].getImageX()+ mBackground[0].getWidth(), mBackground[1].getImageY());

        TextureRegion[] regions = new TextureRegion[] {
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
                System.out.println("touched up");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void addColumn() {
        DoubleColumn endColumn = (mColumns.size() > 0) ? mColumns.get(mColumns.size()-1) : null;
        if(mColumns.isEmpty() || endColumn != null && DoubleColumn.getHorizontalSpacing(endColumn) < MyGdxGame.WIDTH) {
            Column newColumn = new Column(new TextureRegion(new Texture(Gdx.files.internal("toptube.png"))));
            mStage.addActor(newColumn);

            Column newColumn2 = new Column(new TextureRegion(new Texture(Gdx.files.internal("bottomtube.png"))));
            mStage.addActor(newColumn2);

            DoubleColumn doubleColumn = new DoubleColumn(newColumn, newColumn2);
            mColumns.add(doubleColumn);
        }
    }

    private void moveColumns() {
        for(DoubleColumn doubleColumn : mColumns) {
            doubleColumn.moveBy(doubleColumn.mVelocity, 0);
        }
    }

    private void fall() {
        mBird.fall();
    }

    private void trackScore() {
        if(mBird.getRightX() > mColumns.get(mCurrentColumnIndex).getRightX()) {
            mScore++;
            System.out.println("Score: " + mScore + " " + "Column # " + mCurrentColumnIndex);
            mCurrentColumnIndex++;
        }
    }

    private void detectGameOver() {
        for(DoubleColumn doubleColumn : mColumns) {
            if(mBird.detectCollision(doubleColumn.mTop) /*|| mBird.detectCollision(doubleColumn.mBottom)*/) {
                System.out.println(mBird.mRectangle);
                System.out.println(doubleColumn.mTop.mRectangle);
                System.out.println("COLLIDED");
            }
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        addColumn();
        moveColumns();
        if(Gdx.input.justTouched()) {
            mBird.jump();
        }
        fall();
        if(mStage.getActors().contains(mBird, true)) {
            mStage.getActors().get(mStage.getActors().indexOf(mBird, true)).remove();
            mStage.addActor(mBird);
        }

        trackScore();
        detectGameOver();

        getStage().act();
        getStage().draw();
    }

    public Stage getStage() {
        return mStage;
    }

}
