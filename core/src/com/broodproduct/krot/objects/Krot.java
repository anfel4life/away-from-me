package com.broodproduct.krot.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.render.GameWorld;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public class Krot extends BaseModel {

    private boolean forward = true;
    private float krotMoveInterval = 0;

    public Krot(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
    }

    @Override
    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.position.set(origX, origY);
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width, this.height);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.4f;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        Body body = boxWorld.createBody(bd);
        body.createFixture(fd);
        body.setUserData(this);
        shape.dispose();
        return body;
    }

    public void walk() {
        float x = forward ? -20 : 20;
        body.applyLinearImpulse(x, 0, body.getPosition().x, body.getPosition().y, true);
    }

    public void reset() {
        body.setTransform(origX, origY, 0);
        body.setLinearVelocity(new Vector2(0, 0));
    }

    public void changeDirection() {
        this.forward = !forward;
    }

    @Override
    protected Vector2 initOrigin() {
        return new Vector2(origX, origY);
    }

    @Override
    public void update(float delta) {
        krotMoveInterval += delta;
        if (krotMoveInterval >= 2) {
            walk();
            krotMoveInterval = 0;
        }
    }
}
