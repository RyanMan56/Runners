package com.subzero.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.subzero.background.Floor;
import com.subzero.entities.BigCloud;
import com.subzero.entities.Cloud;
import com.subzero.images.ImageProvider;
import com.subzero.util.ToScreenPixels;

public class MainMenuScreen implements Screen {
	private AssetManager assetManager;
	private OrthographicCamera camera;
	private ImageProvider imageProvider;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Floor floor;
	private Random rand;
	private Cloud[] clouds = new Cloud[2];
	private Cloud cloud;
	private BigCloud bigCloud;
	private Rectangle[] dust = new Rectangle[30];
	private float speed = 1.0f;
	private Texture title;
	private float titleWidth, titleHeight;
	private TextureRegion textureRegion, bigNikola;
	private TextureRegion[] animatedTextures;
	private Animation animation;
	private float period = 1 / 1.6f;
	private float bigNikolaY, bigNikolaYDest, bigNikolaWidth, bigNikolaHeight;
	private float elapsedTime = 0, arrivedTime = 0;
	private boolean arrived = false;
	private float titleAlpha = 0, shapeAlpha = 0;
	private boolean finished = false;;
	private boolean touched = false;

	public MainMenuScreen(AssetManager assetManager) {
		this.assetManager = assetManager;
		imageProvider = new ImageProvider();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		floor = new Floor();
		rand = new Random();
		bigNikolaY = ToScreenPixels.toScreenHeightPixels(-122);
		bigNikolaYDest = ToScreenPixels.toScreenHeightPixels(-20);

		clouds[0] = new Cloud(imageProvider.getScreenWidth(), imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		clouds[1] = new Cloud(imageProvider.getScreenWidth() * 1.5f, imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		cloud = new Cloud(-50, clouds[0].getY(), 100, assetManager);
		bigCloud = new BigCloud(-50, clouds[1].getY(), 100, assetManager);
		title = assetManager.get("Menu.png", Texture.class);
		textureRegion = new TextureRegion(assetManager.get("Nikola-j.png", Texture.class));
		animatedTextures = textureRegion.split(18, 22)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP);
		bigNikola = animation.getKeyFrame(elapsedTime);
		
		bigNikolaHeight = imageProvider.getScreenHeight()/(120/122f);
		bigNikolaWidth = bigNikolaHeight * (18/22f);
		
		titleWidth = imageProvider.getScreenWidth()/(180/152f);
		titleHeight = titleWidth / (152/18f);

		createDust();
	}

	public void createDust() {
		for (int i = 0; i < dust.length; i++) {
			dust[i] = new Rectangle(rand.nextInt((int) imageProvider.getScreenWidth()), rand.nextInt(4) + 10, 1, 1);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.19f, 0.54f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		drawBackground();
		updateClouds();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Cloud c : clouds)
			c.render(batch);

		arrived = true;
		if(touched){
			if (shapeAlpha < 1)
				shapeAlpha += 0.2f;
			if (shapeAlpha > 1)
				shapeAlpha = 1;
			if (shapeAlpha == 1) {
				bigNikolaY = bigNikolaYDest;
				bigNikola = animatedTextures[1];
				finished = true;
				arrived = true;
				touched = false;
			}
		}
		if (!finished) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			bigNikola = animation.getKeyFrame(elapsedTime);
			if (bigNikolaY < bigNikolaYDest) {
				bigNikolaY += 0.7f;
				arrivedTime = elapsedTime;
				arrived = false;
			} else {
				if ((elapsedTime > arrivedTime + period * 2) && (animation.isAnimationFinished(elapsedTime))) {
					bigNikola = animatedTextures[1];
					finished = true;
				}
			}
		}
		if (arrived) {
			if (titleAlpha < 1)
				titleAlpha += 0.1f;
		} else {
			if (Gdx.input.isTouched()) {
				touched = true;
			}
		}
		if(finished){
			if(shapeAlpha > 0)
				shapeAlpha -= 0.2f;
			if(shapeAlpha < 0)
				shapeAlpha = 0;
		}
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
		
		batch.draw(bigNikola, imageProvider.getScreenWidth()/20, bigNikolaY, bigNikolaWidth, bigNikolaHeight);
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, titleAlpha);
		batch.draw(title, imageProvider.getScreenWidth()/15, 75*imageProvider.getScreenHeight()/120f, titleWidth, titleHeight);
		batch.end();
		System.out.println(imageProvider.getScreenWidth()+" : "+imageProvider.getScreenHeight());

		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, shapeAlpha);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

	public void updateClouds() {
		for (int i = 0; i < clouds.length; i++) {
			if (clouds[i].getX() < -(clouds[i].getSprite().getWidth())) {
				if (rand.nextInt(2) == 0) {
					cloud.setY(clouds[i].getY());
					clouds[i].setSprite(cloud.getSprite());
				}
				if (rand.nextInt(2) == 1) {
					bigCloud.setY(clouds[i].getY());
					clouds[i].setSprite(bigCloud.getSprite());
				}
				clouds[i].setY(imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10);
				clouds[i].setX(imageProvider.getScreenWidth());
			}
		}
	}

	public void drawBackground() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		floor.render(shapeRenderer);
		renderDust();
		drawDust(shapeRenderer);
		shapeRenderer.end();

	}

	public void renderDust() {
		for (int i = 0; i < dust.length; i++) {
			dust[i].x -= speed;
			if (dust[i].getX() < -1) {
				dust[i] = new Rectangle(imageProvider.getScreenWidth(), rand.nextInt(4) + 10, 1, 1);
			}
		}
	}

	public void drawDust(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(0.55f, 0.39f, 0.15f, 1);
		for (int i = 0; i < dust.length; i++) {
			shapeRenderer.rect(dust[i].x, dust[i].y, dust[i].width, dust[i].height);

		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
