package com.subzero.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Nikola extends Entity {
	private boolean touched = false;
	private boolean jumping = false;
	private boolean falling = false;
	private boolean startedFalling = false;
	private Vector2 groundLevel;
	private float elapsedTime = 0;
	private float elapsedJumpTime = 0;
	private TextureRegion textureRegion;
	private TextureRegion[] animatedTextures, animatedJumpTextures;
	private Animation animation, jumpAnimation;
	private float period = 1 / 5f;
	private float jumpPeriod = 1 / 1.9f;
	private boolean jumpAnimationing = false;
	private float gameSpeed = 1;

	public Nikola(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		bounds = new Rectangle[2];
		bounds[0] = new Rectangle(-20, 0, 8, 8);
		bounds[1] = new Rectangle(-20, 6, 18, 16);
		texture = assetManager.get("Nikola.png", Texture.class);
		textureRegion = new TextureRegion(assetManager.get("Nikola-w.png", Texture.class));
		animatedTextures = textureRegion.split(18, 22)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP);
		textureRegion = new TextureRegion(assetManager.get("Nikola-j.png", Texture.class));
		animatedJumpTextures = textureRegion.split(18, 22)[0];
		jumpAnimation = new Animation(jumpPeriod, animatedJumpTextures);
		groundLevel = new Vector2(x, y);
		sprite = new Sprite(texture, 18, 22);
		sprite.setX(x);
		sprite.setY(y);
	}
	
	public void updateGameSpeed(float gameSpeed){
		this.gameSpeed = gameSpeed;
	}

	public void move(){
		y += speed*Gdx.graphics.getDeltaTime()*(17*gameSpeed); // Determines jump height
		speed -= dy*Gdx.graphics.getDeltaTime()*(25*gameSpeed); // Determines jump duration
		System.out.println("Speed: "+speed+" gameSpeed: "+gameSpeed);
		
		if(y <= groundLevel.y){
			jumping = false;
			y = groundLevel.y;
			speed = 0;
		}
		if(!jumping){
			if(Gdx.input.isTouched()){
				speed = 6.7f; // Determines initial jump velocity
				jumping = true;
			}
		}

		sprite.setX(x);
		sprite.setY(y);
		bounds[0].x = x + 6;
		bounds[0].y = y + 0;
		bounds[1].x = x + 0;
		bounds[1].y = y + 6;
		
		// TODO Do jumping animation not as an animation. Set jump frame 0 when speed > 0 and set jump frame 1 when (speed <= 0 AND jumping)
	}

	public void render(SpriteBatch batch) {
		if (health > 0) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			//sprite.setTexture(animation.getKeyFrame(elapsedTime, false).getTexture());
			if (!jumpAnimationing)
				sprite.setRegion(animation.getKeyFrame(elapsedTime, false));
			if (jumpAnimationing) {
				//elapsedJumpTime += Gdx.graphics.getDeltaTime();
				//sprite.setRegion(jumpAnimation.getKeyFrame(elapsedJumpTime, false));
			}
			move();
		}
			sprite.draw(batch);
	}

}
