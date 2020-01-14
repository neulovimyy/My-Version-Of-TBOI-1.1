package com.tboi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.screens.LoadingScreen;
import com.tboi.game.screens.TitleScreen;
import com.tboi.game.settings.DesktopSettings;


public class TBOIGame extends Game{
	TBOIGame game;
	public static SpriteBatch batch;
	public OrthographicCamera camera;

	Viewport viewport;
	Sound sound;
	public Music music;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(DesktopSettings.WIDTH, DesktopSettings.HEIGHT, camera);
		camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/tboi-soundtrack.mp3"));
		setScreen(new GameScreen(this));
		batch = new SpriteBatch();
		music.setLooping(true);
		music.setVolume(0.5f);
		//music.play();
	}

	@Override
	public void render() {
		camera.update();
		super.render();
	}
	
	@Override
	public void dispose () {
		music.dispose();
		super.dispose();
	}
}
