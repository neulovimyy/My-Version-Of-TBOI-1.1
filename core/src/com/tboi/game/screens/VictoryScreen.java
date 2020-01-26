package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.entities.MainCharacter;

public class VictoryScreen implements Screen {

    FitViewport viewport;
    TBOIGame game;
    Skin skin;
    Stage stage;

    float count;
    Label label, label1;

    public VictoryScreen(TBOIGame game) {
        this.game = game;

        Label.LabelStyle style,style1,style2; style = new Label.LabelStyle(); style1 = new Label.LabelStyle(); style2 = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE; param.size = 70;
        style.font = gen.generateFont(param);
        param.size = 20;
        style1.font = gen.generateFont(param);

        label1 = new Label("Click to play again", style1);
        label = new Label("Victory", style);
        count = 0;
        viewport = new FitViewport(1280, 720, new OrthographicCamera());
        skin = new Skin(Gdx.files.internal("ui/neon/neon.json"), new TextureAtlas("ui/neon/neon.atlas"));
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);

        table.add(label).center(); table.row(); table.add(label1).expandX();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



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

    }
}
