package com.flappybird.vishaan.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
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
    protected void initialize() {
    }

    @Override
    protected float getImageScale() {
        return IMAGE_SCALE;
    }

    public static class DoubleColumn {
        public Column mTop;
        public Column mBottom;
        public float mVelocity = -3.0f;
        private static final float VERTICAL_SPACING = MyGdxGame.HEIGHT/4;

        public DoubleColumn(Column top, Column bottom) {
            mTop = top;
            mBottom = bottom;

            mTop.setPosition(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
            Random random = new Random();
            float leastAmount = MyGdxGame.HEIGHT - mTop.getHeight()*IMAGE_SCALE - VERTICAL_SPACING;
            System.out.println(leastAmount);
            float amount = random.nextInt((int) (mTop.getHeight() * IMAGE_SCALE));
            if(amount < leastAmount) {
                amount = leastAmount;
            }
            mTop.moveBy(0, -amount);
            mBottom.setPosition(MyGdxGame.WIDTH, mTop.getY() - mBottom.getHeight()*IMAGE_SCALE - VERTICAL_SPACING);
        }

        public float getX() {
            return mTop.getX();
        }

        public void moveBy(float x, float y) {
            mTop.moveBy(x, y);
            mBottom.moveBy(x, y);
        }

        public float getRightX() {
            return mTop.getRightX();
        }

        static public float getHorizontalSpacing(DoubleColumn endColumn) {
            return endColumn.getX() + endColumn.mTop.getWidth() + MyGdxGame.WIDTH/6;
        }

    }
}
