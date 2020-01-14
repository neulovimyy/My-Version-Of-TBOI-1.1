
package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tboi.game.TBOIGame;
import com.tboi.game.settings.DesktopSettings;
import com.tboi.game.tween.SpriteTween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class LoadingScreen implements Screen {

    TweenManager tweenManager;
    TBOIGame game;
    Sprite present, author, paper, part, zid;

    public LoadingScreen(TBOIGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        present = new Sprite(new Texture("pictures/presents.png"));
        author = new Sprite(new Texture("pictures/author.png"));
        paper = new Sprite(new Texture("pictures/paper.jpg"));
        tweenSetup();
    }

    private void tweenSetup() {
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteTween());

        Tween.set(author,SpriteTween.FADING).target(0).start(tweenManager);
        Tween.set(present,SpriteTween.FADING).target(0).start(tweenManager);
        Tween.to(author,SpriteTween.FADING,2).target(1).start(tweenManager);
        Tween.to(present,SpriteTween.FADING, 2).target(1).start(tweenManager);

        Tween.to(author,SpriteTween.FADING, 2).delay(2).target(0).start(tweenManager);
        Tween.to(present,SpriteTween.FADING,2).delay(2).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LoadingScreen2(game));
            }
        }).start(tweenManager);

    }

    @Override
    public void render(float delta) {

        tweenManager.update(delta);

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        author.setPosition(DesktopSettings.WIDTH/2 - 100, DesktopSettings.HEIGHT/2 - author.getHeight() + 100);
        present.setPosition(DesktopSettings.WIDTH/2 - present.getWidth() + 100, DesktopSettings.HEIGHT/2 - (present.getHeight() + 100) + 150);
        paper.setSize(DesktopSettings.WIDTH, DesktopSettings.HEIGHT);

        game.batch.begin();
        paper.draw(game.batch);
        author.draw(game.batch);
        present.draw(game.batch);
        game.batch.end();

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
        author.getTexture().dispose();
        present.getTexture().dispose();
    }
}
