package com.tboi.game.entities.hud;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.settings.GameControlSetting;

import java.util.ArrayList;

public class GameHUD implements Disposable {

    private static int health;
    public Stage stage;
    Viewport viewport;
    TBOIGame game;
    OrthographicCamera camera;
    Skin skin;
    private Touchpad move, attack;
    public float count;
    public static Integer mcScore;
    public Integer worldCount;
    public Integer minute;
    public Integer hour;
    public Integer second;
    public Integer key;
    public Integer bomb;
    public Integer coin;
    String hr,min,sec;
    ArrayList<Image> goldHearts, redHearts, boneHeart;
    Image coinHeart, redHeart;
    Label countUpTimer;
    static Label score;
    Label keyCount;
    Label coinCount;
    Label bombCount;
    Label floor;
    Table heartTable;

    static Label hitpoints;

    public static boolean isDead;

    public GameHUD (SpriteBatch batch) {
        worldCount = 0; minute = 0; hour = 0; second = 0; health = 10;
        min = null; sec = null; key = 0; bomb = 0; coin = 0;
        mcScore = 0;
        viewport = new FitViewport(GameControlSetting.WIDTH, GameControlSetting.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        isDead = false;

        skin = new Skin(Gdx.files.internal("ui/classic/classic-ui.json"), new TextureAtlas("ui/classic/classic-ui.atlas"));
        Table entities = new Table(skin);
        entities.left();
        entities.setFillParent(true);

        //styles
        Label.LabelStyle style,style1,style2; style = new Label.LabelStyle(); style1 = new Label.LabelStyle(); style2 = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE; param.size = 35;
        style.font = gen.generateFont(param);
        param.color = Color.WHITE; param.size = 60;
        style1.font = gen.generateFont(param);
        param.color = Color.LIGHT_GRAY; param.size = 40;
        style2.font = gen.generateFont(param);

        Sprite red = new Sprite(new Texture("entities/heart/images/red-heart-full.png"));
        Sprite coin = new Sprite(new Texture("entities/heart/images/coin-heart.png"));
        red.setSize(64,64); coin.setSize(64, 64);
        redHeart = new Image(); coinHeart = new Image();
        redHeart.setDrawable(new SpriteDrawable(red)); coinHeart.setDrawable(new SpriteDrawable(coin));

        redHearts = new ArrayList<Image>();
        redHearts.add(0, redHeart);
        redHearts.add(1, coinHeart);

        heartTable = new Table();
        heartTable.top().left();
        heartTable.setFillParent(true);
        for (int i=0; i<redHearts.size();i++) {
            heartTable.add(redHearts.get(i)).padRight(i*15);
        }

        Table floorLabel = new Table(skin);
        floorLabel.top().right();
        floorLabel.setFillParent(true);

        Table time = new Table(skin);
        time.setFillParent(true);
        time.center().top();

        score = new Label(String.format("%05d", mcScore), style1);
        floor = new Label("Floor 1-1", style);
        countUpTimer = new Label("", style1);

        time.add(countUpTimer).expandX().padTop(10); //add time and score up center
        time.row(); time.add(score).expandX(); time.row();
        stage.addActor(time); //add time and count-up timer to stage

        floorLabel.add(floor).padRight(120).padTop(10);//add level to top right
        stage.addActor(floorLabel);

        hitpoints = new Label("Hitpoints :"+health, style2);

        entities.add(hitpoints); stage.addActor(entities);

        move = new Touchpad(0, skin, "default");
        move.setColor(Color.BLUE);
        move.setBounds(50, 50, 200, 200);
        stage.addActor(move);

        attack = new Touchpad(0, skin, "default");
        attack.setColor(Color.RED);
        attack.setBounds(1030, 50, 200, 200);
        stage.addActor(attack);

        ImageTextButton button = new ImageTextButton("Bomb", skin);
        button.setBounds(880, 50, 100, 100);
        stage.addActor(button);

        ImageTextButton switchItem = new ImageTextButton("O", skin);
        switchItem.setBounds(300, 50, 100, 100);
        stage.addActor(switchItem);

        ImageTextButton pause = new ImageTextButton("||", skin);
        pause.setColor(Color.CORAL);
        pause.setBounds(1150, 630, 50, 50);
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            button.setColor(button.getColor().r, button.getColor().g, button.getColor().b, 1);
            switchItem.setColor(switchItem.getColor().r, switchItem.getColor().g, switchItem.getColor().b, 1);
            pause.setColor(pause.getColor().r, pause.getColor().g, pause.getColor().b, 1);
        } else {
            button.setColor(button.getColor().r, button.getColor().g, button.getColor().b, 0);
            switchItem.setColor(switchItem.getColor().r, switchItem.getColor().g, switchItem.getColor().b, 0);
            pause.setColor(pause.getColor().r, pause.getColor().g, pause.getColor().b, 0);
        }
        stage.addActor(pause);

        Gdx.input.setInputProcessor(stage);
    }

    public static void reduceHealth(int i) {
        /**
         * this method reduces player health when engaging to spikes and enemies
         */
        health -= i;
        if(health == 0) { //check whether to play dead sound
            isDead = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/dead.wav"));
            sound.setLooping(1, false);
            sound.setPitch(1, 0.5f);
            sound.setVolume(1,0.5f);
            //sound.play();
        } else { //else play hurt
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/hurt.wav"));
            sound.setLooping(1, false);
            sound.setPitch(1, 0.5f);
            sound.setVolume(1,0.5f);
            //sound.play();
        }

        hitpoints.setText("Hitpoints: "+health);

    }

    public static void addHealth(int i){
        health += i;
        hitpoints.setText("Hitpoints: "+health);
    }

    public static void addScore(int i) {
        mcScore += i;
        if(mcScore % 2500 == 0 && health > 0) {
            addHealth(1);
        }
        score.setText(String.format("%05d", mcScore));
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

    public void update(float delta) { //timer update
        count += delta;
        if(count >= 1){
            second++;

            if(second%2 == 0) {
                //reduceHealth(1);
            }
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

    public static boolean isIsDead() {
        return isDead;
    }
}
