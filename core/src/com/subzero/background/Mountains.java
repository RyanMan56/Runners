package com.subzero.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.subzero.images.ImageProvider;

public class Mountains {
	private Texture mountain1, mountain2;
	private float mountain1X, mountain1Y;
	private float mountain2X, mountain2Y;
	private ImageProvider imageProvider = new ImageProvider();
	
	public Mountains(AssetManager assetManager){
		mountain1 = assetManager.get("BackgroundNew.png", Texture.class);
		mountain2 = assetManager.get("BackgroundNew.png", Texture.class);
		mountain1X = 0;
		mountain1Y = 37;
		mountain2X = imageProvider.getScreenWidth();
		mountain2Y = 37;
	}
	
	public void update(float gameSpeed){
		mountain1X -= gameSpeed/8f;
		mountain2X -= gameSpeed/8f;
		if(mountain1X+mountain1.getWidth() < 0)
			mountain1X = mountain2X+mountain2.getWidth();
		if(mountain2X+mountain2.getWidth() < 0)
			mountain2X = mountain1X+mountain1.getWidth();
	}
	
	public void render(SpriteBatch batch){		
		batch.draw(mountain1, mountain1X, mountain1Y);
		batch.draw(mountain2, mountain2X, mountain2Y);
	}

}
