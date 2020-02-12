package com.tboi.game.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.settings.GameControlSetting;

public class Bat extends Enemy {

    Array<TextureRegion> frames;
    Animation<TextureRegion> left, right, up, down;
    float statetime;
    public Bat(GameScreen screen, float x, float y) {
        super(screen, x, y);
        animation();
        statetime = 0;
        setBounds(getX(), getY(), 32/100f, 32/100f);
    }

    public void update(float delta) {
        statetime += delta;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 );
        setRegion(down.getKeyFrame(statetime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef def = new BodyDef();
        FixtureDef f = new FixtureDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(getX(), getY());
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4f/GameControlSetting.PPM, 4f/GameControlSetting.PPM);

        f.shape = shape;
        f.filter.categoryBits = CollisionSettings.ENEMY_BIT;
        f.filter.maskBits = CollisionSettings.WALL_BIT | CollisionSettings.MC_BIT;

        body.createFixture(f).setUserData(this);
        body.setLinearVelocity(vec);
    }

    private void animation() {
        frames = new Array<TextureRegion>();
        for(int i=1;i<4;i++)
            frames.add(new TextureRegion(screen.getBat().findRegion("32x32-bat-sprite"), i*32, 0, 32, 32));
            down = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    public void reduceHealth() {
        batLife--;
        if(batLife == 0 && !world.isLocked()) {
            world.destroyBody(body);
        }
    }
}
