package com.subzero.entities;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cloud extends Entity {
	private Texture smallCloud, bigCloud;

	public Cloud(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		smallCloud = assetManager.get("Cloud.png", Texture.class);
		bigCloud = assetManager.get("Cloud2.png", Texture.class);
		texture = smallCloud;
		sprite = new Sprite(texture, 20, 11);
		sprite.setX(x);
		sprite.setY(y);
		speed = 0.1f;
	}
	
	public void update(float gameSpeed){
		if (shouldUpdate) {
			x -= gameSpeed/16f;
			sprite.setX(x);
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	/**
	 * Randomly selects which cloud texture this cloud should take
	 */
	public void randomCloud(){
		Random rand = new Random();
		int value = rand.nextInt(2);
		if(value == 0)
			texture = smallCloud;
		else
			texture = bigCloud;
		sprite = new Sprite(texture);
		sprite.setX(x);
		sprite.setY(y);
	}

}
