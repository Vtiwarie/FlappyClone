package com.flappybird.vishaan.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.flappybird.vishaan.MyGdxGame;

/**
 * Created by Vishaan on 8/10/2016.
 */
public class Bird extends Entity {
    public static float FRAME_DURATION = 0.10f;
    private float mVelocity = 0;
    private float mAcceleration = 0.35f;
    private static final float IMAGE_SCALE = 0.55f;

    public Bird(Animation animation) {
        super(animation);
    }

    @Override
    protected void initialize() {
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

    public void fall() {
        mVelocity -= mAcceleration;
        moveBy(0, mVelocity);
    }

    public void jump() {
        mVelocity = 6.5f;
    }

}
