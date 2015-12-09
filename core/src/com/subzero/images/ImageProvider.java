package com.subzero.images;

public class ImageProvider {
	//private Vector2 size = Scaling.fit.apply(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 180, 120);
	private float SCREEN_WIDTH = 213.5f;//121.5f;//180; // 18/22 = 0.818 18*9 = 162 
	private float SCREEN_HEIGHT = 120;//81;//120; // 162 * 0.818 = 132.516
	//FIX THIS BECAUSE NIKOLA'S TOO WIDE
	private float aspectRatio = 18/22f;
//	private float SCREEN_WIDTH = Gdx.graphics.getWidth()*0.1875f;
//	private float SCREEN_HEIGHT = Gdx.graphics.getHeight()*0.1875f;
//	private float SCREEN_HEIGHT = SCREEN_WIDTH / (Gdx.graphics.getWidth()/Gdx.graphics.getHeight());
	// TODO This
	
	public float getScreenWidth(){
		return SCREEN_WIDTH;
	}
	
	public float getScreenHeight(){
		return SCREEN_HEIGHT; 
	}

}
