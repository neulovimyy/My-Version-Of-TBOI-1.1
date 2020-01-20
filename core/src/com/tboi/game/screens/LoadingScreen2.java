package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tboi.game.TBOIGame;
import com.tboi.game.settings.GameControlSetting;
import com.tboi.game.tween.SpriteTween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class LoadingScreen2 implements Screen {

    TweenManager tweenManager;
    TBOIGame game;
    Sprite cat, paper, part, zid;

    public LoadingScreen2(TBOIGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        cat = new Sprite(new Texture("pictures/cat.jpg"));
        paper = new Sprite(new Texture("pictures/paper.jpg"));
        part = new Sprite(new Texture("pictures/participation.png"));
        zid = new Sprite(new Texture("pictures/zid.png"));
        tweenSetup();
    }

    private void tweenSetup() {

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteTween());

        Tween.set(part,SpriteTween.FADING).target(0).start(tweenManager);
        Tween.set(zid,SpriteTween.FADING).target(0).start(tweenManager);
        Tween.to(part, SpriteTween.FADING, 2).target(1).start(tweenManager);
        Tween.to(zid,SpriteTween.FADING,2).target(1).start(tweenManager);
        Tween.to(part,SpriteTween.FADING, 2).delay(2).target(0).start(tweenManager);
        Tween.to(zid,SpriteTween.FADING,2).delay(2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new TitleScreen(game));
            }
        }).start(tweenManager);

        tweenManager.update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void render(float delta) {

        paper.setSize(GameControlSetting.WIDTH, GameControlSetting.HEIGHT);
        part.setPosition(GameControlSetting.WIDTH/2 - part.getWidth() + 400, GameControlSetting.HEIGHT/2 - part.getHeight() + 100);
        zid.setPosition(GameControlSetting.WIDTH/2 - zid.getWidth(), GameControlSetting.HEIGHT/2 - zid.getHeight() - 100);

        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        paper.draw(game.batch);
        part.draw(game.batch);
        zid.draw(game.batch);
        game.batch.end();

        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        cat.getTexture().dispose();
        zid.getTexture().dispose();
        part.getTexture().dispose();
        paper.getTexture().dispose();
    }
}
