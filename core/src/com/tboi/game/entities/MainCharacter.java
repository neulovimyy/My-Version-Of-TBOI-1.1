package com.tboi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tboi.game.TBOIGame;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.settings.DesktopSettings;

public class MainCharacter extends Sprite{

    World world;
    GameScreen game;
    public Body body;
    TextureAtlas isaac;

    public MainCharacter(GameScreen game){
        this.game = game;
        this.world = game.getWorld();
        isaac = new TextureAtlas(Gdx.files.internal("entities/isaac/isaac.atlas"));
        defineMC();
    }

    public Body defineMC() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(50, 90);

        body = world.createBody(def);
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        FixtureDef f = new FixtureDef();
        f.density = 0.5f;
        f.shape = shape;

        body.createFixture(f);
        return body;
    }

    @Override
    public void draw(Batch batch) {

    }
}
