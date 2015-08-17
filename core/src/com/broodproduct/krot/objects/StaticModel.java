package com.broodproduct.krot.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.krot.render.GameWorld;


public abstract class StaticModel extends BaseModel {
    public StaticModel(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
    }

    @Override
    protected Body initBody() {
        return null;
    }

    @Override
    protected Vector2 initOrigin() {
        return null;
    }
}
