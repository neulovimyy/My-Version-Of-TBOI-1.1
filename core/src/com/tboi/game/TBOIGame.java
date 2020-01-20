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
import com.tboi.game.settings.GameControlSetting;

public class TBOIGame extends Game{
	TBOIGame game;
	public static SpriteBatch batch;
	public OrthographicCamera camera;

	public Viewport viewport;
	Sound sound;
	public Music music;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/tboi-soundtrack.mp3"));
		batch = new SpriteBatch();
		music.setLooping(true);
		music.setVolume(0.5f);
		//music.play();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose () {
		music.dispose();
		super.dispose();
	}
}
