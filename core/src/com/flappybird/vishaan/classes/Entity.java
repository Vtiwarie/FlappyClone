package com.flappybird.vishaan.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishaan on 8/10/2016.
 */
public abstract class Entity extends Image implements Updateable {
    protected Animation mAnimation;
    protected float mStateTime = 0.0f;
    public Rectangle mRectangle;
    private float mRectScale = DEFAULT_RECT_SCALE;
    public static List<Entity> sEntities = new ArrayList<Entity>();
    protected Polygon mPolygon;


    private static final float DEFAULT_RECT_SCALE = 0.95f;

    protected Entity() {
        init();
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
        sEntities.add(this);
        mRectangle = new Rectangle((1+getDefaultRectScale())*getX(), (1+getDefaultRectScale())*getY(), getWidth()*getImageScale()*getDefaultRectScale(), getHeight()*getImageScale()*getDefaultRectScale());
        mPolygon = new Polygon(
                new float[]{
                        0, 0,
                        mRectangle.getWidth(), 0,
                        mRectangle.getWidth(), mRectangle.getHeight(),
                        0, mRectangle.getHeight()
                }
        );
        mPolygon.setOrigin(mRectangle.getWidth()/2, mRectangle.getHeight()/2);
        mPolygon.setPosition(mRectangle.getX(), mRectangle.getY());
        setDebug(true);
    }

    protected void initialize(){
    }


    public void update() {

    }

    public static void updateAll() {
        for(Entity entity : sEntities) {
            entity.update();
        }
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

    abstract protected float getImageScale();

    public float getRightX() {
        return getX() + getWidth()*getImageScale();
    }

    public float getMid() {
        return getX() + getWidth()*getImageScale()/2;
    }

    public boolean detectCollision(Entity entity) {
        return Intersector.overlapConvexPolygons(entity.mPolygon, mPolygon);
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);

    }

    @Override
    protected void positionChanged() {
        if(mRectangle != null) {
            mRectangle.setPosition(getX(), getY());
        }
        if(mPolygon != null) {
            mPolygon.setOrigin(mRectangle.getWidth()/2, mRectangle.getHeight()/2);
            mPolygon.setPosition(mRectangle.getX(), mRectangle.getY());
        }
    }
}
