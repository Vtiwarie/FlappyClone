package com.flappybird.vishaan.classes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vishaan on 11/16/2016.
 */

public abstract class Collider {
    private Entity mEntity;
    private Map<Entity, AbstractCollisionState> map = new HashMap<Entity, AbstractCollisionState>();

    public Collider(Entity entity) {
        mEntity = entity;
        map.put(entity, new UnCollidedState());
    }

    public boolean justCollided(Entity entity) {
        if(mEntity.mRectangle.overlaps(entity.mRectangle) ) {
            if( ! map.containsKey(entity)) {
                map.put(entity, new CollidedState());
            }
            else if(map.get(entity) instanceof UnCollidedState) {
                map.put(entity, new CollidedState());
                return true;
            }  else  {
                map.put(entity, new UnCollidedState());
                return false;
            }
        } else {
            map.put(entity, new UnCollidedState());
        }
        return false;
    }

    public boolean justExited(Entity entity) {
        if( ! mEntity.mRectangle.overlaps(entity.mRectangle) ) {
            if( ! map.containsKey(entity)) {
                map.put(entity, new UnCollidedState());
            }
            if(map.get(entity) instanceof CollidedState) {
                map.put(entity, new UnCollidedState());
                return true;
            }  else  {
                return false;
            }
        }
        return false;
    }

    public abstract void onCollisionEnter(Entity entity);

    public abstract void onCollisionExit(Entity entity);

    private class AbstractCollisionState {

    }

    private class CollidedState extends AbstractCollisionState {

    }

    private class UnCollidedState extends AbstractCollisionState{

    }
}
