package com.broodproduct.krot.objects;

import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.render.GameWorld;


public class Bomb extends DestroBox {

    public Bomb(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
        this.body.setFixedRotation(true);
    }

    @Override
    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.position.set(origX, origY);
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width / 2, this.height / 2);

        FixtureDef fd = new FixtureDef();
        fd.density = 1f;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        Body body = boxWorld.createBody(bd);
        body.createFixture(fd);
        body.setUserData(this);
        shape.dispose();
        return body;
    }

    public Bomb(float x, float y, World boxWorld, GameWorld gameWorld) {
        this(x, y, 2, 2, boxWorld, gameWorld);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

}
