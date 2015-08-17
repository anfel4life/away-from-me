package com.broodproduct.krot.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.render.GameWorld;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public class Krot extends DynamicModel {
    private boolean forward = true;
    private float krotMoveInterval = 1;
    private float hitPoints = 100f;

    public Krot(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
    }

    public void walk() {
        float x = forward ? -20 : 20;
        body.applyLinearImpulse(x, 0, body.getPosition().x, body.getPosition().y, true);
    }

    public void reset() {
        body.setTransform(origX, origY, 0);
        body.setLinearVelocity(new Vector2(0, 0));
    }

    public void acceptDamage(float dmg) {
        this.hitPoints -= dmg;
    }

    public void changeDirection() {
        this.forward = !forward;
    }

    @Override
    public void update(float delta) {
        krotMoveInterval += delta;
        if (krotMoveInterval >= 1) {
            walk();
            krotMoveInterval = 0;
        }
    }

    public float getHitPoints() {
        return hitPoints;
    }

    public void die() {
        boxWorld.destroyBody(body);
    }
}
