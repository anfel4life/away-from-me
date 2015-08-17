package com.broodproduct.krot.objects;


import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.krot.render.GameWorld;

import java.util.ArrayList;
import java.util.List;

public class Fly extends DynamicModel {

    private List<Bomb> bombs = new ArrayList<Bomb>();

    public Fly(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
        this.body.setGravityScale(0);
        this.body.setFixedRotation(true);
    }

    @Override
    public void update(float delta) {
        if (body.getPosition().x - width / 2 > gameWorld.unitWidth) {
            reset();
        }
        float speed = delta * 700;
        body.setLinearVelocity(speed, 0);

        //process bombs
        for (Bomb bomb : bombs) {
            bomb.update(delta);
        }
    }

    public void reset() {
        body.setTransform(this.origX, this.origY, 0);
    }

    public void dropBomb(){
        float x = body.getWorldCenter().x;
        float y = body.getWorldCenter().y - width / 2;
        Bomb bomb = new Bomb(x, y, boxWorld, gameWorld);
        bomb.body.setLinearVelocity(body.getLinearVelocity());
        bombs.add(bomb);
    }
}
