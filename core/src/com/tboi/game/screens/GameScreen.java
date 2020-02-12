package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.ai.SteeringEntity;
import com.tboi.game.enemy.Bat;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.settings.GameControlSetting;
import com.tboi.game.worldsetting.ObjectContact;
import com.tboi.game.worldsetting.WorldSetting;

import box2dLight.ChainLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import static com.tboi.game.settings.GameControlSetting.HALF_HEIGHT;
import static com.tboi.game.settings.GameControlSetting.HALF_WIDTH;
import static com.tboi.game.settings.GameControlSetting.PPM;
import static com.tboi.game.settings.GameControlSetting.ROOM_HEIGHT;
import static com.tboi.game.settings.GameControlSetting.ROOM_WIDTH;

public class GameScreen implements Screen {

    private TextureAtlas isaac, bat, tears;
    private Vector2 target;
    Bat batclass;
    public TiledMap map;
    protected World world;
    public TBOIGame game;
    public OrthographicCamera gameCam;
    public Viewport viewport;
    public OrthogonalTiledMapRenderer renderer;
    GameHUD hud;
    GameControlSetting control;
    Box2DDebugRenderer b2dr;
    WorldSetting setting;
    public MainCharacter mc;
    RayHandler handler;
    ParticleEffect pe;
    PointLight light; ChainLight chain;
    SteeringEntity mainChar, enemyBat;
    int camval;



    public GameScreen(TBOIGame game){
        this.game = game;
        gameCam = new OrthographicCamera();

        viewport = new FitViewport(416 / GameControlSetting.PPM, 312 / GameControlSetting.PPM, gameCam);
        world = new World(new Vector2(0, 0), true);

        isaac = new TextureAtlas("entities/isaac/tr.pack");
        bat = new TextureAtlas("entities/bat/bat.atlas");
        tears = new TextureAtlas("entities/proj/bullet.txt");
        mc = new MainCharacter(this);

        map = new TmxMapLoader().load("map/levels/basement.tmx"); //map
        setting = new WorldSetting(this);
        mainChar = new SteeringEntity(mc.body, 10);
        batclass = new Bat(this, 1, 1);
        enemyBat = new SteeringEntity(batclass.body, 10);

        hud = new GameHUD(game.batch);

        renderer = new OrthogonalTiledMapRenderer(map, 1f/100);

        gameCam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        b2dr = new Box2DDebugRenderer();

        handler = new RayHandler(world);
        handler.setAmbientLight(1f);
        light = new PointLight(handler, 100, Color.BLACK, 32/100f, 0 ,0);
        light.setSoftnessLength(0.5f);
        //chain = new ChainLight(handler, 100, Color.CORAL, 32/100f, 20);
        world.setContactListener(new ObjectContact(this));
        control = new GameControlSetting();
        //batclass.body.setActive(false);
        Arrive<Vector2> arrive = new Arrive<Vector2>(enemyBat, mainChar);
        arrive.setDecelerationRadius(10f);
        arrive.setArrivalTolerance(0.1f);
        arrive.setTimeToTarget(0.1f);

        enemyBat.setsBeve(arrive);

        target = new Vector2(0, 0);
    }

    @Override
    public void show() {
    }

    public void update(float delta) {

        control.platformControlConfig(mc, hud.getMove(), hud.getAttack()); //controls
        world.step(1/60f, 6, 2); //draw world
        mc.update(delta); batclass.update(delta); hud.update(delta); //mc, hud, lights
        enemyBat.update(delta);
        //pe.getEmitters().first().setPosition(mc.posx, mc.posy);
        //pe.start();
        gameCam.position.set(mc.body.getPosition().x, mc.body.getPosition().y, 0);
        gameCam.update();

        renderer.setView(gameCam);
        light.setPosition(mc.body.getPosition().x, mc.body.getPosition().y);
        light.update();

    }

    @Override
    public void render(float delta) {

        /**
         * rendering order:
         * update - cls - map/world - world/map - sprite - lights - hud
         * map/world -> to debug body positioning
         * world/map -> to debug sprite positioning
         */

        update(delta); //okay to jumble methods here iff no dependency to other classes detected

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //handler.setCombinedMatrix(gameCam.combined);

        renderer.render();                    //map render
        b2dr.render(world, gameCam.combined); //world render

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();                   //sprite render
        //pe.draw(game.batch);
        mc.draw(game.batch);
        batclass.draw(game.batch);
        game.batch.end();
        //handler.updateAndRender();                    //lights render
        if(hud.isIsDead()) {                //is dead execution
            game.setScreen(new GameOverScreen(game));
        }

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); // camera set to hud
        hud.stage.draw();                  //hud render
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        map.dispose();
        b2dr.dispose();
        world.dispose();
        hud.dispose();
        handler.dispose();
    }

    public TextureAtlas getIsaac() {
        return isaac;
    }
    public TextureAtlas getBat() {
        return bat;
    }
    public TiledMap getMap() {
        return map;
    }
    public World getWorld() {
        return world;
    }
    public ParticleEffect getPe() {
        return pe;
    }
    public TextureAtlas getTears() {
        return tears;
    }


}
