package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.tboi.game.TBOIGame;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.worldsetting.CollidingObject;

public class Lava extends CollidingObject {

    GameScreen game;
    TiledMap map;
    Rectangle bounds;

    public Lava(GameScreen game, MapObject object){
        super(game, object);
        fixture.setUserData(this);
        setFilter(CollisionSettings.LAVA_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Lava", "Head");
        setFilter(CollisionSettings.DESTROYED_BIT);
    }

    @Override
    public void onBodyHit() {
        Gdx.app.log("Lava", "Body");
        setFilter(CollisionSettings.DESTROYED_BIT);
    }
}
