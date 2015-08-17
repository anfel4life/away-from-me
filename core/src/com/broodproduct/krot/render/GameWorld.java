package com.broodproduct.krot.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.listeners.KrotContactListener;
import com.broodproduct.krot.objects.Fly;
import com.broodproduct.krot.objects.Krot;
import com.broodproduct.krot.tool.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld {
    public final float unitWidth; //60 meters
    public final float unitHeight; //33.75 meters
    private final List<Krot> krots;
    private final Fly fly;
    private World boxWorld = new World(new Vector2(0, -20), true);
    private Body groundBody;
    private float timeToKrot = 4;

    public GameWorld() {
        this.unitWidth = Constants.scaleToUnit(Constants.GAME_PIXEL_WIDTH);
        this.unitHeight = Constants.scaleToUnit(Constants.GAME_PIXEL_HEIGTH);
        this.krots = new ArrayList<Krot>();
        this.fly = new Fly(0, unitHeight - 2, 4, 2, boxWorld, this);
        //ground
        createWall(0, 0, unitWidth * 3, 0.2f, "ground");
        //ceiling
        //createWall(0, unitHeight, unitWidth*3, 0.2f, "ceiling");
        //left
        //createWall(0, 0, 0.2f, unitHeight, "leftWall");
        //right
        createWall(unitWidth * 3, 0, 0.2f, unitHeight, "rightWall");
        //left Rack
        createWall(0, 0, 4, 10, "rack");
        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        groundBody = boxWorld.createBody(new BodyDef());

        //krot contact
        boxWorld.setContactListener(new KrotContactListener());
    }

    private void createWall(float x, float y, float width, float height, Object gameObject) {
        BodyDef bd = new BodyDef();
        bd.position.set(x + (width / 2), y);
        bd.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

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
        fly.update(delta);
        krotUpdate(delta);
    }

    private void krotUpdate(float delta) {
        Iterator<Krot> krotIterator = krots.iterator();
        while (krotIterator.hasNext()){
            Krot krot = krotIterator.next();
            if(krot.getHitPoints() > 0)
                krot.update(delta);
            else {
                krotIterator.remove();
                krot.die();
            }
        }
        spawnKrot(delta);
    }

    private void spawnKrot(float delta) {
        timeToKrot += delta;
        if(timeToKrot >= 5){
            timeToKrot = 0;
            Krot krot = new Krot(unitWidth - 3, 2, 3, 3, boxWorld, this);
            krots.add(krot);
        }
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

    public Fly getFly() {
        return fly;
    }
}

