package com.broodproduct.krot.objects;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.krot.render.GameWorld;

public class PullableBox extends DestroBox {

    private boolean gainPullPower = false;
    private float pullPower = 0;
    private static final float MAX_PULL_POWER = 1f;

    public PullableBox(float x, float y, float width, float height, World boxWorld, GameWorld gameWorld) {
        super(x, y, width, height, boxWorld, gameWorld);
    }

    public void pull() {
        if (body == null)
            reset();
        body.applyForceToCenter(5000 * pullPower, 0, true);
        gainPullPower = false;
        pullPower = 0;
    }

    public void pullAndFly(boolean left) {
        if (body == null)
            reset();
        body.setLinearVelocity(new Vector2(0, 0));
        body.applyLinearImpulse(left ? -10 : 10, 20, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, true);
    }

    public void gainPullPower() {
        gainPullPower = true;
    }

    public float getPullPower() {
        return pullPower;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (gainPullPower && pullPower < MAX_PULL_POWER) {
            float sum = pullPower + delta;
            pullPower = sum > MAX_PULL_POWER ? MAX_PULL_POWER : sum;
        }
    }
}
