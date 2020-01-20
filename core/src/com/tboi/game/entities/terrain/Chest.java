package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.worldsetting.CollidingObject;

public class Chest extends CollidingObject {

    public Chest(GameScreen game, MapObject object){
        super(game, object);
        fixture.setUserData(this);
        setFilter(CollisionSettings.CHEST_BIT);
    }

    @Override
    public void onHeadHit() {
        setFilter(CollisionSettings.DESTROYED_BIT);
        Gdx.app.log("Chest", "Head");
    }

    @Override
    public void onBodyHit() {
        setFilter(CollisionSettings.DESTROYED_BIT);
        Gdx.app.log("Chest", "Head");
    }
}
