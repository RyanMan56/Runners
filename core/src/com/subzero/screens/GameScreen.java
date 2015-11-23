package com.subzero.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.subzero.background.Floor;
import com.subzero.entities.BigCloud;
import com.subzero.entities.Cactus;
import com.subzero.entities.Cloud;
import com.subzero.entities.Entity;
import com.subzero.entities.Nikola;
import com.subzero.entities.SmallCactus;
import com.subzero.entities.ThreeSmallCactus;
import com.subzero.entities.TwoSmallCactus;
import com.subzero.images.ImageProvider;
import com.subzero.runners.Runners;

public class GameScreen implements Screen {
	private Runners game;
	private AssetManager assetManager;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private ImageProvider imageProvider = new ImageProvider();
	private Floor floor;
	private ShapeRenderer shapeRenderer;
	private Nikola nikola;
	private Cactus[] cacti = new Cactus[2];
	private Random rand;
	private int spawnChance = 2;
	private boolean shouldCheck = false;
	private Cactus cactus, cactus2;
	private SmallCactus smallCactus, smallCactus2;
	private TwoSmallCactus twoSmallCactus, twoSmallCactus2;
	private ThreeSmallCactus threeSmallCactus, threeSmallCactus2;
	private Cloud clouds[] = new Cloud[2];
	private Cloud cloud;
	private BigCloud bigCloud;
	private Rectangle[] dust = new Rectangle[30];
	private boolean running = true;
	private BitmapFont font;
	private Label text, testText, gameOverText, gameOverScore, highScore;
	private LabelStyle textStyle;
	private float score = 0;
	private int cactusScore = -1;
	private boolean yAdjusted = false;
	private Rectangle endSlate, endSlateBorder;
	private Rectangle restartButton;
	private boolean restarting = false;
	private int scorePadding = 0;
	private boolean cactus0Passed = false, cactus1Passed = false;
	private Texture medal, scoreTex, hi;
	private float endScoreOffset, highScoreOffset;
	private Texture medalHolder, blankMedal, bronzeMedal, silverMedal, goldMedal, platinumMedal;

