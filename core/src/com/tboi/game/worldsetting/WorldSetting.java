package com.tboi.game.worldsetting;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.entities.collision.CollisionSettings.WALL_BIT;
import static com.tboi.game.settings.GameControlSetting.PPM;

public class WorldSetting{

    GameScreen screen;

    World world;
    TiledMap map;
    Body body;


    public WorldSetting(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        defineWorldCollision();
    }

    private void defineWorldCollision() {

        int count = map.getLayers().getCount(), tileCount = 3;
        Shape shape;
        for (MapObject object : map.getLayers().get(5).getObjects()) { //wall
            if (object instanceof RectangleMapObject) {
                shape = createRect((RectangleMapObject) object);
                Rectangle bounds = ((RectangleMapObject) object).getRectangle();
                BodyDef def = new BodyDef();
                FixtureDef fdef = new FixtureDef();

                def.type = BodyDef.BodyType.StaticBody;
                def.position.set((bounds.getX() + bounds.getWidth() / 2) / PPM, (bounds.getY() + bounds.getHeight() / 2 )/ PPM);
                body = world.createBody(def);

                fdef.shape = shape;
                fdef.filter.categoryBits = WALL_BIT;
                body.createFixture(shape, 1.0f).setUserData(this);
                shape.dispose();
            } else {
                continue;
            }

        }


    }

    private PolygonShape createRect(RectangleMapObject object) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox((object.getRectangle().getWidth() / 2 )/ PPM,
                (object.getRectangle().getHeight() / 2 )/ PPM);
        return ps;
    }


    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / 100f, vertices[i * 2 + 1] / 100f);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }


}
