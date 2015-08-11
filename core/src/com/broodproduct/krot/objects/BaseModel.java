package com.broodproduct.krot.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.broodproduct.krot.render.GameWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public abstract class BaseModel {
    protected GameWorld gameWorld;
    protected World boxWorld;
    protected float origX, origY;
    protected Body body;
    protected Vector2 origin;
    protected float width;
    protected float height;
    protected List<Body> brokenParts;

    public BaseModel(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        this.width = width;
        this.height = height;
        this.origX = x;
        this.origY = y;
        this.boxWorld = boxWorld;
        this.gameWorld = gameWorld;
        this.brokenParts = new ArrayList<Body>();
        this.body = initBody();
        this.origin = initOrigin();
        this.body.setUserData(this);
    }

    protected abstract Body initBody();

    protected abstract Vector2 initOrigin();

    public Body getBody() {
        return body;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public abstract void update(float delta);

}
