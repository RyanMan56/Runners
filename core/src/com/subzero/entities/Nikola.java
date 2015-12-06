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

	/*public void move() {
		System.out.println(y+", "+speed);
		// TODO Set jumping so that when it is half over the cactus make the jump go down again
		// TODO Or set jumping distance relative to the speed (preferred)
		if ((!touched) && (!jumping) & (!falling)) {
			if (Gdx.input.isTouched()) {
				elapsedJumpTime = 0;
				jumpAnimationing = true;
				touched = true;
				jumping = true;
				speed = 4f/gameSpeed+0.5f; // Mess with this // Was 4 // Down part
				System.out.println(speed + " : "+gameSpeed);
			}
		}
		if (touched && jumping) {
			//sprite.setTexture(texture);
			//sprite.setTexture(animatedJumpTextures[0].getTexture());
			speed *= dy*(1+gameSpeed/100); // Uppy part
			y += speed;
			if (speed <= 0.25f) { // Mess with this // Was 0.9 //TODO MAYBE 0.5
				falling = true;
				startedFalling = true;
				touched = false;
				jumping = false;
			}
		}
		if (falling) {
			speed *= 1.15f; // Mess with this // Down speed
			if (y - speed < groundLevel.y)
				y = groundLevel.y;
			else
				y -= speed;
			if (startedFalling) {
				//sprite.setTexture(animatedJumpTextures[1].getTexture());
				//speed = -2f;
				startedFalling = false;
			}
			if (y <= groundLevel.y) {
				jumpAnimationing = false;
				speed = 0;
				falling = false;
			}
		}

		//y += speed;
		//speed *= dy;
		sprite.setX(x);
		sprite.setY(y);
		bounds[0].x = x + 6;
		bounds[0].y = y + 0;
		bounds[1].x = x + 0;
		bounds[1].y = y + 6;
		//System.out.println(speed);
	}*/
	
	public void move(){
		y += speed*Gdx.graphics.getDeltaTime()*(17*gameSpeed);//gameSpeed*10); // Determines jump height
		speed -= dy*Gdx.graphics.getDeltaTime()*(25*gameSpeed);//gameSpeed;//*(25+gameSpeed/10f); // Determines jump duration
		System.out.println("Speed: "+speed+" gameSpeed: "+gameSpeed);
		
		if(y <= groundLevel.y){
			jumping = false;
			y = groundLevel.y;
			speed = 0;
		}
		if(!jumping){
			if(Gdx.input.isTouched()){
				speed = 6.7f;//+gameSpeed; // Determines jump height 
				jumping = true;
			}
		}

		sprite.setX(x);
		sprite.setY(y);
		bounds[0].x = x + 6;
		bounds[0].y = y + 0;
		bounds[1].x = x + 0;
		bounds[1].y = y + 6;
	}

	public void render(SpriteBatch batch) {
		if (health > 0) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			//sprite.setTexture(animation.getKeyFrame(elapsedTime, false).getTexture());
			if (!jumpAnimationing)
				sprite.setRegion(animation.getKeyFrame(elapsedTime, false));
			if (jumpAnimationing) {
				elapsedJumpTime += Gdx.graphics.getDeltaTime();
				sprite.setRegion(jumpAnimation.getKeyFrame(elapsedJumpTime, false));
			}
			move();
		}
			sprite.draw(batch);
	}

}
