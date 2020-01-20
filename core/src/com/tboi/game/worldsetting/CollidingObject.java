package com.tboi.game.worldsetting;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.screens.GameScreen;

public abstract class CollidingObject {

    protected World world;
    protected GameScreen screen;
    protected TiledMap map;
    protected MapObject object;
    protected Rectangle bounds;
    protected Fixture fixture;
    protected Body body;

    public CollidingObject (GameScreen screen, MapObject object) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.object = object;
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / 100f, (bounds.getY() + bounds.getHeight() / 2) / 100f);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / 100, bounds.getHeight() / 2 / 100);

        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }
    public abstract void onHeadHit();
    public abstract void onBodyHit();

    public void setFilter(short bit) {
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
}
