package com.broodproduct.krot.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.render.GameWorld;

public abstract class DynamicModel extends BaseModel {

    public DynamicModel(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
    }

    @Override
    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.position.set(origX, origY);
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width / 2, this.height / 2);

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

    @Override
    protected Vector2 initOrigin() {
        return new Vector2(origX, origY);
    }

}
