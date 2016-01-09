package com.subzero.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.subzero.background.Floor;
import com.subzero.background.Mountains;
import com.subzero.entities.BigCloud;
import com.subzero.entities.Cloud;
import com.subzero.entities.Podium;
import com.subzero.images.ImageProvider;
import com.subzero.runners.Runners;

public class CharacterSelectScreen implements Screen {
	private AssetManager assetManager;
	private Runners game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private ImageProvider imageProvider;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Floor floor;
	private Mountains mountains;
	private float speed = 1.0f;
	private Rectangle[] dust = new Rectangle[30];
	private Random rand;
	private Cloud[] clouds = new Cloud[2];
	private Cloud cloud;
	private BigCloud bigCloud;
	private Texture characterSelectText;
	private Podium nikolaPodium, ryanPodium;
	private Preferences pref;
	private String defaultCharacter;
	private Texture backButton, playButton;
	private Rectangle backButtonBounds, playButtonBounds;
	private Screen oldScreen;
	private float timePassed = 0, activeTime = 0.15f;
	private float soundVolume = 0.5f;

	public CharacterSelectScreen(Runners game, AssetManager assetManager, Screen screen) {
		this.game = game;
		this.assetManager = assetManager;
		this.oldScreen = screen;
		imageProvider = new ImageProvider();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		viewport = new FitViewport(imageProvider.getScreenWidth(), imageProvider.getScreenHeight(), camera);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		floor = new Floor();
		mountains = new Mountains(assetManager);
		rand = new Random();
		clouds[0] = new Cloud(imageProvider.getScreenWidth(), imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		clouds[1] = new Cloud(imageProvider.getScreenWidth() * 1.5f, imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		cloud = new Cloud(-50, clouds[0].getY(), 100, assetManager);
		bigCloud = new BigCloud(-50, clouds[1].getY(), 100, assetManager);
		characterSelectText = assetManager.get("CharacterSelectText.png", Texture.class); // 36pt text size Upheaval TT
		backButton = assetManager.get("Back.png", Texture.class);
		backButtonBounds = new Rectangle(3, imageProvider.getScreenHeight() - backButton.getHeight() / 2 - 6f, backButton.getWidth() / 2, backButton.getHeight() / 2);
		playButton = assetManager.get("Restart.png", Texture.class);
		playButtonBounds = new Rectangle(imageProvider.getScreenWidth()/2-playButton.getWidth()/4, 10, playButton.getWidth()/2, playButton.getHeight()/2);

		createDust();

		nikolaPodium = new Podium("Nikola", 40, 32, assetManager);
		ryanPodium = new Podium("Ryan", 120, 32, assetManager);
		nikolaPodium.setSelected(true);
		pref = Gdx.app.getPreferences("com.subzero.runners");
		defaultCharacter = pref.getString("defaultCharacter", "Nikola");

		if (defaultCharacter.equals("Nikola")) {
			nikolaPodium.setSelected(true);
			ryanPodium.setSelected(false);
		} else if (defaultCharacter.equals("Ryan")) {
			nikolaPodium.setSelected(false);
			ryanPodium.setSelected(true);
		}

	}

	@Override
	public void show() {
		timePassed = 0;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		timePassed += Gdx.graphics.getDeltaTime();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0.19f, 0.54f, 0.85f, 1);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		shapeRenderer.end();

		drawBackground();
		mountains.update(speed);
		updateClouds();
		for (Cloud c : clouds)
			c.update(speed);
		if (timePassed > activeTime)
			if (Gdx.input.isTouched()) {
				if (backButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
					assetManager.get("Select.wav", Sound.class).play(soundVolume);
					game.setScreen(oldScreen);
				}
			}
		if (timePassed > activeTime) {
			if (nikolaPodium.checkSelecting(camera)) {
				ryanPodium.setSelected(false);
				defaultCharacter = "Nikola";
				pref.putString("defaultCharacter", defaultCharacter);
				pref.flush();
			}
			if (ryanPodium.checkSelecting(camera)) {
				nikolaPodium.setSelected(false);
				defaultCharacter = "Ryan";
				pref.putString("defaultCharacter", defaultCharacter);
				pref.flush();
			}
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mountains.render(batch);
		for (Cloud c : clouds)
			c.render(batch);
		nikolaPodium.render(batch);
		ryanPodium.render(batch);
		batch.draw(characterSelectText, imageProvider.getScreenWidth() / 2 - characterSelectText.getWidth() / 4, imageProvider.getScreenHeight() - characterSelectText.getHeight(), characterSelectText.getWidth() / 2, characterSelectText.getHeight() / 2);
		batch.draw(backButton, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);
		batch.draw(playButton, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);

		batch.end();

	}

	public void createDust() {
		for (int i = 0; i < dust.length; i++) {
			dust[i] = new Rectangle(rand.nextInt((int) imageProvider.getScreenWidth()), rand.nextInt(4) + 10, 1, 1);
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

	public void drawDust(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(0.55f, 0.39f, 0.15f, 1);
		for (int i = 0; i < dust.length; i++) {
			shapeRenderer.rect(dust[i].x, dust[i].y, dust[i].width, dust[i].height);

		}
	}

	public void renderDust() {
		for (int i = 0; i < dust.length; i++) {
			dust[i].x -= speed;
			if (dust[i].getX() < -1) {
				dust[i] = new Rectangle(imageProvider.getScreenWidth(), rand.nextInt(4) + 10, 1, 1);
			}
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

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
