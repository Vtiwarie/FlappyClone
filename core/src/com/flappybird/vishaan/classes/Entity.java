package com.flappybird.vishaan.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishaan on 8/10/2016.
 */
public abstract class Entity extends Image  {
    protected Animation mAnimation;
    protected float mStateTime = 0.0f;
    public Rectangle mRectangle;
    private float mRectScale = DEFAULT_RECT_SCALE;

    private static final float DEFAULT_RECT_SCALE = 0.90f;

    protected Entity() {

    }
    protected Entity(TextureRegion textureRegion) {
        super(textureRegion);
        initialize();
        init();
    }
    protected Entity(Animation animation) {
        super(animation.getKeyFrame(0));
        mAnimation = animation;
        initialize();
        init();
    }

    final private void init() {
        setScale(getImageScale());
        setHeight(getHeight()*getImageScale());
        setWidth(getWidth()*getImageScale());
        mRectangle = new Rectangle((1+getDefaultRectScale())*getX(), (1+getDefaultRectScale())*getY(), getWidth()*getImageScale()*getDefaultRectScale(), getHeight()*getImageScale()*getDefaultRectScale());
        setDebug(true);
    }

    public float getDefaultRectScale() {
        return mRectScale;
    }

    public void setDefaultRectScale(float scale) {
        mRectScale = scale;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    protected void initialize(){ }

    abstract protected float getImageScale();

    public float getRightX() {
        return getX() + getWidth()*getImageScale();
    }

    public float getMid() {
        return getX() + getWidth()*getImageScale()/2;
    }


    public boolean detectCollision(Entity entity) {
        return Intersector.overlaps(mRectangle, entity.mRectangle);
    }

    @Override
    protected void positionChanged() {
        if(mRectangle != null) {
            mRectangle.setPosition(getX(), getY());
        }
    }

    //    final private void init() {
//        setScale(getImageScale());
//        setHeight(getHeight());
//        setWidth(getWidth());
//        mRectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
//        System.out.println(getClass().getName() + " " + mRectangle);
//        setDebug(true);
//    }

//    @Override
//    public float getWidth() {
//        return (super.getWidth() * getImageScale());
//    }
//
//    @Override
//    public float getHeight() {
//        return (super.getHeight() * getImageScale());
//    }
}
