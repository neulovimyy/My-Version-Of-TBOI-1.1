package com.tboi.game.worldsetting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.screens.GameScreen;

public class ObjectContact implements ContactListener {


    /**
     * Contact to all objects in the game
     */

    World world;
    GameScreen screen;

    public ObjectContact(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() == "head" || b.getUserData() == "head") {
            Fixture head = a.getUserData() == "head" ? a : b;
            Fixture obj = head == a ? b : a;
            if(obj.getUserData() != null && CollidingObject.class.isAssignableFrom(obj.getUserData().getClass())) {
                ((CollidingObject) obj.getUserData()).onHeadHit();
            }
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
