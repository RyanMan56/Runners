package com.subzero.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.subzero.images.ImageProvider;

public class Mountains {
	private AssetManager assetManager;
	private Texture mountainFront1, mountainFront2, mountainMiddle1, mountainMiddle2, mountainBack1, mountainBack2;
	private float mountainFront1X, mountainFront2X, mountainMiddle1X, mountainMiddle2X, mountainBack1X, mountainBack2X;
	private float mountainFront1Y, mountainFront2Y, mountainMiddle1Y, mountainMiddle2Y, mountainBack1Y, mountainBack2Y;
	private ImageProvider imageProvider = new ImageProvider();
	
	public Mountains(AssetManager assetManager){
		this.assetManager = assetManager; 
		mountainFront1 = assetManager.get("BackgroundFront.png", Texture.class);
		mountainFront2 = assetManager.get("BackgroundFront.png", Texture.class);
		mountainMiddle1 = assetManager.get("BackgroundMiddle.png", Texture.class);
		mountainMiddle2 = assetManager.get("BackgroundMiddle.png", Texture.class);
		mountainBack1 = assetManager.get("BackgroundBack.png", Texture.class);
		mountainBack2 = assetManager.get("BackgroundBack.png", Texture.class);
		mountainFront1X = 0;
		mountainMiddle1X = 0;
		mountainBack1X = 0;
		mountainFront1Y = 17;
		mountainMiddle1Y = 17;
		mountainBack1Y = 17;
		mountainFront2X = imageProvider.getScreenWidth()-1;
		mountainMiddle2X = imageProvider.getScreenWidth()-1;
		mountainBack2X = imageProvider.getScreenWidth()-1;
		mountainFront2Y = 17;
		mountainMiddle2Y = 17;
		mountainBack2Y = 17;
	}
	
	public void update(float gameSpeed){
		mountainFront1X -= gameSpeed/2f;
		mountainFront2X -= gameSpeed/2f;
		mountainMiddle1X -= gameSpeed/4f;
		mountainMiddle2X -= gameSpeed/4f;
		mountainBack1X -= gameSpeed/8f;
		mountainBack2X -= gameSpeed/8f;
		if(mountainFront1X+mountainFront1.getWidth() < 0)
			mountainFront1X = mountainFront2X+mountainFront2.getWidth();
		if(mountainFront2X+mountainFront2.getWidth() < 0)
			mountainFront2X = mountainFront1X+mountainFront1.getWidth();

		if(mountainMiddle1X+mountainMiddle1.getWidth() < 0)
			mountainMiddle1X = mountainMiddle2X+mountainMiddle2.getWidth();
		if(mountainMiddle2X+mountainMiddle2.getWidth() < 0)
			mountainMiddle2X = mountainMiddle1X+mountainMiddle1.getWidth();

		if(mountainBack1X+mountainBack1.getWidth() < 0)
			mountainBack1X = mountainBack2X+mountainBack2.getWidth();
		if(mountainBack2X+mountainBack2.getWidth() < 0)
			mountainBack2X = mountainBack1X+mountainBack1.getWidth();

	}
	
	public void render(SpriteBatch batch){		
		batch.draw(mountainBack1, mountainBack1X, mountainBack1Y);
		batch.draw(mountainBack2, mountainBack2X, mountainBack2Y);
		
//		batch.draw(mountainMiddle1, mountainMiddle1X, mountainMiddle1Y);
//		batch.draw(mountainMiddle2, mountainMiddle2X, mountainMiddle2Y);
		
//		batch.draw(mountainFront1, mountainFront1X, mountainFront1Y);
//		batch.draw(mountainFront2, mountainFront2X, mountainFront2Y);
	}

}
