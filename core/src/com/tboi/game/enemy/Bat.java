package com.tboi.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
        body.setLinearVelocity(vec);
    }

    @Override
    protected void defineEnemy() {
        BodyDef def = new BodyDef();
        FixtureDef f = new FixtureDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(getX(), getY());
        body = world.createBody(def);
        body.setLinearVelocity(new Vector2(2,0));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4f/GameControlSetting.PPM2, 4f/GameControlSetting.PPM2);

        f.shape = shape;
        f.filter.categoryBits = CollisionSettings.ENEMY_BIT;
        f.filter.maskBits = CollisionSettings.WALL_BIT | CollisionSettings.DOOR_BIT |
                            CollisionSettings.CHEST_BIT | CollisionSettings.MC_BIT;
        body.createFixture(f).setUserData(this);
    }

    @Override
    public void flipDirection(boolean x, boolean y) {
        if(x)
            vec.x = -vec.x;
        if(y)
            vec.y = -vec.y;
    }

    private void animation() {
        frames = new Array<TextureRegion>();
        for(int i=1;i<4;i++)
            frames.add(new TextureRegion(screen.getBat().findRegion("32x32-bat-sprite"), i*32, 0, 32, 32));
            down = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }
}
