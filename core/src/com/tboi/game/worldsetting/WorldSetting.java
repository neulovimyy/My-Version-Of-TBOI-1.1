package com.tboi.game.worldsetting;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.terrain.Chest;
import com.tboi.game.entities.terrain.Door;
import com.tboi.game.entities.terrain.Lava;
import com.tboi.game.screens.GameScreen;

public class WorldSetting{

    GameScreen screen;

    World world;
    BodyDef def = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    TiledMap map;
    Body body;
    SpriteBatch batch;
    ParticleEffect pe;

    public WorldSetting(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.batch = screen.game.batch;
        this.pe = screen.getPe();
        defineWorldCollision();
    }

    private void defineWorldCollision() {

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject .class)){ //ground/wall
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            def.type = BodyDef.BodyType.StaticBody;
            def.position.set((rect.getX() + rect.width/2)/100, (rect.getY() + rect.height/2)/100);

            body = world.createBody(def);
            fdef.filter.categoryBits = CollisionSettings.WALL_BIT;
            shape.setAsBox(rect.width/2/100 , rect.height/2/100);
            fdef.shape = shape;

            body.createFixture(fdef);
        }

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) { //door
            new Door(screen, object);
        }

        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){ //lava
            new Lava(screen, object);
        }

        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){ //lava
            new Chest(screen, object);
        }

    }

}
