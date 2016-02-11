package com.subzero.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	private boolean jumping = false;
	private Vector2 groundLevel;
	private float elapsedTime = 0;
	private TextureRegion textureRegion;
	private TextureRegion[] animatedTextures, animatedJumpTextures;
	private Animation animation;
	private float period = 1 / 5f;
	private float jumpPeriod = 1 / 1.9f;
	private boolean jumpAnimationing = false;
	private float gameSpeed = 1;
	private float soundVolume;
	private Preferences pref;
	private String defaultCharacter;
	private boolean volumeButtonPressed = false;

	public Player(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		bounds = new Rectangle[2];
		bounds[0] = new Rectangle(-20, 0, 8, 8);
		bounds[1] = new Rectangle(-20, 6, 18, 16);
		pref = Gdx.app.getPreferences("com.subzero.runners");
		defaultCharacter = pref.getString("defaultCharacter", "Nikola");

		texture = assetManager.get(defaultCharacter + ".png", Texture.class);
		textureRegion = new TextureRegion(assetManager.get(defaultCharacter + "-w.png", Texture.class));
		animatedTextures = textureRegion.split(18, 22)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP);
		textureRegion = new TextureRegion(assetManager.get(defaultCharacter + "-j.png", Texture.class));
		animatedJumpTextures = textureRegion.split(18, 22)[0];
		groundLevel = new Vector2(x, y);
		sprite = new Sprite(texture, 18, 22);
		sprite.setX(x);
		sprite.setY(y);
	}

	public void setCharacter() {
		defaultCharacter = pref.getString("defaultCharacter", "Nikola");
		texture = assetManager.get(defaultCharacter + ".png", Texture.class);
		textureRegion = new TextureRegion(assetManager.get(defaultCharacter + "-w.png", Texture.class));
		animatedTextures = textureRegion.split(18, 22)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP);
		textureRegion = new TextureRegion(assetManager.get(defaultCharacter + "-j.png", Texture.class));
		animatedJumpTextures = textureRegion.split(18, 22)[0];
		groundLevel = new Vector2(x, y);
		sprite = new Sprite(texture, 18, 22);
		sprite.setX(x);
		sprite.setY(y);
	}

	public void updateGameSpeed(float gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}

	public void move() {
		y += speed * Gdx.graphics.getDeltaTime() * (17 * gameSpeed); // Determines jump height
		speed -= dy * Gdx.graphics.getDeltaTime() * (25 * gameSpeed); // Determines jump duration

		if (y <= groundLevel.y) {
			jumping = false;
			y = groundLevel.y;
			speed = 0;
		}
		if (gameSpeed > 1.003)
			if (!volumeButtonPressed)
				if (!jumping) {
					if (Gdx.input.justTouched()) {
						speed = 6.7f; // Determines initial jump velocity
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Jump.wav", Sound.class).play(soundVolume);
						jumping = true;
					}
				}

		sprite.setX(x);
		sprite.setY(y);
		bounds[0].x = x + 6;
		bounds[0].y = y + 0;
		bounds[1].x = x + 0;
		bounds[1].y = y + 6;

		if (jumping) {
			if (speed > 0)
				sprite.setRegion(animatedJumpTextures[0]);
			if (speed <= 0)
				sprite.setRegion(animatedJumpTextures[1]);
		}
	}

	public void update(boolean volumeButtonPressed) {
		this.volumeButtonPressed = volumeButtonPressed;
		if (health > 0) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			if (!jumpAnimationing)
				sprite.setRegion(animation.getKeyFrame(elapsedTime, false));
			move();
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
