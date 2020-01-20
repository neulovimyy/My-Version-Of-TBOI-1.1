package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.worldsetting.CollidingObject;

public class Door extends CollidingObject {
    public Door(GameScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setFilter(CollisionSettings.DOOR_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Door", "Head");
        setFilter(CollisionSettings.DESTROYED_BIT);
    }

    @Override
    public void onBodyHit() {
        Gdx.app.log("Door", "Body");
        setFilter(CollisionSettings.DESTROYED_BIT);
    }
}
