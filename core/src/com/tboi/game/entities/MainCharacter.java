package com.tboi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.terrain.Chest;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.settings.Conditions;
import com.tboi.game.settings.GameControlSetting;

public class MainCharacter extends Sprite{

    World world;
    GameScreen game;
    Animation<TextureRegion> up, down, left, right, steady;
    public static Body body;
    float duration = 1/60f;
    float timer;
    public float posx;
    public float posy;
    public final float height = 20, width = 20;

    TextureRegion region;

    public enum Direction {LEFT, RIGHT, DOWN, UP, STEADY}; //enums to determine direction
    public Direction currentState;
    public Direction previousState;
    boolean isTeleported;
    TiledMap map;
    public FixtureDef f;
    /**
     * Class for MC
     *
     */

    public MainCharacter(GameScreen game ){
        super(game.getIsaac().findRegion("isaac-walking"));
        this.game = game;
        this.map = game.getMap();
        this.world = game.getWorld();

        frames();

        defineMC();// defining body

        region = new TextureRegion(getTexture(), 0, 0, 96, 64);
        setBounds(0, 0, 96 / GameControlSetting.PPM2, 64 / GameControlSetting.PPM2);
        setRegion(region);
        timer = 0;
        currentState = Direction.STEADY;
        previousState = Direction.STEADY;

    }

    public void update(float delta) { //positioning the sprite to the body
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - 15/100f );
        setRegion(getFacade(delta));
    }

    public void defineMC() {
        BodyDef def = new BodyDef();
        f = new FixtureDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(176/GameControlSetting.PPM2, 124f/GameControlSetting.PPM2);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7f/GameControlSetting.PPM2, 3f/GameControlSetting.PPM2);

        f.shape = shape;
        f.filter.categoryBits = CollisionSettings.MC_BIT;
        f.filter.maskBits = CollisionSettings.WALL_BIT | CollisionSettings.DOOR_BIT | CollisionSettings.CHEST_BIT
                | CollisionSettings.ENEMY_BIT;
        body.createFixture(f);

        CircleShape head = new CircleShape();
        head.setPosition(new Vector2(0, 12f/GameControlSetting.PPM2));
        head.setRadius(10f/GameControlSetting.PPM2);
        f.shape = head;

        body.createFixture(f).setUserData(this);
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
        down = new Animation(0.1f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 0, 0, 96,  64));
        steady = new Animation<TextureRegion>(0.1f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 96, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 192, 64, 96,  64));;
        frame.add(new TextureRegion(getTexture(), 192, 0, 96,  64));
        left = new Animation<TextureRegion>(0.1f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 288, 64, 96,  64));
        frame.add(new TextureRegion(getTexture(), 288, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 384, 64, 96,  64));
        right = new Animation<TextureRegion>(0.1f, frame);
        frame.clear();

        frame.add(new TextureRegion(getTexture(), 384, 0, 96,  64));
        frame.add(new TextureRegion(getTexture(), 480, 64, 96,  64));
        frame.add(new TextureRegion(getTexture(), 480, 0, 96,  64));
        up = new Animation<TextureRegion>(0.1f, frame);
        frame.clear();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public float getTimer() {
        return timer;
    }
}
