package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.images.ImageProvider;

public class Cactus extends Entity {
	private ImageProvider imageProvider = new ImageProvider();

	public Cactus(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		bigCactus = true;
		bounds = new Rectangle[3];
		bounds[0] = new Rectangle(0, 6, 4, 10);
		bounds[1] = new Rectangle(6, 0, 6, 22);
		bounds[2] = new Rectangle(14, 10, 3, 9);
		texture = assetManager.get("Cactus.png", Texture.class);
		sprite = new Sprite(texture, 17, 22);
		sprite.setX(imageProvider.getScreenWidth() + x);
		sprite.setY(y);
		speed = 1;
	}
	
	public void update(){
		if (shouldUpdate) {
			x -= speed;
			sprite.setX(x);
			bounds[0].x = x + 0;
			bounds[0].y = y + 6;
			bounds[1].x = x + 6;
			bounds[1].y = y + 0;
			bounds[2].x = x + 14;
			bounds[2].y = y + 10;
		}
	}

	public void render(SpriteBatch batch) {
		/*
		 * if(x < -20){ x = (imageProvider.getScreenWidth()+startX); //speed *=
		 * 1.1f; }
		 */
		
		sprite.draw(batch);
	}

}
