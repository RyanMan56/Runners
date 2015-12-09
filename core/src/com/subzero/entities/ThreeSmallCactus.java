package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.images.ImageProvider;

public class ThreeSmallCactus extends Entity {
	ImageProvider imageProvider = new ImageProvider();

	public ThreeSmallCactus(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
//		bounds = new Rectangle[9];
//		bounds[0] = new Rectangle(0, 3, 2, 5);
//		bounds[1] = new Rectangle(3, 0, 3, 11);
//		bounds[2] = new Rectangle(7, 5, 1.5f, 4.5f);
//		bounds[3] = new Rectangle(9, 2.5f, 2, 6.5f);
//		bounds[4] = new Rectangle(12, 0, 3, 11);
//		bounds[5] = new Rectangle(16, 1.5f, 1.5f, 6.5f);
//		bounds[6] = new Rectangle(18, 5, 1.5f, 5.5f);
//		bounds[7] = new Rectangle(20.5f, 0, 3, 11);
//		bounds[8] = new Rectangle(24.5f, 4, 1.5f, 5);
		texture = assetManager.get("ThreeSmallCactus.png", Texture.class);
		sprite = new Sprite(texture, 52, 22);
		sprite.setX(imageProvider.getScreenWidth() + x);
		sprite.setY(y);
		sprite.setSize(33, 14);
		speed = 1;

		// 33 x 14
	}
}
