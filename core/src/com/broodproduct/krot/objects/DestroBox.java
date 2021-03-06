package com.broodproduct.krot.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.render.GameWorld;
import com.broodproduct.krot.tool.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public class DestroBox extends DynamicModel {

    private List<Body> particles = new ArrayList<Body>();
    private static final float PARTICLES_TTL = 6f;
    private float particlesTime = 0;
    private boolean needToDestroy;


    public DestroBox(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
    }

    public void reset() {
        if (body == null) {
            this.body = initBody();
        } else {
            body.setTransform(this.origX, this.origY, 0);
            body.setLinearVelocity(new Vector2(0, 0));
        }
    }

    @Override
    public void update(float delta) {
        processParticles(delta);
        if (needToDestroy) {
            boom2();
            needToDestroy = false;
        }
    }

    private void processParticles(float delta) {
        if (particlesTime != -1)
            particlesTime += delta;
        if (particlesTime >= PARTICLES_TTL) {
            destroyParticles();
            particlesTime = -1;
        }
    }

    private void destroyParticles() {
        for (Body particle : particles) {
            boxWorld.destroyBody(particle);
        }
        particles.clear();
    }



    public void boom2() {
        Vector2 position = body.getPosition();
        boxWorld.destroyBody(body);
        body = null;
        int particlesCount = 4;
        for (int i = 0; i < particlesCount; i++) {
            BodyDef bd = new BodyDef();
            bd.position.set(position);
            bd.type = BodyDef.BodyType.DynamicBody;

            PolygonShape shape = new PolygonShape();

            shape.setAsBox((this.width / 4), (this.height / 4));

            FixtureDef fd = new FixtureDef();
            fd.density = 0.4f;
            fd.friction = 0.5f;
            fd.restitution = 0.5f;
            fd.shape = shape;

            Body body = boxWorld.createBody(bd);
            body.createFixture(fd);
            shape.dispose();

            float angle = (360 / particlesCount) * i;
            //from degrees to radians
            angle = (float) Math.toRadians(angle);
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);
            Vector2 velocity = new Vector2(x, y);
            body.setLinearVelocity(velocity.scl(20));

            particles.add(body);
            particlesTime = 0;
        }
    }

    public void setNeedToDestroy(boolean need) {
        needToDestroy = need;
    }
}
