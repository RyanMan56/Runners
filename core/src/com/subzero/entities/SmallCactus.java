package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.images.ImageProvider;

public class SmallCactus extends Entity {
	private ImageProvider imageProvider = new ImageProvider();

	public SmallCactus(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
//		bounds = new Rectangle[3];
//		bounds[0] = new Rectangle(0, 3, 2, 5);
//		bounds[1] = new Rectangle(3, 0, 3, 11);
//		bounds[2] = new Rectangle(7, 5, 1.5f, 4.5f);
		texture = assetManager.get("SmallCactus.png", Texture.class);
		sprite = new Sprite(texture, 17, 22);
		sprite.setX(imageProvider.getScreenWidth() + x);
		sprite.setY(y);
		sprite.setSize(11, 14);
		speed = 1;

		// 11 x 14
	}

	public void render(SpriteBatch batch) {
		if (shouldUpdate) {
			x -= speed;
			sprite.setX(x);
			//bounds[0].x = x + 
//			bounds[0].x = x + 0;
//			bounds[0].y = y + 3;
//			bounds[1].x = x + 3;
//			bounds[1].y = y + 0;
//			bounds[2].x = x + 7;
//			bounds[2].y = y + 5;
		}
		sprite.draw(batch);
	}

}
