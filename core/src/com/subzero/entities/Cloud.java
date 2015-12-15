package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cloud extends Entity {

	public Cloud(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		texture = assetManager.get("Cloud.png", Texture.class);
		sprite = new Sprite(texture, 20, 11);
		sprite.setX(x);
		sprite.setY(y);
//		speed = 0.25f;
		speed = 0.1f;
	}
	
	public void update(){
		if (shouldUpdate) {
			x -= speed;
			sprite.setX(x);
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
