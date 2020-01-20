package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.hud.GameHUD;
import com.tboi.game.settings.GameControlSetting;
import com.tboi.game.worldsetting.ObjectContact;
import com.tboi.game.worldsetting.WorldSetting;

public class GameScreen implements Screen {

    private TextureAtlas isaac;
    private TiledMap map;
    private World world;
    private TBOIGame game;
    public OrthographicCamera gameCam;
    public Viewport viewport;
    public OrthogonalTiledMapRenderer renderer;
    GameHUD hud;
    GameControlSetting control;
    Box2DDebugRenderer b2dr;
    WorldSetting setting;
    MainCharacter mc;

    public GameScreen(TBOIGame game){
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(352 / GameControlSetting.PPM2, 214 / GameControlSetting.PPM2, gameCam);

        isaac = new TextureAtlas("entities/isaac/tr.pack");
        hud = new GameHUD(game.batch);

        map = new TmxMapLoader().load("map/maps/dungeon1-1.tmx");//map
        renderer = new OrthogonalTiledMapRenderer(map, GameControlSetting.PPM1);
        gameCam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        setting = new WorldSetting(this);

        mc = new MainCharacter(this);

        world.setContactListener(new ObjectContact(this));
    }

    @Override
    public void show() {
        control = new GameControlSetting();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gameCam.combined);

        game.batch.begin();
        mc.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void update(float delta) {
        control.platformControlConfig(mc, hud.getMove(), hud.getAttack());

        world.step(1/60f, 6, 2);

        mc.update(delta);

        hud.update(delta);

        gameCam.position.set(new Vector2(mc.body.getPosition().x, mc.body.getPosition().y), 0);

        gameCam.update();

        renderer.setView(gameCam);
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
}
