package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tboi.game.TBOIGame;
import com.tboi.game.settings.DesktopSettings;
import com.tboi.game.settings.MobileSettings;
import com.tboi.game.tween.SpriteTween;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TitleScreen implements Screen {

    TBOIGame game;
    Sprite floatingTitle;
    Sprite bg;

    TweenManager tweenManager;

    Skin skin;
    Stage stage;
    Touchpad attack;
    Touchpad move;

    public TitleScreen(TBOIGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        bg = new Sprite(new Texture("pictures/titlescreen.jpg"));
        floatingTitle = new Sprite(new Texture("pictures/tboi-title-screen.png"));

        bg.setSize(DesktopSettings.WIDTH, DesktopSettings.HEIGHT);
        floatingTitle.setBounds(240, DesktopSettings.HEIGHT/2 + 150, 800,200);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/classic/classic-ui.json"), new TextureAtlas("ui/classic/classic-ui.atlas"));

        ImageTextButton play = new ImageTextButton("Hello",skin, MobileSettings.STYLENAME);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.music.stop();
                game.music.dispose();
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        Music music;
                        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/revel.mp3"));
                        music.setVolume(0.2f);
                        music.setLooping(true);
                        music.play();
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
                    }
                })));
            }
        });
        play.setPosition(DesktopSettings.WIDTH/2 - play.getWidth(), DesktopSettings.HEIGHT/2 + play.getHeight());
        stage.addActor(play);

        Gdx.input.setInputProcessor(stage);
        tweenSetup();
    }

    private void tweenSetup() {
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteTween());

        Tween.set(bg,SpriteTween.FADING).target(0).start(tweenManager);
        Tween.to(bg,SpriteTween.FADING, 2).target(2).start(tweenManager);

        Tween.call(windCallback).start(tweenManager);
        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        bg.draw(game.batch);
        floatingTitle.draw(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();

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
        game.batch.end();
        bg.getTexture().dispose();
        floatingTitle.getTexture().dispose();
    }

    private final TweenCallback windCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            float d = MathUtils.random(5f, 10f) * 0.5f + 0.5f;   // duration
            float t = 1.1f * floatingTitle.getHeight()*2 - 4f ;    // amplitude

            Tween.to(floatingTitle, SpriteTween.FLOAT, d)
                    .target(t, t)
                    .ease(Sine.INOUT)
                    .repeatYoyo(1, 0)
                    .setCallback(windCallback)
                    .start(tweenManager);
        }
    };
}
