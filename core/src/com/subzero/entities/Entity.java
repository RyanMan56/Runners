package com.subzero.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	protected float x, y;
	protected float dx = 0.5f, dy = 0.9f;
	protected float speed = 0;
	protected float health;
	protected Texture texture;
	protected Sprite sprite;
	protected AssetManager assetManager;
	protected boolean shouldUpdate = true;
	protected Rectangle[] bounds;
	protected boolean bigCactus = false;
	
	public Entity(float x, float y, float health, AssetManager assetManager){
		this.x = x;
		this.y = y;
		this.health = health;
		this.assetManager = assetManager;
	}
	
	public void increaseSpeedBy(float value){
		speed += value;
	}
	
	public void setBigCactus(boolean bigCactus){
		this.bigCactus = bigCactus;
	}
	
	public boolean isBigCactus(){
		return bigCactus;
	}
	
	public Rectangle[] getBounds(){
		return bounds;
	}
	
	public void setBounds(Rectangle[] bounds){
		this.bounds = bounds;
	}
	
	public boolean getShouldUpdate(){
		return shouldUpdate;
	}
	
	public void setShouldUpdate(boolean shouldUpdate){
		this.shouldUpdate = shouldUpdate;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
	
	
	
	
}
