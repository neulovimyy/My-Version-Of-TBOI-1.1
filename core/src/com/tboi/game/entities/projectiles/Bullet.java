package com.tboi.game.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.collision.CollisionSettings;

public abstract class Bullet extends Sprite {

    MainCharacter mc;
    Body body;
    World world;
    public Bullet(World world) {
        this.world = world;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(mc.body.getPosition().x, mc.body.getPosition().y);

        body = world.createBody(bdef);
        body.isBullet();
        CircleShape bullet = new CircleShape();
        bullet.setRadius(2f/100);

        fdef.shape = bullet;
        fdef.filter.categoryBits = CollisionSettings.BULLET_BIT;
        fdef.filter.maskBits = CollisionSettings.WALL_BIT | CollisionSettings.OBJECT_BIT;
        body.createFixture(fdef);
    }

    public void update(float delta) {
        switch (mc.getDirection()) {
            case LEFT:
                Gdx.app.log("Shoot", "Left");
                break;
            case RIGHT:
                Gdx.app.log("Shoot", "Right");
                break;
            case UP:
                Gdx.app.log("Shoot", "Up");
                break;
            case DOWN:
            case STEADY:
            default:
                Gdx.app.log("Shoot", "Down");
                break;
        }
    }

    public void destroyBullet() {
        world.destroyBody(body);
    }
}
