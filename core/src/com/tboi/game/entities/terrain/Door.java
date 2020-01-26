package com.tboi.game.entities.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.TBOIGame;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.screens.VictoryScreen;
import com.tboi.game.worldsetting.CollidingObject;

public class Door extends CollidingObject {

    GameScreen screen;
    World world;
    boolean triggered;
    TBOIGame game;

    public Door(GameScreen screen, MapObject object) {
        super(screen, object);
        game = screen.game;
        this.screen = screen;
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

    public void teleport() {
        game.setScreen(new VictoryScreen(game));
    }
}
