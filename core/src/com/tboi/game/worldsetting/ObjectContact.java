package com.tboi.game.worldsetting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.terrain.Chest;
import com.tboi.game.entities.terrain.Door;
import com.tboi.game.entities.terrain.Lava;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.entities.collision.CollisionSettings.BOMB_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.CHEST_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.COIN_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.DESTROYED_LAVA_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.DOOR_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.ENEMY_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.KEY_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.MC_BIT;

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

        int collisionDef = a.getFilterData().categoryBits | b.getFilterData().categoryBits;

        switch (collisionDef) {
            case CHEST_BIT | MC_BIT:
                if(a.getFilterData().categoryBits == CHEST_BIT) {
                    ((Chest)a.getUserData()).onHeadHit();
                } else if (b.getFilterData().categoryBits == CHEST_BIT){
                    ((Chest)b.getUserData()).onHeadHit();
                }
                break;
            case DOOR_BIT | MC_BIT:
                if(a.getFilterData().categoryBits == DOOR_BIT) {
                    ((Door)a.getUserData()).teleport();
                } else if (b.getFilterData().categoryBits == DOOR_BIT) {
                    ((Door)b.getUserData()).teleport();
                }
                break;
            case DESTROYED_LAVA_BIT | MC_BIT:
                if(a.getFilterData().categoryBits == DESTROYED_LAVA_BIT) {
                    ((Lava)a.getUserData()).inflictDamage();
                } else if (b.getFilterData().categoryBits == DESTROYED_LAVA_BIT) {
                    ((Lava)b.getUserData()).inflictDamage();
                }
                break;
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
