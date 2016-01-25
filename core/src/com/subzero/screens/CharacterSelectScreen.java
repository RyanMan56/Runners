package com.subzero.screens;

import java.util.ArrayList;
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
	private Podium nikolaPodium, ryanPodium, p1, p2, p3;
	private ArrayList<Podium> podiums = new ArrayList<Podium>();
	private Preferences pref;
	private String defaultCharacter;
	private Texture backButton, playButton;
	private Rectangle backButtonBounds, playButtonBounds;
	private Screen oldScreen, gameScreen;
	private float timePassed = 0, activeTime = 0.15f;
	private float soundVolume = 0.5f;
	private float leftBorder = 15;
	private float rightBorder = 15; // Remember to add the right-most podium x to this!
	private float x1, x2;
	private float velocity, gravity = 0.9f, displacement;

	public CharacterSelectScreen(Runners game, AssetManager assetManager, Screen screen, Screen gameScreen) {
		this.game = game;
		this.assetManager = assetManager;
		this.oldScreen = screen;
		this.gameScreen = gameScreen;
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
		playButtonBounds = new Rectangle(imageProvider.getScreenWidth() / 2 - playButton.getWidth() / (1.5f * 2), 5, playButton.getWidth() / 1.5f, playButton.getHeight() / 1.5f);

		createDust();

		podiums.add(new Podium("Nikola", assetManager));
		podiums.add(new Podium("Ryan", assetManager));
		podiums.add(new Podium("Ash", assetManager));
		podiums.add(new Podium("Rob", assetManager));
		podiums.add(new Podium("Xorp", assetManager));
		podiums.add(new Podium("ComingSoon", assetManager));
		podiums.get(0).setSelected(true);
		pref = Gdx.app.getPreferences("com.subzero.runners");
		defaultCharacter = pref.getString("defaultCharacter", "Nikola");

		sort();
		rightBorder = imageProvider.getScreenWidth() - 36 - 15; // TODO change right border
	}

	@Override
	public void show() {
		timePassed = 0;
		for(Podium podium : podiums)
			podium.checkPrefs();
	}

	private void sort() {
		for (int i = 0; i < podiums.size(); i++) {
			podiums.get(i).setPos((i + 1) * 100 - 85 + displacement, 41);
		}
		if (podiums.get(0).getX() > leftBorder)
			displacement -= 1;
		else if (podiums.get(podiums.size() - 1).getX() + podiums.get(podiums.size() - 1).getWidth() < rightBorder)
			displacement += 1;

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
		if (timePassed > activeTime) {
			if (Gdx.input.justTouched()) {
				if (backButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
					assetManager.get("Select.wav", Sound.class).play(soundVolume);
					game.setScreen(oldScreen);
				}
				if (playButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
					assetManager.get("Select.wav", Sound.class).play(soundVolume);
					game.setScreen(gameScreen);
				}
			}

			for(Podium podium : podiums){
				if(podium.checkSelecting(camera))
					setOnlySelected(podium.getName());
			}
				

			scroll();
		}                                                                      

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mountains.render(batch);
		for (Cloud c : clouds)
			c.render(batch);
		for(Podium podium : podiums)
			podium.render(batch);
		batch.draw(characterSelectText, imageProvider.getScreenWidth() / 2 - characterSelectText.getWidth() / 4, imageProvider.getScreenHeight() - characterSelectText.getHeight(), characterSelectText.getWidth() / 2, characterSelectText.getHeight() / 2);
		batch.draw(backButton, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);
		batch.draw(playButton, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);

		batch.end();

	}

	public void scroll() {
		if (Gdx.input.isTouched()) {
			x1 = camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
			if (x2 != -100)
				velocity = ((x2 - x1) / Gdx.graphics.getDeltaTime()) / 59;
		}
		if ((podiums.get(0).getX() - velocity < leftBorder) && (podiums.get(podiums.size() - 1).getX() + podiums.get(podiums.size() - 1).getWidth() - velocity > rightBorder)) {
			displacement -= velocity;
			velocity *= gravity;
		}
		sort();
		if (Gdx.input.isTouched())
			x2 = x1;
		else
			x2 = -100;
	}

	/**
	 * Makes sure that this is the only selected Podium, deselects all others
	 * and makes this the default character
	 * 
	 * @param name
	 *            The name of the selected character's Podium
	 */
	private void setOnlySelected(String name) {
		for (Podium podium : podiums) {
			if (!podium.getName().equals(name))
				podium.setSelected(false);
		}
		defaultCharacter = name;
		pref.putString("defaultCharacter", defaultCharacter);
		pref.flush();
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
