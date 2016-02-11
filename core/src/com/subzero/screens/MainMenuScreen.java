/******************************************************************************
 * Copyright © 2016 Ryan Jones
 * 
 * This program is distributed under the terms of the
 * GNU Lesser General Public License. Version 3 or later.
 * You may obtain a copy of the license at
 * 
 * http://www.gnu.org/licenses/gpl.txt
 * http://www.gnu.org/licenses/lgpl.txt
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE. 
 ******************************************************************************/

package com.subzero.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.subzero.background.Floor;
import com.subzero.background.Mountains;
import com.subzero.entities.Cloud;
import com.subzero.images.ImageProvider;
import com.subzero.runners.Runners;
import com.subzero.services.IGoogleServices;

public class MainMenuScreen implements Screen {
	private AssetManager assetManager;
	private OrthographicCamera camera;
	private ImageProvider imageProvider;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Floor floor;
	private Random rand;
	private Cloud[] clouds = new Cloud[2];
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
	private Texture restart;
	private float restartX, restartY, restartWidth, restartHeight;
	private Rectangle restartBounds;
	private Texture characterSelect;
	private Rectangle characterSelectBounds;
	private Texture leaderboards;
	private Rectangle leaderboardsBounds;
	private GameScreen gameScreen;
	private CharacterSelectScreen characterSelectScreen;
	private Runners game;
	private Viewport viewport;
	private Mountains mountains;
	private Preferences pref;
	private String defaultCharacter;
	private float timePassed = 0, initActiveTime = 0.15f, activeTime;
	private float soundVolume = 0.2f;
	private Music music;
	private boolean shouldPlay = true;
	private IGoogleServices googleServices;
	private boolean productsChecked = false;

