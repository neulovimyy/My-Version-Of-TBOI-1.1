package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.worldsetting.CollidingObject;

public class Lava extends CollidingObject {

    public final int LAVA = 251;
    public Lava(GameScreen game, MapObject object){
        super(game, object);
        object.setColor(Color.CORAL);
        fixture.setUserData(this);
        setFilter(CollisionSettings.DESTROYED_LAVA_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Lava", "Head");
        setFilter(CollisionSettings.DESTROYED_LAVA_BIT);
    }

    public void onBodyHit() {
        Gdx.app.log("Lava", "Body");
        setFilter(CollisionSettings.DESTROYED_BIT);
    }

    public void inflictDamage() {
        GameHUD.reduceHealth(1);
    }
}