	public GameScreen(Runners game, AssetManager assetManager) {
		this.game = game;
		this.assetManager = assetManager;
		camera = new OrthographicCamera();
		viewport = new FitViewport(imageProvider.getScreenWidth(), imageProvider.getScreenHeight(), camera);
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		floor = new Floor();
		nikola = new Nikola(20, 12, 100, assetManager);
		rand = new Random();
		cacti[0] = new Cactus(imageProvider.getScreenWidth(), 12, 100, assetManager);
		cacti[1] = new Cactus(-20, 12, 100, assetManager);
		twoSmallCactus = new TwoSmallCactus(-50, 12, 100, assetManager);
		twoSmallCactus2 = new TwoSmallCactus(-50, 12, 100, assetManager);
		threeSmallCactus = new ThreeSmallCactus(-50, 12, 100, assetManager);
		threeSmallCactus2 = new ThreeSmallCactus(-50, 12, 100, assetManager);
		cactus = new Cactus(-50, 12, 100, assetManager);
		cactus2 = new Cactus(-50, 12, 100, assetManager);
		smallCactus = new SmallCactus(-50, 12, 100, assetManager);
		smallCactus2 = new SmallCactus(-50, 12, 100, assetManager);
		clouds[0] = new Cloud(imageProvider.getScreenWidth(), imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		clouds[1] = new Cloud(imageProvider.getScreenWidth() * 1.5f, imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		cloud = new Cloud(-50, clouds[0].getY(), 100, assetManager);
		bigCloud = new BigCloud(-50, clouds[1].getY(), 100, assetManager);
		font = new BitmapFont(Gdx.files.internal("fipps.fnt"));
		textStyle = new LabelStyle();
		textStyle.font = font;
		text = new Label("", textStyle);
		text.setPosition(imageProvider.getScreenWidth() / 2 - 5, imageProvider.getScreenHeight() - 20);
		//text.setOrigin(imageProvider.getScreenWidth()/2, imageProvider.getScreenHeight()-20);
		testText = new Label("", textStyle);
		testText.setPosition(0, imageProvider.getScreenHeight() - 2);
		//text.setColor(Color.ORANGE);
		//text.set
		//		font = new BitmapFont(Gdx.files.internal("fipps_game_over.fnt"));
		font = new BitmapFont(Gdx.files.internal("fipps.fnt"));
		textStyle.font = font;
		
		gameOverText = new Label("Game Over", textStyle);
		gameOverText.setPosition(imageProvider.getScreenWidth()/4.5f, imageProvider.getScreenHeight() / 1.3f);//+7f);
		gameOverText.setColor(0.996f, 0.557f, 0.227f, 0);
		endSlate = new Rectangle(imageProvider.getScreenWidth() / 5, gameOverText.getY() - gameOverText.getHeight()*1.75f, imageProvider.getScreenWidth() / 1.8f, 45);
		endSlateBorder = new Rectangle(endSlate.x - 1, endSlate.y - 1, endSlate.width + 2, endSlate.height + 2);
		restartButton = new Rectangle(endSlate.x, endSlate.y - endSlate.height / 2 - 3, endSlate.width / 2, endSlate.height / 2);
		gameOverScore = new Label("", textStyle);
		
		highScore = new Label("0", textStyle);
		
		medal = assetManager.get("Medal.png", Texture.class);
		scoreTex = assetManager.get("Score.png", Texture.class);
		hi = assetManager.get("High.png", Texture.class);
		
		blankMedal = assetManager.get("BlankMedal.png", Texture.class);
		bronzeMedal = assetManager.get("BronzeMedal.png", Texture.class);
		silverMedal = assetManager.get("SilverMedal.png", Texture.class);
		goldMedal = assetManager.get("GoldMedal.png", Texture.class);
		platinumMedal = assetManager.get("PlatinumMedal.png", Texture.class);
		medalHolder = blankMedal;

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
		//Gdx.gl.glClearColor(0, 0.47f, 0.67f, 1);
		//Gdx.gl.glClearColor(0.42f, 0.63f, 0.8f, 1);
		Gdx.gl.glClearColor(0.19f, 0.54f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Cloud c : clouds)
			c.render(batch);
		nikola.render(batch);
		for (Cactus c : cacti)
			c.render(batch);
		text.setText("" + (int) cactusScore);
		//		text.setText("Game Over");
		if (!running)
			gameOver();
		if (running)
			text.draw(batch, 1);
		//		testText.setText(""+cacti[0].getSpeed());
		//		testText.draw(batch, 1);
		batch.end();

		updateEnd();
	}

	public void update() {
		updateScore();
		updateSpeed();
		drawBackground();
		if (nikola.getHealth() > 0) {
			updateClouds();
			updateCacti();
		}
		checkCollisions();
		if (!running)
			restart();
	}

	public void updateEnd() {
		if (!running)
			gameOverShape();
	}
	
	public void gameOver() {
		gameOverText.draw(batch, 1);
		if (gameOverText.getColor().a < 0.5f) {
			gameOverText.setPosition(gameOverText.getX(), gameOverText.getY() + 0.5f);
		}
		if ((gameOverText.getColor().a > 0.5f) && (gameOverText.getColor().a < 1)) {
			gameOverText.setPosition(gameOverText.getX(), gameOverText.getY() - 0.5f);
		}
		if (gameOverText.getColor().a < 1) {
			gameOverText.setColor(gameOverText.getColor().r, gameOverText.getColor().g, gameOverText.getColor().b, gameOverText.getColor().a += 0.05f);
		}
	}

	public void gameOverShape() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(endSlateBorder.x, endSlateBorder.y, endSlateBorder.width, endSlateBorder.height);
		shapeRenderer.setColor(0.886f, 0.8f, 0.592f, 1);
		shapeRenderer.rect(endSlate.x, endSlate.y, endSlate.width, endSlate.height);

		shapeRenderer.setColor(0.827f, 0.616f, 0.376f, 1);
		shapeRenderer.line(endSlate.x + 2, endSlate.y + endSlate.height - 2, endSlate.x + endSlate.width - 2, endSlate.y + endSlate.height - 2);
		shapeRenderer.line(endSlate.x + 2, endSlate.y + endSlate.height - 2, endSlate.x + 2, endSlate.y + 3);
		shapeRenderer.line(endSlate.x, endSlate.y+0.5f, endSlate.x+endSlate.width, endSlate.y+0.5f);
		shapeRenderer.setColor(0.95f, 0.859f, 0.635f, 1);
		shapeRenderer.line(endSlate.x + 2, endSlate.y + 3, endSlate.x + endSlate.width - 2, endSlate.y + 3);
		shapeRenderer.line(endSlate.x + endSlate.width - 2, endSlate.y + 3, endSlate.x + endSlate.width - 2, endSlate.y + endSlate.height - 2);

		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(restartButton.x, restartButton.y, restartButton.width, restartButton.height);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(restartButton.x + 1, restartButton.y + 1, restartButton.width - 2, restartButton.height - 2);
		shapeRenderer.setColor(0.929f, 0.929f, 0.929f, 1);
		shapeRenderer.rect(restartButton.x + 1, restartButton.y + 1, restartButton.width - 2, restartButton.height / 2 - 1);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.triangle(restartButton.x + restartButton.width / 2 - 5, restartButton.y + restartButton.height / 1.5f + 2.5f - 0.5f, restartButton.x + restartButton.width / 2 - 5, restartButton.y + restartButton.height / 3 - 2.5f - 0.5f, restartButton.x + restartButton.width / 2 + 5, restartButton.y + restartButton.height / 2 - 0.5f);
		shapeRenderer.setColor(0, 0.686f, 0.278f, 1);
		shapeRenderer.triangle(restartButton.x + restartButton.width / 2 - 5, restartButton.y + restartButton.height / 1.5f + 2.5f, restartButton.x + restartButton.width / 2 - 5, restartButton.y + restartButton.height / 3 - 2.5f, restartButton.x + restartButton.width / 2 + 5, restartButton.y + restartButton.height / 2);
		shapeRenderer.end();
		
		endScoreOffset = 0;
		if(cactusScore >= 10)
			endScoreOffset = 8;
		if(cactusScore >= 100)
			endScoreOffset = 16;
		if(cactusScore >= 1000)
			endScoreOffset = 24;
		if(cactusScore >= 10000)
			endScoreOffset = 32;
		if(cactusScore >= 100000)
			endScoreOffset = 40;
		if(cactusScore >= 1000000)
			endScoreOffset = 48;
		
		gameOverScore.setPosition(endSlate.x+endSlate.width-20-endScoreOffset, endSlate.y+endSlate.height-gameOverScore.getHeight()-15);
		highScore.setPosition(endSlate.x+endSlate.width-20-highScoreOffset, gameOverScore.getY()-30);
		
		medalHolder = blankMedal;
		if(cactusScore >= 20)
			medalHolder = bronzeMedal;
		if(cactusScore >= 30)
			medalHolder = silverMedal;
		if(cactusScore >= 40)
			medalHolder = goldMedal;
		if(cactusScore >= 50)
			medalHolder = platinumMedal;
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		text.setPosition(imageProvider.getScreenWidth() / 2 - scorePadding, imageProvider.getScreenHeight() - 20);
		batch.draw(medal, endSlate.x+10, endSlate.y+endSlate.height-medal.getHeight()-4);
		batch.draw(scoreTex, endSlate.x+endSlate.width-10-scoreTex.getWidth(), endSlate.y+endSlate.height-scoreTex.getHeight()-4);
		gameOverScore.setText(""+(int)cactusScore);
		gameOverScore.draw(batch, 1);
		batch.draw(hi, endSlate.x+endSlate.width-10-hi.getWidth(), gameOverScore.getY()-12);
		highScore.draw(batch, 1);
		batch.draw(medalHolder, endSlate.x+9, endSlate.y+7.5f);
		batch.end();
	}

	public void restart() {
		if (!restarting)
			if ((restartButton.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) && (Gdx.input.isTouched())) {
				restarting = true;
				cacti[0] = new Cactus(imageProvider.getScreenWidth(), 12, 100, assetManager);
				cacti[1] = new Cactus(-20, 12, 100, assetManager);
				twoSmallCactus = new TwoSmallCactus(-50, 12, 100, assetManager);
				twoSmallCactus2 = new TwoSmallCactus(-50, 12, 100, assetManager);
				threeSmallCactus = new ThreeSmallCactus(-50, 12, 100, assetManager);
				threeSmallCactus2 = new ThreeSmallCactus(-50, 12, 100, assetManager);
				cactus = new Cactus(-50, 12, 100, assetManager);
				cactus2 = new Cactus(-50, 12, 100, assetManager);
				smallCactus = new SmallCactus(-50, 12, 100, assetManager);
				smallCactus2 = new SmallCactus(-50, 12, 100, assetManager);
				score = 0;
				cactus0Passed = false;
				cactus1Passed = false;
				cactusScore = -1;
				gameOverText.setColor(gameOverText.getColor().r, gameOverText.getColor().g, gameOverText.getColor().b, 0);
				gameOverText.setPosition(imageProvider.getScreenWidth()/4.5f, imageProvider.getScreenHeight() / 1.3f);//+7f);
//				cacti[0].setX(imageProvider.getScreenWidth());
//				cacti[1].setX(-20);
//				score = 0;
//				cacti[0].setSpeed(1);
//				cacti[1].setSpeed(1);
//				cacti[0].setSprite(cactus.getSprite());
//				cacti[0].setBigCactus(cactus.isBigCactus());
//				cacti[1].setSprite(cactus.getSprite());
//				cacti[1].setBigCactus(cactus.isBigCactus());
				clouds[0].setSpeed(0.25f);
				clouds[1].setSpeed(0.25f);
				clouds[0].setX(imageProvider.getScreenWidth());
				clouds[1].setX(imageProvider.getScreenWidth() * 1.5f);
//				cacti[0].setShouldUpdate(true);
//				cacti[1].setShouldUpdate(true);
				clouds[0].setShouldUpdate(true);
				clouds[1].setShouldUpdate(true);
				nikola.setHealth(100);
				restarting = false;
				running = true;
			}
	}

	public void updateSpeed() {
		if (cacti[0].getSpeed() <= 2.2f)
			for (int i = 0; i < cacti.length; i++) {
				cacti[i].increaseSpeedBy(0.0005f);
			}
	}

	public void updateScore() {
		if (cactusScore >= 10)
			scorePadding = 5;
		if (cactusScore >= 99)
			scorePadding = 10;
		if (cactusScore >= 999)
			scorePadding = 15;
		if (cactusScore >= 9999)
			scorePadding = 20;
		if (cactusScore >= 99999)
			scorePadding = 25;
		if (cactusScore >= 999999)
			scorePadding = 30;
		text.setPosition(imageProvider.getScreenWidth() / 2 - scorePadding, imageProvider.getScreenHeight() - 20);
		score += cacti[0].getSpeed() / 10;
	}

	// TODO When jumping on three small cactus you go through the third one for some reason...
	public boolean doesCollide(Entity e1, Entity e2) {
		for (int i = 0; i < e1.getBounds().length; i++) {
			if (e2.isBigCactus()) {
				for (int j = 0; j < e2.getBounds().length; j++) {
					if (e1.getBounds()[0].overlaps(e2.getBounds()[j]))
						return true;
				}
			} else {
				if (e1.getBounds()[0].overlaps(e2.getSprite().getBoundingRectangle())) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkCollisions() {
		for (int i = 0; i < cacti.length; i++)
			if (nikola.getSprite().getBoundingRectangle().overlaps(cacti[i].getSprite().getBoundingRectangle())) {
				if (doesCollide(nikola, cacti[i])) {
					cacti[0].setSpeed(0);
					running = false;

				}
			}
		if (!running) {
			nikola.setHealth(0);
			for (int i = 0; i < cacti.length; i++)
				cacti[i].setShouldUpdate(false);
			for (int i = 0; i < clouds.length; i++)
				clouds[i].setShouldUpdate(false);
		}
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

	public void updateCacti() {
		int randValue;
		
		if(!cactus0Passed)
			if(cacti[0].getX() + cacti[0].getSprite().getWidth() < nikola.getX()){
				cactusScore++;
				cactus0Passed = true;
			}
		if(!cactus1Passed)
			if(cacti[1].getX() + cacti[1].getSprite().getWidth() < nikola.getX()){
				cactusScore++;
				cactus1Passed = true;
			}
		
		if (cacti[0].getX() < -(cacti[0].getSprite().getWidth())) {
			randValue = rand.nextInt(4);
			if (randValue == 0) {
				cacti[0].setSprite(cactus.getSprite());
				cacti[0].setBigCactus(cactus.isBigCactus());
				//				cacti[0].setBounds(cactus.getBounds());
			} else if (randValue == 1) {
				cacti[0].setSprite(smallCactus.getSprite());
				cacti[0].setBigCactus(smallCactus.isBigCactus());
				//				cacti[0].setBounds(smallCactus.getBounds());
			} else if (randValue == 2) {
				cacti[0].setSprite(twoSmallCactus.getSprite());
				cacti[0].setBigCactus(twoSmallCactus.isBigCactus());
				//				cacti[0].setBounds(twoSmallCactus.getBounds());
			} else if (randValue == 3) {
				cacti[0].setSprite(threeSmallCactus.getSprite());
				cacti[0].setBigCactus(threeSmallCactus.isBigCactus());
				//				cacti[0].setBounds(threeSmallCactus.getBounds());
			}
			cacti[0].setX(imageProvider.getScreenWidth());
			cacti[0].getSprite().setY(12);
			shouldCheck = true;
			cactus0Passed = false;
		}

		if (shouldCheck) {
			if (cacti[1].getX() < -(cacti[1].getSprite().getWidth())) {
				randValue = rand.nextInt(4);
				if (cacti[0].getX() < imageProvider.getScreenWidth() / 2) {
					if (rand.nextInt(spawnChance) == 1) {
						if (randValue == 0) {
							cacti[1].setSprite(cactus2.getSprite());
							cacti[1].setBigCactus(cactus2.isBigCactus());
							//							cacti[1].setBounds(cactus.getBounds());
						}
						if (randValue == 1) {
							cacti[1].setSprite(smallCactus2.getSprite());
							cacti[1].setBigCactus(smallCactus2.isBigCactus());
							//							cacti[1].setBounds(smallCactus.getBounds());
						}
						if (randValue == 2) {
							cacti[1].setSprite(twoSmallCactus2.getSprite());
							cacti[1].setBigCactus(twoSmallCactus2.isBigCactus());
							//							cacti[1].setBounds(twoSmallCactus.getBounds());
						}
						if (randValue == 3) {
							cacti[1].setSprite(threeSmallCactus2.getSprite());
							cacti[1].setBigCactus(threeSmallCactus2.isBigCactus());
							//							cacti[1].setBounds(threeSmallCactus.getBounds());
						}
						cacti[1].setX(imageProvider.getScreenWidth());
						cacti[1].getSprite().setY(12);
						cactus1Passed = false;
					}
					shouldCheck = false;
				}
			}
		}
	}

	public void drawBackground() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		floor.render(shapeRenderer);
		renderDust();
		drawDust(shapeRenderer);
		//		shapeRenderer.rect(nikola.getBounds()[0].x, nikola.getBounds()[0].y, nikola.getBounds()[0].width, nikola.getBounds()[0].height);
		//		shapeRenderer.rect(nikola.getBounds()[1].x, nikola.getBounds()[1].y, nikola.getBounds()[1].width, nikola.getBounds()[1].height);
		//		for (int i = 0; i < cacti.length; i++)
		//			for (int j = 0; j < cacti[i].getBounds().length; j++)
		//				//shapeRenderer.rect(cacti[i].getBounds()[j].x, cacti[i].getBounds()[j].y, cacti[i].getBounds()[j].width, cacti[i].getBounds()[j].height);
		//				shapeRenderer.rect(cacti[i].getSprite().getBoundingRectangle().x, cacti[i].getSprite().getBoundingRectangle().y, cacti[i].getSprite().getBoundingRectangle().width, cacti[i].getSprite().getBoundingRectangle().height);
		shapeRenderer.end();

	}

	public void renderDust() {
		if (running) {
			for (int i = 0; i < dust.length; i++) {
				dust[i].x -= cacti[0].getSpeed();
				if (dust[i].getX() < -1) {
					dust[i] = new Rectangle(imageProvider.getScreenWidth(), rand.nextInt(4) + 10, 1, 1);
				}
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