	public MainMenuScreen(Runners game, AssetManager assetManager, IGoogleServices googleServices) {
		this.game = game;
		this.assetManager = assetManager;
		this.googleServices = googleServices;
		imageProvider = new ImageProvider();
		pref = Gdx.app.getPreferences("com.subzero.runners");
		defaultCharacter = pref.getString("defaultCharacter", "Nikola");
		initCharacters();
		if (!pref.getBoolean("MusicMuted", false)) {
			pref.putBoolean("MusicMuted", false);
			pref.flush();
		}
		if (!pref.getBoolean("SoundMuted", false)) {
			pref.putBoolean("SoundMuted", false);
			pref.flush();
		}
		gameScreen = new GameScreen(game, assetManager, this, googleServices);
		characterSelectScreen = new CharacterSelectScreen(game, assetManager, this, gameScreen, googleServices);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		viewport = new FitViewport(imageProvider.getScreenWidth(), imageProvider.getScreenHeight(), camera);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		floor = new Floor();
		mountains = new Mountains(assetManager);
		rand = new Random();
		bigNikolaY = -122;
		bigNikolaYDest = -20;
		restart = assetManager.get("Restart.png", Texture.class);
		restartX = 130;
		restartY = 60;
		restartWidth = restart.getWidth();
		restartHeight = restart.getHeight();
		restartBounds = new Rectangle(restartX, restartY, restartWidth, restartHeight);
		characterSelect = assetManager.get("CharacterSelectButton.png", Texture.class);
		characterSelectBounds = new Rectangle(restartX, restartY - 27, characterSelect.getWidth(), characterSelect.getHeight());
		leaderboards = assetManager.get("Leaderboards.png", Texture.class);
		leaderboardsBounds = new Rectangle(restartX, characterSelectBounds.y-27, leaderboards.getWidth(), leaderboards.getHeight());

		clouds[0] = new Cloud(imageProvider.getScreenWidth(), imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		clouds[1] = new Cloud(imageProvider.getScreenWidth() * 1.5f, imageProvider.getScreenHeight() - 25 + rand.nextInt(20) - 10, 100, assetManager);
		title = assetManager.get("Menu.png", Texture.class);
		textureRegion = new TextureRegion(assetManager.get(defaultCharacter + "-j.png", Texture.class));
		animatedTextures = textureRegion.split(18, 22)[0];
		animation = new Animation(period, animatedTextures);
		animation.setPlayMode(PlayMode.LOOP);
		bigNikola = animation.getKeyFrame(elapsedTime);

		bigNikolaHeight = 122;
		bigNikolaWidth = 100;

		titleWidth = title.getWidth() * 1.2f;//imageProvider.getScreenWidth() / (180 / 152f);
		titleHeight = title.getHeight() * 1.2f;//titleWidth / (152 / 18f);

		createDust();

		music = assetManager.get("248117__zagi2__retro-gaming-loop.wav", Music.class);
		music.setVolume(1);
		
		googleServices.signIn();
	}

	public void createDust() {
		for (int i = 0; i < dust.length; i++) {
			dust[i] = new Rectangle(rand.nextInt((int) imageProvider.getScreenWidth()), rand.nextInt(4) + 10, 1, 1);
		}
	}

	@Override
	public void show() {
		defaultCharacter = pref.getString("defaultCharacter", "Nikola");
		bigNikola = new TextureRegion(assetManager.get(defaultCharacter + ".png", Texture.class));
		timePassed = 0;
		if ((shouldPlay) && (!pref.getBoolean("MusicMuted")))
			music.play();
		
		if(!productsChecked){
			googleServices.queryPurchases(pref);
			productsChecked = true;
		}
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

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
		mountains.render(batch);
		for (Cloud c : clouds)
			c.render(batch);

		arrived = true;
		if (touched) {
			if (shapeAlpha < 1)
				shapeAlpha += 0.2f;
			if (shapeAlpha > 1)
				shapeAlpha = 1;
			if (shapeAlpha == 1) {
				bigNikolaY = bigNikolaYDest;
				bigNikola = new TextureRegion(assetManager.get(defaultCharacter + ".png", Texture.class));
				finished = true;
				activeTime = timePassed + initActiveTime;
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
				if ((elapsedTime > arrivedTime + period * 4) && (animation.isAnimationFinished(elapsedTime))) {
					bigNikola = new TextureRegion(assetManager.get(defaultCharacter + ".png", Texture.class));
					finished = true;
					activeTime = timePassed + initActiveTime;
				}
			}
		}
		if (arrived) {
			if (titleAlpha < 1)
				titleAlpha += 0.1f;
		} else {
			if (timePassed > activeTime)
				if (Gdx.input.justTouched()) {
					touched = true;
				}
		}
		if (finished) {
			if (timePassed > activeTime) {
				activeTime = 0;
				if (Gdx.input.justTouched()) {
					if (restartBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Select.wav", Sound.class).play(soundVolume);
						shouldPlay = false;
						music.pause();
						game.setScreen(gameScreen);
					}
					if (characterSelectBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Select.wav", Sound.class).play(soundVolume);
						music.pause();
						shouldPlay = false;
						game.setScreen(characterSelectScreen);
					}
					if (leaderboardsBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Select.wav", Sound.class).play(soundVolume);
						leaderboardsPressed();
					}
				}
			}
			if (shapeAlpha > 0)
				shapeAlpha -= 0.2f;
			if (shapeAlpha < 0)
				shapeAlpha = 0;
		}
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
		batch.draw(bigNikola, imageProvider.getScreenWidth() / 20, bigNikolaY, bigNikolaWidth, bigNikolaHeight);
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, titleAlpha);
		batch.draw(title, 1+imageProvider.getScreenWidth() / 15, 90, titleWidth, titleHeight);
		batch.draw(restart, restartX, restartY, restartWidth, restartHeight);
		batch.draw(characterSelect, characterSelectBounds.x, characterSelectBounds.y);
		batch.draw(leaderboards, leaderboardsBounds.x, leaderboardsBounds.y);
		batch.end();

		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, shapeAlpha);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
	
	public void leaderboardsPressed(){
		googleServices.showScores();
	}

	public void updateClouds() {
		for (int i = 0; i < clouds.length; i++) {
			if (clouds[i].getX() < -(clouds[i].getSprite().getWidth())) {
				clouds[i].randomCloud();
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

	private void initCharacters() {
		if (!pref.getBoolean("Nikola", false)) {
			pref.putBoolean("Nikola", true);
			pref.flush();
		}
		if (!pref.getBoolean("ComingSoon", false)) {
			pref.putBoolean("ComingSoon", true);
			pref.flush();
		}

		initCharacter("Ryan");
		initCharacter("Ash");
		initCharacter("Rob");
		initCharacter("Xorp");
		initCharacter("BattleCat");
		initCharacter("Rootsworth");
		initCharacter("Snap");
		initCharacter("Abaddon");
		initCharacter("Metatron");
	}

	private void initCharacter(String name) {
		if (!pref.getBoolean(name, false)) {
			pref.putBoolean(name, false);
			pref.flush();
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
