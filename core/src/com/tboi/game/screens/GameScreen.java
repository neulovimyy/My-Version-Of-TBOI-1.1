package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.WorldSetting;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.settings.DesktopSettings;
import com.tboi.game.settings.GameControlSetting;

public class GameScreen implements Screen {

	TBOIGame game;
	OrthographicCamera gamecam;
	Viewport gameViewPort;
	TiledMap map;
	TmxMapLoader mapLoader;
	OrthogonalTiledMapRenderer renderer;

	World world;
	Box2DDebugRenderer b2dr;
	MainCharacter mc;
	WorldSetting setting;
    Body body;

    Touchpad attack;
    Touchpad move;

    GameControlSetting control;
	Stage stage;
	Skin skin;


	public GameScreen(TBOIGame game) {
		this.game = game;

		gamecam = new OrthographicCamera();
		gameViewPort = new FitViewport(DesktopSettings.V_WIDTH, DesktopSettings.V_HEIGHT, gamecam);
		gamecam.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldHeight()/2, 0);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map/maps/dungeon1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();

		setting = new WorldSetting(this);

        mc = new MainCharacter(this);
	}

	@Override
	public void show() {
		//misc classes here & on-screen controls
		control = new GameControlSetting();
		stagedControls();
	}

	public void update (float dt){

	    control.platformControlConfig(mc, dt, move, attack);//controls

		world.step(dt, 6, 2);

		gamecam.position.x = mc.body.getPosition().x;
		gamecam.position.y = mc.body.getPosition().y;

		gamecam.update();

		renderer.setView(gamecam);
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

		b2dr.render(world, gamecam.combined);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		gameViewPort.update(width, height);
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

		/**
		 * clean the class
		 */

		world.dispose();
		map.dispose();
		b2dr.dispose();
		renderer.dispose();
	}

	public World getWorld() {
		return world;
	}

	public TiledMap getTiledMap() {
		return map;
	}

	public void stagedControls() {
		/**
		 * setting up stage here to control the inputs from different platforms
		 */

		stage = new Stage(gameViewPort);
		skin = new Skin(Gdx.files.internal("ui/neon/neon.json"), new TextureAtlas("ui/neon/neon.atlas"));

		//move pad
		move = new Touchpad(0, skin, "default");
		move.setSize(150, 150);
		move.setPosition( 20, 20 );
		move.setColor(Color.BLACK);
		stage.addActor(move);

		//attack pad
		attack = new Touchpad(0, skin, "default");
		attack.setSize(DesktopSettings.V_WIDTH/4,DesktopSettings.V_WIDTH/4);
		attack.setPosition(230,20);
		stage.addActor(attack);

		Gdx.input.setInputProcessor(stage);
	}


}