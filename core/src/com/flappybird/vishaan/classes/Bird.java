package com.flappybird.vishaan.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.flappybird.vishaan.MyGdxGame;

/**
 * Created by Vishaan on 8/10/2016.
 */
public class Bird extends Entity {
    private float mVelocity = 0;
    private float mAcceleration = 0.35f;
    private float mAngularVelocity = 0;
    private float mAngularAcceleration = 0.04f;
    private static final float IMAGE_SCALE = 0.55f;
    public static float FRAME_DURATION = 0.10f;
    private static float DEFUALT_RECT_SCALE = 0.9f;
    private static final float VELOCITY_BEFORE_ROTATE = -5f;

    public Bird(Animation animation) {
        super(animation);
    }

    @Override
    protected void initialize() {
        setDefaultRectScale(DEFUALT_RECT_SCALE);
        setPosition(MyGdxGame.WIDTH/4, MyGdxGame.HEIGHT/2);
    }

    @Override
    protected float getImageScale() {
        return IMAGE_SCALE;
    }

    @Override
    public void act(float delta) {
        ((TextureRegionDrawable)getDrawable()).setRegion(mAnimation.getKeyFrame(mStateTime += delta, true));
        super.act(delta);
    }

    @Override
    public void update() {
        fall();
        rotate();
    }

    public void fall() {
        mVelocity -= mAcceleration;
        moveBy(0, mVelocity);
    }

    public void rotate() {
        if(mVelocity <= VELOCITY_BEFORE_ROTATE) {
            if(getRotation() > -60) {
                mAngularVelocity -= mAngularAcceleration;
                rotateBy(mAngularVelocity);

            }
        }
    }

    public void jump() {
        mVelocity = 6.5f;
        setRotation(20);
    }

}
