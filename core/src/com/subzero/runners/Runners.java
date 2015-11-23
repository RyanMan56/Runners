package com.subzero.runners;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.subzero.screens.GameScreen;
import com.subzero.screens.MainMenuScreen;

public class Runners extends Game {
	MainMenuScreen mainMenuScreen;
	GameScreen gameScreen;
	AssetManager assetManager;
	private boolean loaded = false;
	
	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load("Nikola.png", Texture.class);
		assetManager.load("Cactus.png", Texture.class);
		assetManager.load("SmallCactus.png", Texture.class);
		assetManager.load("ThreeSmallCactus.png", Texture.class);
		assetManager.load("TwoSmallCactus.png", Texture.class);
		assetManager.load("Cloud.png", Texture.class);
		assetManager.load("Cloud2.png", Texture.class);
		assetManager.load("Nikola-w.png", Texture.class);
		assetManager.load("Nikola-j.png", Texture.class);
		assetManager.load("Medal.png", Texture.class);
		assetManager.load("Score.png", Texture.class);
		assetManager.load("Hi.png", Texture.class);
		assetManager.load("High.png", Texture.class);
		assetManager.load("BlankMedal.png", Texture.class);
		assetManager.load("BronzeMedal.png", Texture.class);
		assetManager.load("SilverMedal.png", Texture.class);
		assetManager.load("GoldMedal.png", Texture.class);
		assetManager.load("PlatinumMedal.png", Texture.class);
	}

	@Override
	public void render () {
		super.render();
		if(!loaded){
			if(assetManager.update()){
				gameScreen = new GameScreen(this, assetManager);
				setScreen(gameScreen);
				loaded = true;
			}
		}
	}
}