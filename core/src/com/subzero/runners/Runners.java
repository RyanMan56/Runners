package com.subzero.runners;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
		assetManager.load("Menu.png", Texture.class);
		assetManager.load("Restart.png", Texture.class);
		assetManager.load("Pause.png", Texture.class);
		assetManager.load("BackgroundFront.png", Texture.class);
		assetManager.load("BackgroundMiddle.png", Texture.class);
		assetManager.load("BackgroundBack.png", Texture.class);
		assetManager.load("BackgroundNew.png", Texture.class);
		assetManager.load("CharacterSelectButton.png", Texture.class);
		assetManager.load("CharacterSelectText.png", Texture.class);
		assetManager.load("PodiumBack.png", Texture.class);
		assetManager.load("PodiumFront.png", Texture.class);
		assetManager.load("NikolaName.png", Texture.class);
		assetManager.load("Ryan.png", Texture.class);
		assetManager.load("Ryan-w.png", Texture.class);
		assetManager.load("Ryan-j.png", Texture.class);
		assetManager.load("RyanName.png", Texture.class);
		assetManager.load("Back.png", Texture.class);
		assetManager.load("BackButton.png", Texture.class);
		
		assetManager.load("Jump.wav", Sound.class);
		assetManager.load("Hit.wav", Sound.class);
		assetManager.load("Select.wav", Sound.class);
		assetManager.load("Point.wav", Sound.class);
	}

	@Override
	public void render () {
		super.render();
		if(!loaded){
			if(assetManager.update()){
				assetManager.get("Select.wav", Sound.class).play(0);
//				gameScreen = new GameScreen(assetManager);
				mainMenuScreen = new MainMenuScreen(this, assetManager);
				setScreen(mainMenuScreen);
				loaded = true;
			}
		}
	}
}
