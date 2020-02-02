package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.settings.Conditions;
import com.tboi.game.worldsetting.CollidingObject;

import java.util.concurrent.locks.Condition;

public class Chest extends CollidingObject {

    public TiledMapTileSet newChest;
    MainCharacter mc;
    GameScreen game;
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
        GameHUD.addScore(500);
    }
}
