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
public class DestroBox extends BaseModel {

    private List<Body> particles = new ArrayList<Body>();
    private static final float PARTICLES_TTL = 6f;
    private float particlesTime = 0;
    private boolean needToDestroy;
    private boolean gainPullPower = false;
    private float pullPower = 0;
    private static final float MAX_PULL_POWER = 1f;

    public DestroBox(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
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

    public void pull() {
        if(body == null)
            reset();
        body.applyForceToCenter(5000 * pullPower, 0, true);
        gainPullPower = false;
        pullPower = 0;
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
    protected Vector2 initOrigin() {
        return new Vector2(origX, origY);
    }

    @Override
    public void update(float delta) {
        processParticles(delta);
        if (needToDestroy) {
            boom2();
            needToDestroy = false;
        }
        if(gainPullPower && pullPower < MAX_PULL_POWER) {
            float sum = pullPower + delta;
            pullPower = sum > MAX_PULL_POWER ? MAX_PULL_POWER : sum;
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

    public void pullAndFly(boolean left) {
        if(body == null)
            reset();
        body.setLinearVelocity(new Vector2(0, 0));
        body.applyLinearImpulse(left ? -10 : 10, 20, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, true);
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

            shape.setAsBox(this.width / 2, this.height / 2);

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

    public void gainPullPower() {
        gainPullPower = true;
    }

    public float getPullPower() {
        return pullPower;
    }
}
