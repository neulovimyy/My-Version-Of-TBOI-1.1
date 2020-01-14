package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.TBOIGame;
import com.tboi.game.worldsetting.ObjectContact;
import com.tboi.game.worldsetting.WorldSetting;
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

    int count;
    TextureAtlas isaac;
	ObjectContact contact;

	public GameScreen(TBOIGame game) {
		this.game = game;

		game.camera.position.set(game.viewport.getWorldWidth()/2, game.viewport.getWorldHeight()/2, 0);

		isaac = new TextureAtlas("entities/isaac/tr.pack");

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map/maps/dungeon1-1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 100f);

		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();

		setting = new WorldSetting(this);
		//contact = new ObjectContact(this);

        mc = new MainCharacter(this, world);
	}

	@Override
	public void show() {
		//misc classes here & on-screen controls
		control = new GameControlSetting();
		stagedControls();
	}

	public void update (float dt){

	    control.platformControlConfig(mc, dt, move, attack);//controls

		world.step( 1/ 60f, 6, 2);

		mc.update(dt);

		game.camera.position.x = mc.body.getPosition().x;
		game.camera.position.y = mc.body.getPosition().y;



		game.camera.update();

		renderer.setView(game.camera);
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

		game.batch.begin();
		mc.draw(game.batch);
		game.batch.end();

		b2dr.render(world, game.camera.combined);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
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

	public void stagedControls() {
		/**
		 * setting up stage here to control the inputs from different platforms
		 */

		stage = new Stage(game.viewport);
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

    public World getWorld() {
        return world;
    }
    public TiledMap getTiledMap() {
        return map;
    }
    public int getCount() {
        return count;
    }

    public TextureAtlas getIsaac(){
		return isaac;
	}
}