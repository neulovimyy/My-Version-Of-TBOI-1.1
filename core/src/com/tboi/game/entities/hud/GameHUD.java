package com.tboi.game.entities.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.settings.GameControlSetting;

public class GameHUD implements Disposable {

    public Stage stage;
    Viewport viewport;
    TBOIGame game;
    OrthographicCamera camera;
    Skin skin;
    private Touchpad move, attack;
    public float count;
    public Integer mcScore, worldCount, minute, hour, second;
    String hr,min,sec;

    Label countUpTimer, score, keyCount, cointCount, bombCount, floor;
    Label damage, AS, projSpeed, movement, luck;

    public GameHUD (SpriteBatch batch) {
        worldCount = 0; minute = 0; hour = 0; second = 0;
        min = null; sec = null;
        mcScore = 0;
        viewport = new FitViewport(GameControlSetting.WIDTH, GameControlSetting.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        skin = new Skin(Gdx.files.internal("ui/classic/classic-ui.json"), new TextureAtlas("ui/classic/classic-ui.atlas"));
        Table entities = new Table(skin);
        entities.left();
        entities.setFillParent(true);

        Table floorLabel = new Table(skin);
        floorLabel.top().right();
        floorLabel.setFillParent(true);

        Table time = new Table(skin);
        time.setFillParent(true);
        time.center().top();

        score = new Label(String.format("%05d", mcScore), skin);
        floor = new Label("Floor 1-1", skin);
        countUpTimer = new Label("", skin);

        time.add(countUpTimer).expandX().padTop(10); //add time and score up center
        time.row(); time.add(score).expandX();

        stage.addActor(time); //add time and count-up timer to stage

        floorLabel.add(floor).padRight(120).padTop(10);//add level to top right
        stage.addActor(floorLabel);

        luck = new Label("Luck: 10%", skin); luck.setColor(Color.WHITE);
        damage = new Label("Damage: 5", skin); damage.setColor(Color.WHITE);
        movement = new Label("Movement: 5",skin); movement.setColor(Color.WHITE);
        entities.add(luck).padLeft(30).padTop(-20); entities.row(); entities.add(damage).padLeft(30); entities.row(); entities.add(movement).padLeft(30);
        stage.addActor(entities);

        move = new Touchpad(0, skin, "default");
        move.setBounds(50, 50, 200, 200);
        stage.addActor(move);

        attack = new Touchpad(0, skin, "default");
        attack.setBounds(1030, 50, 200, 200);
        stage.addActor(attack);

        ImageTextButton button = new ImageTextButton("Bomb", skin);
        button.setBounds(880, 50, 100, 100);
        stage.addActor(button);

        ImageTextButton switchItem = new ImageTextButton("O", skin);
        switchItem.setBounds(300, 50, 100, 100);
        stage.addActor(switchItem);

        ImageTextButton pause = new ImageTextButton("||", skin);
        pause.setBounds(1150, 630, 50, 50);
        stage.addActor(pause);

        Gdx.input.setInputProcessor(stage);
    }

    public Touchpad getAttack() {
        return attack;
    }
    public Touchpad getMove() {
        return move;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float delta) { //crude timer without separator
        count += delta;
        if(count >= 1){
            second++;
            if(second == 60) {
                minute++;
                second = 0;
                if(minute == 60) {
                    hour = 1;
                    minute = 0;
                }
            }
            if(second<10) {
                sec = "0"+second.toString();
            } else {
                sec = second.toString();
            }
            if(minute<10) {
                min = "0"+minute.toString();
            } else {
                min = minute.toString();
            }
            countUpTimer.setText("Time: " +hour+":"+min+":"+sec);
            count = 0;
        }
    }
}
