package com.tboi.game.entities.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.tboi.game.TBOIGame;
import com.tboi.game.ai.SteeringEntity;
import com.tboi.game.worldsetting.WorldSetting;

public class Level1 extends Level {

    WorldSetting setting;
    SteeringEntity mainChar;

    public Level1(TBOIGame game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        map = new TmxMapLoader().load("map/maps/dungeon1-1.tmx"); //map
        renderer.setMap(map);
        setting = new WorldSetting(this);
        mainChar = new SteeringEntity(mc.body, 10);
    }

}
