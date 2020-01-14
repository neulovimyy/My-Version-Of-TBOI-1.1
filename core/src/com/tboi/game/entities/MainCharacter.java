package com.tboi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.TBOIGame;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.settings.DesktopSettings;
import com.tboi.game.settings.GameControlSetting;

import java.util.ArrayList;

public class MainCharacter extends Sprite{

    World world;
    GameScreen game;
    Animation<TextureRegion> up, down, left, right, steady;
    public Body body;
    float duration = 1/60f;
    float timer;

    public enum Direction {LEFT, RIGHT, DOWN, UP, STEADY}; //enums to determine direction
    public Direction currentState;
    public Direction previousState;

    /**
     * Class for MC
     *
     */


    public MainCharacter(GameScreen game, World world ){
        super(game.getIsaac().findRegion("isaac-walking"));

        this.game = game;
        this.world = world;

        frames();

        defineMC();// defining body

        setBounds(0,0, 96*3, 64*3);

        timer = 0;
        currentState = Direction.STEADY;
        previousState = Direction.STEADY;
    }

    public void update(float delta) { //positioning the sprite to the body
        setRegion(getFacade(delta));
        setPosition(496, 315);
    }

    public void defineMC() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(100/100f, 100/100f);

        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7f/100, 3f/100);

        FixtureDef f = new FixtureDef();
        f.shape = shape;
        body.createFixture(f).setUserData("body");

        CircleShape head = new CircleShape();
        head.setPosition(new Vector2(0, 12f/100));
        head.setRadius(10f/100);
        f.shape = head;

        body.createFixture(f).setUserData("head");
        /*EdgeShape head = new EdgeShape();
        head.set(new Vector2(1, 5), new Vector2(9, 9));*/
    }

    //method to get the direction Isaac is facing
    public Direction getDirection() {
            if (body.getLinearVelocity().y > 0 || ((body.getLinearVelocity().y > 0 && body.getLinearVelocity().x > 0) ||
                    (body.getLinearVelocity().y > 0 && body.getLinearVelocity().x < 0))) {
                return Direction.UP;  //up

            } else if (body.getLinearVelocity().y < 0 || ((body.getLinearVelocity().y < 0 && body.getLinearVelocity().x > 0) ||
                    (body.getLinearVelocity().y < 0 && body.getLinearVelocity().x < 0))){
                return Direction.DOWN;//down

            } else if (body.getLinearVelocity().x < 0 || ((body.getLinearVelocity().y > 0 && body.getLinearVelocity().x < 0) ||
                    (body.getLinearVelocity().x < 0 && body.getLinearVelocity().y < 0))) {
                return Direction.LEFT;//left

            } else if (body.getLinearVelocity().x > 0 || ((body.getLinearVelocity().y > 0 && body.getLinearVelocity().x > 0) ||
                    (body.getLinearVelocity().x > 0 && body.getLinearVelocity().y < 0))) {
                return Direction.RIGHT;//right

            } else {
                return Direction.STEADY;//steady
            }
    }

    public TextureRegion getFacade(float delta) {
        currentState = getDirection();

        TextureRegion region;

        switch (currentState){
            case DOWN:
                region = down.getKeyFrame(timer, true);
                break;
            case LEFT:
                region = left.getKeyFrame(timer, true);
                break;
            case RIGHT:
                region = right.getKeyFrame(timer, true);
                break;
            case UP:
                region = up.getKeyFrame(timer, true);
                break;
            default:
                region = steady.getKeyFrame(timer);
                break;
        }

        timer = currentState == previousState ? timer + delta : 0;
        previousState = currentState;

        return region;
    }

    public void frames() {
        Array<TextureRegion> frame = new Array<TextureRegion>();
        frame.add(new TextureRegion(getTexture(), 0, 64, 96,  64));
        frame.add(new TextureRegion(getTexture(), 0, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 96, 64, 96,  64));
        down = new Animation(0.3f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 0, 0, 96,  64));
        steady = new Animation<TextureRegion>(0.5f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 96, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 192, 64, 96,  64));;
        frame.add(new TextureRegion(getTexture(), 192, 0, 96,  64));
        left = new Animation<TextureRegion>(0.3f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 288, 64, 96,  64));
        frame.add(new TextureRegion(getTexture(), 288, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 384, 64, 96,  64));
        right = new Animation<TextureRegion>(0.3f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 384, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 480, 64, 96,  64));
        frame.add(new TextureRegion(getTexture(), 480, 0, 96,  64));
        up = new Animation<TextureRegion>(0.3f, frame);
        frame.clear();
    }
}