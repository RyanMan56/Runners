package com.subzero.runners;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
		assetManager.load("NikolaDesc.png", Texture.class);
		assetManager.load("Ryan.png", Texture.class);
		assetManager.load("Ryan-w.png", Texture.class);
		assetManager.load("Ryan-j.png", Texture.class);
		assetManager.load("RyanName.png", Texture.class);
		assetManager.load("RyanDesc.png", Texture.class);
		assetManager.load("Ash.png", Texture.class);
		assetManager.load("Ash-w.png", Texture.class);
		assetManager.load("Ash-j.png", Texture.class);
		assetManager.load("AshName.png", Texture.class);
		assetManager.load("AshDesc.png", Texture.class);
		assetManager.load("Rob.png", Texture.class);
		assetManager.load("Rob-w.png", Texture.class);
		assetManager.load("Rob-j.png", Texture.class);
		assetManager.load("RobName.png", Texture.class);
		assetManager.load("RobDesc.png", Texture.class);
		assetManager.load("Xorp.png", Texture.class);
		assetManager.load("Xorp-w.png", Texture.class);
		assetManager.load("Xorp-j.png", Texture.class);
		assetManager.load("XorpName.png", Texture.class);
		assetManager.load("XorpDesc.png", Texture.class);
		assetManager.load("BattleCat.png", Texture.class);
		assetManager.load("BattleCat-w.png", Texture.class);
		assetManager.load("BattleCat-j.png", Texture.class);
		assetManager.load("BattleCatName.png", Texture.class);
		assetManager.load("BattleCatDesc.png", Texture.class);
		assetManager.load("LockedName.png", Texture.class);
		assetManager.load("LockedDesc.png", Texture.class);
		assetManager.load("ComingSoon.png", Texture.class);
		assetManager.load("ComingSoonName.png", Texture.class);
		assetManager.load("ComingSoonDesc.png", Texture.class);
		assetManager.load("Back.png", Texture.class);
		assetManager.load("BackButton.png", Texture.class);
		assetManager.load("10.png", Texture.class);
		assetManager.load("20.png", Texture.class);
		assetManager.load("30.png", Texture.class);
		assetManager.load("40.png", Texture.class);
		assetManager.load("50.png", Texture.class);
		assetManager.load("CharacterUnlocked.png", Texture.class);
		assetManager.load("ShopButton.png", Texture.class);
		
		assetManager.load("Jump.wav", Sound.class);
		assetManager.load("Hit.wav", Sound.class);
		assetManager.load("Select.wav", Sound.class);
		assetManager.load("Point.wav", Sound.class);
		assetManager.load("Yay.wav", Sound.class);
		
		assetManager.load("265549__vikuserro__cheap-flash-game-tune.wav", Music.class);
		assetManager.load("251461__joshuaempyre__arcade-music-loop.wav", Music.class);
		assetManager.load("248117__zagi2__retro-gaming-loop.wav", Music.class);
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
