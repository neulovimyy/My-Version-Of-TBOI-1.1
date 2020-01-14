package com.tboi.game.worldsetting;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.screens.GameScreen;

public class WorldSetting {

    GameScreen screen;

    World world;
    BodyDef def = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    TiledMap map;
    Body body;


    public WorldSetting(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getTiledMap();
        defineWorldCollision();
    }

    private void defineWorldCollision() {
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject .class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            def.type = BodyDef.BodyType.StaticBody;
            def.position.set((rect.getX() + rect.width/2)/100, (rect.getY() + rect.height/2)/100);

            body = world.createBody(def);

            shape.setAsBox(rect.width/2/100 , rect.height/2/100);
            fdef.shape = shape;

            body.createFixture(fdef);
        }

    }

}
