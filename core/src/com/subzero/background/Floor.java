package com.subzero.background;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.images.ImageProvider;

public class Floor {
	ShapeRenderer shapeRenderer;
	ImageProvider imageProvider = new ImageProvider();
	
	public Floor(){
		shapeRenderer = new ShapeRenderer();
	}
	
	public void render(ShapeRenderer shapeRenderer){
		//shapeRenderer.setColor(0.86f, 0.61f, 0.25f, 1); // Light
		//shapeRenderer.setColor(0.72f, 0.51f, 0.208f, 1); // Dark
		shapeRenderer.setColor(0.745f, 0.525f, 0.216f, 1);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), 15);
		//shapeRenderer.setColor(0.72f, 0.51f, 0.208f, 1); // Light
		//shapeRenderer.setColor(0.612f, 0.43f, 0.176f, 1); // Dark
		shapeRenderer.setColor(0.643f, 0.455f, 0.184f, 1);
		shapeRenderer.rect(0, 9, imageProvider.getScreenWidth(), 6);
		shapeRenderer.setColor(0.745f, 0.525f, 0.216f, 1);
		shapeRenderer.rect(0, 15, imageProvider.getScreenWidth(), 3);
	}
}
