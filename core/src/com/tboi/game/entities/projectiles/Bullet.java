package com.tboi.game.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.screens.GameScreen;

import java.util.ArrayList;

import javax.xml.soap.Text;

import static com.tboi.game.settings.GameControlSetting.PPM;

/**
 * Created by brentaureli on 10/12/15.
 */
public class Bullet extends Sprite {

    GameScreen screen;
    Animation<TextureRegion> bullets;
    Boolean isRight, isUp, destroyed = false, setToDestroy = false;
    World world;
    Array<TextureRegion> frame;

    Body body; float statetime = 0, posx, posy, vecx, vecy;
    public Bullet(GameScreen gs, Boolean isRight, Boolean isUp, float x, float y) {
        this.screen = gs;
        this.isRight = isRight; this.isUp = isUp;
        this.world = gs.getWorld();

        frame = new Array<TextureRegion>();
        frame.add(new TextureRegion(screen.getTears().findRegion("silvertear"), 0, 0, 68, 69));

        bullets = new Animation<TextureRegion>(0.2f, frame);
        setRegion(bullets.getKeyFrame(0, true));

        setBounds(x, y, 80/PPM, 80/PPM);
        defineBullet();
    }

    private void defineBullet() {
        BodyDef bdef = new BodyDef();
        if(isRight == null) {
            posx = getX();
            vecx = 0;
        } else if(isRight == true) {
            posx = getX() + 1 / PPM;
            vecx = 2;
        } else {
            posx = getX() - 1 / PPM;
            vecx = -2;
        }

        if(isUp == null) {
            posy = getY();
            vecy = getY();
        } else if (isUp == true) {
            posy = getY() + 1 / PPM;
            vecy = 2;
        } else {
            posy = getY() - 1 / PPM;
            vecy = -2;
        }

        bdef.position.set(posx, posy);
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked()) {
            body = world.createBody(bdef);

        }
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/PPM);
        fdef.shape = shape;
        fdef.friction = 0;
        fdef.density = 1f;
        fdef.restitution = 0.5f;
        body.createFixture(fdef);
        body.setLinearVelocity(vecx, vecy);
    }

    public void update(float delta) {
        statetime += delta;
        setRegion(bullets.getKeyFrame(statetime, false));
        setPosition(body.getPosition().x - getWidth() + 9/100f, body.getPosition().y - getHeight() / 2 + 12/100f);
        if((statetime > 3 || setToDestroy ) && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            body = null;
        }
    }

    public void destroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
