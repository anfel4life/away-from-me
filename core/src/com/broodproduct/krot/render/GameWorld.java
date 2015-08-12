package com.broodproduct.krot.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.listeners.KrotContactListener;
import com.broodproduct.krot.objects.DestroBox;
import com.broodproduct.krot.objects.Krot;
import com.broodproduct.krot.tool.Constants;

public class GameWorld {
    public final float unitWidth; //60 meters
    public final float unitHeight; //33.75 meters
    private final Krot krot;
    private World boxWorld = new World(new Vector2(0, -20), true);
    private Body groundBody;
    private DestroBox destroBox;

    public GameWorld() {
        this.unitWidth = Constants.scaleToUnit(Constants.GAME_PIXEL_WIDTH);
        this.unitHeight = Constants.scaleToUnit(Constants.GAME_PIXEL_HEIGTH);
        this.destroBox = new DestroBox(2, unitHeight - 12, 1, 1, boxWorld, this);
        this.krot = new Krot(unitWidth - 3, 1, 1.5f, 1.5f, boxWorld, this);
        //ground
        createWall(0, 0, unitWidth, 0.2f, "ground");
        //ceiling
        createWall(0, unitHeight, unitWidth, 0.2f, "ceiling");
        //left
        createWall(0, 0, 0.2f, unitHeight, "leftWall");
        //right
        createWall(unitWidth, 0, 0.2f, unitHeight, "rightWall");
        //left Rack
        createWall(2, 10, 2, 10, "rack");
        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        groundBody = boxWorld.createBody(new BodyDef());

        //krot contact
        boxWorld.setContactListener(new KrotContactListener());
    }

    private void createWall(float x, float y, float width, float height, Object gameObject) {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;
        Body body = boxWorld.createBody(bd);
        body.createFixture(fd);
        body.setUserData(gameObject);
        shape.dispose();
    }

    public void update(float delta) {
        boxWorld.step(1 / 60f, 10, 10);
        krot.update(delta);
        destroBox.update(delta);
    }

    public void dispose() {
        boxWorld.dispose();
    }

    public World getBoxWorld() {
        return boxWorld;
    }

    public Body getGroundBody() {
        return groundBody;
    }

    public DestroBox getDestroBox() {
        return destroBox;
    }

}

