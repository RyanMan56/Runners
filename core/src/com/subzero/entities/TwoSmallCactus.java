package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.images.ImageProvider;

public class TwoSmallCactus extends Entity {
	ImageProvider imageProvider = new ImageProvider();

	public TwoSmallCactus(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
//		bounds = new Rectangle[6];
//		bounds[0] = new Rectangle(0, 3, 2, 5);
//		bounds[1] = new Rectangle(3, 0, 3, 11);
//		bounds[2] = new Rectangle(7, 5, 1.5f, 4.5f);
//		bounds[3] = new Rectangle(9, 2.5f, 2, 6.5f);
//		bounds[4] = new Rectangle(12, 0, 3, 11);
//		bounds[5] = new Rectangle(16, 1.5f, 1.5f, 6.5f);
		texture = assetManager.get("TwoSmallCactus.png", Texture.class);
		sprite = new Sprite(texture, 35, 22);
		sprite.setX(imageProvider.getScreenWidth() + x);
		sprite.setY(y);
		sprite.setSize(22, 14);
		speed = 1;

		// 22 x 14
	}

	public void render(SpriteBatch batch) {
		if (shouldUpdate) {
			x -= speed;
			sprite.setX(x);
//			bounds[0].x = x + 0;
//			bounds[0].y = y + 3;
//			bounds[1].x = x + 3;
//			bounds[1].y = y + 0;
//			bounds[2].x = x + 7;
//			bounds[2].y = y + 5;
//			bounds[3].x = x + 9;
//			bounds[3].y = y + 2.5f;
//			bounds[4].x = x + 12;
//			bounds[4].y = y + 0;
//			bounds[5].x = x + 16;
//			bounds[5].y = y + 1.5f;
		}
		sprite.draw(batch);
	}
}
