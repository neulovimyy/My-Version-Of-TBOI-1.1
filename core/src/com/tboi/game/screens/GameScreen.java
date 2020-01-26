package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.ai.SteeringEntity;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.collision.CollisionSettings;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.entities.projectiles.Bullet;
import com.tboi.game.settings.GameControlSetting;
import com.tboi.game.worldsetting.ObjectContact;
import com.tboi.game.worldsetting.WorldSetting;
import box2dLight.PointLight;
import box2dLight.PositionalLight;
import box2dLight.RayHandler;

public class GameScreen implements Screen {

    private TextureAtlas isaac;
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
    PointLight light;
    SteeringEntity mainChar, chest;
    public GameScreen(TBOIGame game){
        this.game = game;
        gameCam = new OrthographicCamera();

        viewport = new FitViewport(352 / GameControlSetting.PPM2, 214 / GameControlSetting.PPM2, gameCam);
        world = new World(new Vector2(0, 0), true);

        isaac = new TextureAtlas("entities/isaac/tr.pack");
        mc = new MainCharacter(this);
        mainChar = new SteeringEntity(mc.body, 10);

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particles/fire"),Gdx.files.internal(""));

        hud = new GameHUD(game.batch);

        //map = new TmxMapLoader().load("map/maps/dungeon1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(null, 1f/100);
        gameCam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        b2dr = new Box2DDebugRenderer();

        handler = new RayHandler(world);
        handler.setAmbientLight(0.001f);
        light = new PointLight(handler, 32);
        light.setSoft(true);
        light.attachToBody(mc.body);
        light.setPosition(mc.posx, mc.posy + 2);
        light.setColor(Color.GOLDENROD);
        world.setContactListener(new ObjectContact(this));
        control = new GameControlSetting();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, gameCam.combined); //world render
        renderer.render();                    //map render

        game.batch.begin();                   //sprite render
        pe.draw(game.batch);
        mc.draw(game.batch);
        game.batch.end();

        handler.setCombinedMatrix(gameCam.combined);
        handler.updateAndRender();                    //lights render
        if(pe.isComplete()) pe.reset();


        if(hud.isIsDead()) {                //is dead execution
            game.setScreen(new GameOverScreen(game));
        }

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); // camera set to hud
        hud.stage.draw();                  //hud render
    }

    public void update(float delta) {

        control.platformControlConfig(mc, hud.getMove(), hud.getAttack()); //controls
        world.step(1/60f, 6, 2); //draw world
        mc.update(delta); hud.update(delta); //mc, hud, lights
        pe.getEmitters().first().setPosition(mc.posx, mc.posy);
        pe.start();
        gameCam.position.set(new Vector2(mc.body.getPosition().x, mc.body.getPosition().y), 0); //camera positions
        gameCam.update();
        renderer.setView(gameCam);
        light.update();
        pe.update(delta);
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
    public TiledMap getMap() {
        return map;
    }
    public World getWorld() {
        return world;
    }

    public ParticleEffect getPe() {
        return pe;
    }
}
