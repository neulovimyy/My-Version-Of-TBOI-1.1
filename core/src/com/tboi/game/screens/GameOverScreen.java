package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.entities.MainCharacter;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class GameOverScreen implements Screen {

    FitViewport viewport;
    TBOIGame game;
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    public GameOverScreen(final TBOIGame game) {
        this.game = game;
        this.batch = game.batch;
        viewport = new FitViewport(1280, 720, new OrthographicCamera());
        skin = new Skin(Gdx.files.internal("ui/classic/classic-ui.json"), new TextureAtlas("ui/classic/classic-ui.atlas"));
        stage = new Stage(viewport, batch);

        Label.LabelStyle style,style1,style2; style = new Label.LabelStyle(); style1 = new Label.LabelStyle(); style2 = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE; param.size = 70;
        style.font = gen.generateFont(param);
        param.size = 20;
        style1.font = gen.generateFont(param);

        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("Game Over", style);
        Label label1 = new Label("Play again", style1);
        table.add(label).center().expandX(); table.row();
        table.add(label1).center().expandX();
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
            game.setScreen(new GameScreen(game));
        }

        stage.draw();
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
        stage.dispose();
    }
}
