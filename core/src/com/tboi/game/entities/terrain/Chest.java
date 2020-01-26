package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.worldsetting.CollidingObject;

public class Chest extends CollidingObject {

    public TiledMapTileSet newChest;

    public final int CHEST = 561;
    public Chest(GameScreen game, MapObject object){
        super(game, object);
        newChest = map.getTileSets().getTileSet("Floors");
        fixture.setUserData(this);
        setFilter(CollisionSettings.CHEST_BIT);
    }

    @Override
    public void onHeadHit() {
        setFilter(CollisionSettings.DESTROYED_BIT);
        thirdLayer().setTile(newChest.getTile(CHEST));
        Gdx.app.log("Chest", "Head");
        GameHUD.addScore(500);
    }

    @Override
    public void onBodyHit() {
        setFilter(CollisionSettings.DESTROYED_BIT);
        thirdLayer().setTile(newChest.getTile(CHEST));
        Gdx.app.log("Chest", "Body");
        GameHUD.addScore(500);
    }
}
