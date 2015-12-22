package com.subzero.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
	private Texture backPodium, frontPodium, character, name;
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

	public Podium(String characterName, float x, float y, AssetManager assetManager) {
		this.assetManager = assetManager;
		this.characterName = characterName;
		backPodium = assetManager.get("PodiumBack.png", Texture.class);
		frontPodium = assetManager.get("PodiumFront.png", Texture.class);
		character = assetManager.get(characterName + ".png", Texture.class);
		name = assetManager.get(characterName + "Name.png", Texture.class);
		this.x = x;
		this.y = y;
		textureRegion = new TextureRegion(assetManager.get(characterName+"-j.png", Texture.class));
		animatedTextures = textureRegion.split(18, 22)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP);
		
		sprite = new Sprite(assetManager.get(characterName+".png", Texture.class));
		sprite.setPosition(x + 6.5f * scale, y + 9.5f * scale);
		sprite.setScale(scale);
	}

	public void render(SpriteBatch batch) {
		animation();
		
		batch.draw(backPodium, x, y, backPodium.getWidth() * scale, backPodium.getHeight() * scale);
		sprite.draw(batch);
		if (selected)
			batch.draw(frontPodium, x, y, frontPodium.getWidth() * scale, frontPodium.getHeight() * scale);
		batch.draw(name, x - name.getWidth() / (scale * 2) + frontPodium.getWidth(), y - name.getHeight() / scale - 2, name.getWidth() / scale, name.getHeight() / scale);
	}

	public boolean checkSelecting(OrthographicCamera camera) {
		if (!selected)
			if (Gdx.input.isTouched()) {
				if (new Rectangle(x, y, frontPodium.getWidth() * scale, frontPodium.getHeight() * scale).contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
					selected = true;
					animate = true;
					return true;
				}
			}
		return false;
	}
	
	public void animation(){
		if(animate){
			elapsedTime += Gdx.graphics.getDeltaTime();
			sprite.setRegion(animation.getKeyFrame(elapsedTime, false));
			if(elapsedTime >= period*4f){
				elapsedTime = 0;
				sprite = new Sprite(assetManager.get(characterName+".png", Texture.class));
				sprite.setPosition(x + 6.5f * scale, y + 9.5f * scale);
				sprite.setScale(scale);
				animate = false;
			}
		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
