package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BigCloud extends Entity {

	public BigCloud(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		texture = assetManager.get("Cloud2.png", Texture.class);
		sprite = new Sprite(texture, 38, 15);
		sprite.setX(x);
		sprite.setY(y);
		speed = 0.1f;
	}

	public void render(SpriteBatch batch) {
		if (shouldUpdate) {
			x -= speed;
			sprite.setX(x);
		}
		sprite.draw(batch);
	}

}
