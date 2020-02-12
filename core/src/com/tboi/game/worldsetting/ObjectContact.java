package com.tboi.game.worldsetting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.enemy.Bat;
import com.tboi.game.enemy.Enemy;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.entities.collision.CollisionSettings.BOMB_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.CHEST_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.COIN_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.DOOR_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.ENEMY_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.KEY_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.LAVA_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.MC_BIT;
import static com.tboi.game.entities.collision.CollisionSettings.WALL_BIT;

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
            case  MC_BIT | WALL_BIT :
                if(a.getFilterData().categoryBits == MC_BIT) {
                    Gdx.app.log("MC", "Collided");
                } else if (b.getFilterData().categoryBits == MC_BIT) {
                    Gdx.app.log("MC", "Collided");
                }
                break;
            case  MC_BIT | ENEMY_BIT :
                if(a.getFilterData().categoryBits == LAVA_BIT) {
                    Gdx.app.log("Bat", "Collided");

                } else if (b.getFilterData().categoryBits == LAVA_BIT) {
                    Gdx.app.log("Bat", "Collided");

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
