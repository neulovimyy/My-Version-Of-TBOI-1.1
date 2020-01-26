package com.tboi.game.entities.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.tboi.game.TBOIGame;
import com.tboi.game.screens.GameScreen;

public abstract class Level extends GameScreen {
    public Level(TBOIGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void update(float delta){
        super.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }

}
