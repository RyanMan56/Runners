package com.subzero.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Podium {
	private Texture backPodium, frontPodium, character, name, description, value;
	private float x, y;
	private String characterName, nameText, descriptionText;
	private boolean selected = false;
	private float scale = 2f;
	private float elapsedTime = 0;
	private TextureRegion textureRegion;
	private TextureRegion[] animatedTextures, animatedJumpTextures;
	private Animation animation;
	private float period = 1 / 1.9f;
	private boolean animate = false;
	private Sprite sprite;
	private AssetManager assetManager;
	private float soundVolume = 0.2f;
	private float width, height;
	private Preferences pref;
	private boolean isUnlocked = false, lockDisplayed = true, isComingSoon = false;

	/**
	 * Should be called when making a Podium list. External sort function should
	 * be called on list to set positions of Podiums.
	 * 
	 * @param characterName
	 *            Name of character
	 * @param assetManager
	 *            AssetManager containing all graphic / audio files
	 */
	public Podium(String characterName, AssetManager assetManager) {
		this.assetManager = assetManager;
		this.characterName = characterName;
		backPodium = assetManager.get("PodiumBack.png", Texture.class);
		frontPodium = assetManager.get("PodiumFront.png", Texture.class);
		pref = Gdx.app.getPreferences("com.subzero.runners");
		isUnlocked = pref.getBoolean(characterName, false);

		if (characterName.equals("ComingSoon"))
			isComingSoon = true;
		character = assetManager.get(characterName + ".png", Texture.class);
		nameText = characterName;
		if (isUnlocked && (!isComingSoon)) {
			lockDisplayed = false;
			name = assetManager.get(characterName + "Name.png", Texture.class);
			description = assetManager.get(characterName + "Desc.png", Texture.class);
			textureRegion = new TextureRegion(assetManager.get(characterName + "-j.png", Texture.class));
			animatedTextures = textureRegion.split(18, 22)[0];
			animation = new Animation(period, animatedTextures);
			animation.setPlayMode(PlayMode.LOOP);
		}
		if ((!isUnlocked) || isComingSoon) {
			name = assetManager.get("LockedName.png", Texture.class);
			description = assetManager.get("LockedDesc.png", Texture.class);
			if (nameText.equals("Ryan"))
				value = assetManager.get("10.png", Texture.class);
			if (nameText.equals("Ash"))
				value = assetManager.get("20.png", Texture.class);
			if (nameText.equalsIgnoreCase("Rob"))
				value = assetManager.get("30.png", Texture.class);
			if (nameText.equalsIgnoreCase("BattleCat"))
				value = assetManager.get("40.png", Texture.class);
			if (nameText.equalsIgnoreCase("Xorp"))
				value = assetManager.get("50.png", Texture.class);
			if (nameText.equalsIgnoreCase("Rootsworth"))
				value = assetManager.get("60.png", Texture.class);
			if (nameText.equalsIgnoreCase("Snap"))
				value = assetManager.get("70.png", Texture.class);
			if (nameText.equalsIgnoreCase("Metatron"))
				value = assetManager.get("80.png", Texture.class);
			if (nameText.equalsIgnoreCase("Abaddon"))
				value = assetManager.get("90.png", Texture.class);
			if (nameText.equals("ComingSoon")) {
				name = assetManager.get("ComingSoonName.png", Texture.class);
				description = assetManager.get("ComingSoonDesc.png", Texture.class);
			}
		}

		sprite = new Sprite(assetManager.get(characterName + ".png", Texture.class));
		sprite.setScale(scale);
		width = sprite.getWidth();
		height = sprite.getHeight();
	}
	
	public void setupCharacter(String characterName){
		this.characterName = characterName;
		backPodium = assetManager.get("PodiumBack.png", Texture.class);
		frontPodium = assetManager.get("PodiumFront.png", Texture.class);
		pref = Gdx.app.getPreferences("com.subzero.runners");
		isUnlocked = pref.getBoolean(characterName, false);

		if (characterName.equals("ComingSoon"))
			isComingSoon = true;
		character = assetManager.get(characterName + ".png", Texture.class);
		nameText = characterName;
		if (isUnlocked && (!isComingSoon)) {
			lockDisplayed = false;
			name = assetManager.get(characterName + "Name.png", Texture.class);
			description = assetManager.get(characterName + "Desc.png", Texture.class);
			textureRegion = new TextureRegion(assetManager.get(characterName + "-j.png", Texture.class));
			animatedTextures = textureRegion.split(18, 22)[0];
			animation = new Animation(period, animatedTextures);
			animation.setPlayMode(PlayMode.LOOP);
		}
		if ((!isUnlocked) || isComingSoon) {
			name = assetManager.get("LockedName.png", Texture.class);
			description = assetManager.get("LockedDesc.png", Texture.class);
			if (nameText.equals("Ryan"))
				value = assetManager.get("10.png", Texture.class);
			if (nameText.equals("Ash"))
				value = assetManager.get("20.png", Texture.class);
			if (nameText.equalsIgnoreCase("Rob"))
				value = assetManager.get("30.png", Texture.class);
			if (nameText.equalsIgnoreCase("BattleCat"))
				value = assetManager.get("40.png", Texture.class);
			if (nameText.equalsIgnoreCase("Xorp"))
				value = assetManager.get("50.png", Texture.class);
			if (nameText.equalsIgnoreCase("Rootsworth"))
				value = assetManager.get("60.png", Texture.class);
			if (nameText.equalsIgnoreCase("Snap"))
				value = assetManager.get("70.png", Texture.class);
			if (nameText.equalsIgnoreCase("Metatron"))
				value = assetManager.get("80.png", Texture.class);
			if (nameText.equalsIgnoreCase("Abaddon"))
				value = assetManager.get("90.png", Texture.class);
			if (nameText.equals("ComingSoon")) {
				name = assetManager.get("ComingSoonName.png", Texture.class);
				description = assetManager.get("ComingSoonDesc.png", Texture.class);
			}
		}
		
		sprite = new Sprite(assetManager.get(characterName + ".png", Texture.class));
		sprite.setScale(scale);
		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public void render(SpriteBatch batch) {
		if((pref.getBoolean(nameText)) && (!isUnlocked))
			setupCharacter(nameText);
		
		if (isUnlocked)
			animation();

		batch.draw(backPodium, x, y, backPodium.getWidth() * scale, backPodium.getHeight() * scale);
		if (!isUnlocked) {
			sprite.setColor(5 / 255f, 5 / 255f, 5 / 255f, 1);
		}
		sprite.draw(batch);
		if (selected)
			batch.draw(frontPodium, x, y, frontPodium.getWidth() * scale, frontPodium.getHeight() * scale);
		batch.draw(name, x - name.getWidth() / (scale * 2) + frontPodium.getWidth(), y - name.getHeight() / scale - 2, name.getWidth() / scale, name.getHeight() / scale);
		if (isUnlocked)
			batch.draw(description, (int) (x - description.getWidth() / (scale * 4) + frontPodium.getWidth()), (int) (y - (description.getHeight() * 2) / scale), description.getWidth() / (scale * 2), description.getHeight() / (scale * 2));
		else
			batch.draw(description, (int) (x - value.getWidth() / (scale * 4) - description.getWidth() / (scale * 4) + frontPodium.getWidth()), (int) (y - (description.getHeight() * 2) / scale), description.getWidth() / (scale * 2), description.getHeight() / (scale * 2));
		if (!isUnlocked)
			batch.draw(value, (int) ((x - value.getWidth() / (scale * 4) - description.getWidth() / (scale * 4) + frontPodium.getWidth()) + description.getWidth() / (scale * 2) + 4), (int) (y - (description.getHeight() * 2) / scale), value.getWidth() / (scale * 2), value.getHeight() / (scale * 2));
	}

	public void checkPrefs() {
		isUnlocked = pref.getBoolean(characterName, false);
		if (!isComingSoon)
			if (isUnlocked)
				if (lockDisplayed) {

					name = assetManager.get(characterName + "Name.png", Texture.class);
					description = assetManager.get(characterName + "Desc.png", Texture.class);
					textureRegion = new TextureRegion(assetManager.get(characterName + "-j.png", Texture.class));
					animatedTextures = textureRegion.split(18, 22)[0];
					animation = new Animation(period, animatedTextures);
					animation.setPlayMode(PlayMode.LOOP);

					sprite = new Sprite(assetManager.get(characterName + ".png", Texture.class));
					sprite.setScale(scale);
					width = sprite.getWidth();
					height = sprite.getHeight();

					lockDisplayed = false;
				}

	}

	public boolean checkSelecting(OrthographicCamera camera) {
		if (!isComingSoon)
			if (!selected)
				if (Gdx.input.justTouched()) {
					if (new Rectangle(x, y, frontPodium.getWidth() * scale, frontPodium.getHeight() * scale).contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
						selected = true;
						animate = true;
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Select.wav", Sound.class).play(soundVolume);
						return true;
					}
				}
		return false;
	}

	public void animation() {
		if (animate) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			sprite.setRegion(animation.getKeyFrame(elapsedTime, false));
			if (elapsedTime >= period * 4f) {
				elapsedTime = 0;
				sprite = new Sprite(assetManager.get(characterName + ".png", Texture.class));
				sprite.setPosition(x + 6.5f * scale, y + 9.5f * scale);
				sprite.setScale(scale);
				animate = false;
			}
		}
	}

	public void setUnlocked(boolean unlocked){
		this.isUnlocked = unlocked;
	}
	
	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		sprite.setPosition(x + 6.5f * scale, y + 9.5f * scale);
	}

	public void setX(float x) {
		this.x = x;
		sprite.setPosition(x + 6.5f * scale, y + 9.5f * scale);
	}

	public void setY(float y) {
		this.y = y;
		sprite.setPosition(x + 6.5f * scale, y + 9.5f * scale);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isUnlocked() {
		return isUnlocked;
	}

	public boolean isSelected() {
		return selected;
	}

	public String getName() {
		return nameText;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
