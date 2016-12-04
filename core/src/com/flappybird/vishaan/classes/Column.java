package com.flappybird.vishaan.classes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flappybird.vishaan.MyGdxGame;

import java.util.Random;

/**
 * Created by Vishaan on 8/10/2016.
 */
public class Column extends Entity {

    public static final int COLUMN_SPACING = MyGdxGame.WIDTH/3;
    public static final float IMAGE_SCALE = 0.5f;

    public Column(TextureRegion textureRegion) {
        super(textureRegion);
    }

    @Override
    protected float getImageScale() {
        return IMAGE_SCALE;
    }

    @Override
    protected void initialize() {
        super.initialize();
        setDefaultRectScale(1);
    }

    public static class DoubleColumn extends Entity  {
        public Column mTop;
        public Column mBottom;
        public float mVelocity = -2.8f;
        static final float VERTICAL_SPACING = MyGdxGame.HEIGHT/3.5f;

        public DoubleColumn(Column top, Column bottom) {
            mTop = top;
            mBottom = bottom;

            mTop.setPosition(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
            Random random = new Random();
            float leastAmount = MyGdxGame.HEIGHT - mTop.getHeight()*IMAGE_SCALE - VERTICAL_SPACING;
            float amount = random.nextInt((int) (mTop.getHeight() * IMAGE_SCALE));
            if(amount < leastAmount) {
                amount = leastAmount;
            }
            mTop.moveBy(0, -amount);
            mBottom.setPosition(MyGdxGame.WIDTH, mTop.getY() - mBottom.getHeight()*IMAGE_SCALE - VERTICAL_SPACING);

        }

        @Override
        public void update() {
            moveBy(mVelocity, 0);
        }

        public float getX() {
            return (mTop != null) ? mTop.getX() : super.getX();
        }

        public void moveBy(float x, float y) {
            mTop.moveBy(x, y);
            mBottom.moveBy(x, y);
        }

        @Override
        protected float getImageScale() {
            return IMAGE_SCALE;
        }

        public float getRightX() {
            return mTop.getRightX();
        }

        static public float getHorizontalSpacing(DoubleColumn endColumn) {
            return endColumn.getX() + endColumn.mTop.getWidth();
        }

        static public float getHorizontalSpacingWithMargin(DoubleColumn endColumn) {
            return getHorizontalSpacing(endColumn) + MyGdxGame.WIDTH/6.5f;
        }

        @Override
        protected void initialize() {
            setDefaultRectScale(IMAGE_SCALE);
        }
    }
}
