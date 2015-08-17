package com.broodproduct.krot.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.krot.objects.DestroBox;
import com.broodproduct.krot.objects.Krot;

public class KrotContactListener implements ContactListener {

    private DestroBox destroBox = null;
    private Krot krot = null;
    private Object unknown = null;

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        Object obj1 = bodyA.getUserData();
        Object obj2 = bodyB.getUserData();
        if (obj1 != null && obj2 != null) {
            getObj(obj1, obj2);
            if (krot != null && destroBox != null) {
                destroBox.setNeedToDestroy(true);
                krot.acceptDamage(100);
            } else if (destroBox != null && unknown != null && "ground".equals(unknown.toString())) {
                destroBox.setNeedToDestroy(true);

            } else if (krot != null && unknown != null &&("rack".equals(unknown.toString()) || "rightWall".equals(unknown.toString())))
                krot.changeDirection();

            destroBox = null;
            krot = null;
            unknown = null;
        }
    }

    private void getObj(Object... obj) {
        for (Object o : obj) {
            if (o instanceof Krot)
                this.krot = (Krot) o;
            else if (o instanceof DestroBox)
                this.destroBox = (DestroBox) o;
            else
                unknown = o;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
