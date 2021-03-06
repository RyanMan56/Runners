/******************************************************************************
 * Copyright � 2016 Ryan Jones
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
import com.subzero.background.Mountains;
import com.subzero.entities.Cactus;
import com.subzero.entities.Cloud;
import com.subzero.entities.Entity;
import com.subzero.entities.Player;
import com.subzero.entities.Podium;
import com.subzero.entities.SmallCactus;
import com.subzero.entities.ThreeSmallCactus;
import com.subzero.entities.TwoSmallCactus;
import com.subzero.images.ImageProvider;
import com.subzero.runners.Runners;
import com.subzero.services.IGoogleServices;

public class GameScreen implements Screen {
	private AssetManager assetManager;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ImageProvider imageProvider = new ImageProvider();
	private Floor floor;
	private Mountains mountains;
	private ShapeRenderer shapeRenderer;
	private Player player;
	private Cactus[] cacti = new Cactus[2];
	private Random rand;
	private int spawnChance = 2;
	private boolean shouldCheck = false;
	private Cactus cactus, cactus2;
	private SmallCactus smallCactus, smallCactus2;
	private TwoSmallCactus twoSmallCactus, twoSmallCactus2;
	private ThreeSmallCactus threeSmallCactus, threeSmallCactus2;
	private Cloud clouds[] = new Cloud[2];
	private Rectangle[] dust = new Rectangle[30];
	private boolean running = true;
	private BitmapFont font;
	private Label text, testText, gameOverText, gameOverScore, highScore;
	private LabelStyle textStyle;
	private int cactusScore = -1;
	private Rectangle endSlate, endSlateBorder;
	private Rectangle restartButton;
	private boolean restarting = false;
	private int scorePadding = 0;
	private boolean cactus0Passed = false, cactus1Passed = false;
	private Texture medal, scoreTex, hi;
	private float endScoreOffset, highScoreOffset;
	private Texture medalHolder, blankMedal, bronzeMedal, silverMedal, goldMedal, platinumMedal;
	private float startEndSlateY, endEndSlateY;
	private float endSlateAlpha = 0;
	private float alphaValue;
	private boolean restartable = false;
	private boolean canPlayHitSound = true;
	private float soundVolume = 0.2f, musicVolume = 1f;
	private Preferences pref;
	private int highScoreValue;
	private Runners game;
	private Texture restart, pause, backButton, leaderboardsButton;
	private Rectangle restartBounds, pauseBounds, unpauseBounds, backButtonBounds, pausedBackBounds, leaderboardsButtonBounds;
	private Viewport viewport;
	private boolean paused = false;
	private Screen oldScreen;
	private Texture characterUnlocked;
	private Podium unlockPodium;
	private boolean shouldDrawCharacterUnlock = false;
	private long unlockTime;
	private Music music;
	private Texture musicButton, soundButton;
	private Rectangle musicButtonBounds, soundButtonBounds;
	private IGoogleServices googleServices;
	private boolean hasSubmitted = false;
	private boolean volumeButtonPressed = false;
	private boolean unlocksChecked = false;

	public GameScreen(Runners game, AssetManager assetManager, Screen screen, IGoogleServices googleServices) {
		this.game = game;
		this.assetManager = assetManager;
		this.oldScreen = screen;
		this.googleServices = googleServices;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		viewport = new FitViewport(imageProvider.getScreenWidth(), imageProvider.getScreenHeight(), camera);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		floor = new Floor();
		mountains = new Mountains(assetManager);
		player = new Player(20, 12, 100, assetManager);
		player.setSoundVolume(soundVolume);
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
		font = new BitmapFont(Gdx.files.internal("fipps.fnt"));
		textStyle = new LabelStyle();
		textStyle.font = font;
		text = new Label("", textStyle);
		text.setPosition(imageProvider.getScreenWidth() / 2 - 5, imageProvider.getScreenHeight() - 20);
		testText = new Label("", textStyle);
		testText.setPosition(0, imageProvider.getScreenHeight() - 2);
		font = new BitmapFont(Gdx.files.internal("fipps.fnt"));
		textStyle.font = font;

		pref = Gdx.app.getPreferences("com.subzero.runners");
		highScoreValue = pref.getInteger("score", 0);

		gameOverText = new Label("Game Over", textStyle);
		gameOverText.setPosition(imageProvider.getScreenWidth() / 4.5f, imageProvider.getScreenHeight() / 1.3f);//+7f);
		gameOverText.setColor(0.996f, 0.557f, 0.227f, 0);
		endSlate = new Rectangle(imageProvider.getScreenWidth() / 5, gameOverText.getY() - gameOverText.getHeight() * 1.75f, imageProvider.getScreenWidth() / 1.8f, 45);
		startEndSlateY = endSlate.y;
		endSlate.setY(imageProvider.getScreenHeight() / 5);
		endEndSlateY = endSlate.y;
		endSlateBorder = new Rectangle(endSlate.x - 1, endSlate.y - 1, endSlate.width + 2, endSlate.height + 2);
		restart = assetManager.get("Restart.png", Texture.class);
		restartButton = new Rectangle(endSlate.x - 17.5f, endSlate.y - endSlate.height / 2 - 3, endSlate.width / 2, endSlate.height / 2);
		gameOverScore = new Label("", textStyle);
		highScore = new Label("highScoreValue", textStyle);
		medal = assetManager.get("Medal.png", Texture.class);
		scoreTex = assetManager.get("Score.png", Texture.class);
		hi = assetManager.get("High.png", Texture.class);
		blankMedal = assetManager.get("BlankMedal.png", Texture.class);
		bronzeMedal = assetManager.get("BronzeMedal.png", Texture.class);
		silverMedal = assetManager.get("SilverMedal.png", Texture.class);
		goldMedal = assetManager.get("GoldMedal.png", Texture.class);
		platinumMedal = assetManager.get("PlatinumMedal.png", Texture.class);
		medalHolder = blankMedal;
		restartBounds = new Rectangle(restartButton.x + restart.getWidth() + 2, restartButton.y, restart.getWidth(), restart.getHeight());
		pause = assetManager.get("Pause.png", Texture.class);
		pauseBounds = new Rectangle(5, imageProvider.getScreenHeight() - pause.getHeight() - 5, pause.getWidth(), pause.getHeight());
		unpauseBounds = new Rectangle(restartBounds.x + 17.5f, imageProvider.getScreenHeight() / 2 - restart.getHeight() / 2, restartBounds.width, restartBounds.height);
		backButton = assetManager.get("BackButton.png", Texture.class);
		backButtonBounds = new Rectangle(restartButton.x, restartButton.y, backButton.getWidth(), backButton.getHeight());
		pausedBackBounds = new Rectangle(backButtonBounds.x + 17.5f, imageProvider.getScreenHeight() / 2 - backButton.getHeight() / 2, backButtonBounds.width, backButtonBounds.height);
		leaderboardsButton = assetManager.get("Leaderboards.png", Texture.class);
		leaderboardsButtonBounds = new Rectangle(restartBounds.x + leaderboardsButton.getWidth() + 2, backButtonBounds.y, leaderboardsButton.getWidth(), leaderboardsButton.getHeight());

		createDust();

		characterUnlocked = assetManager.get("CharacterUnlocked.png", Texture.class);

		music = assetManager.get("251461__joshuaempyre__arcade-music-loop.wav", Music.class);
		music.setVolume(musicVolume);
		music.setLooping(true);

		if (!pref.getBoolean("MusicMuted", false))
			musicButton = assetManager.get("Music.png", Texture.class);
		else
			musicButton = assetManager.get("MusicMuted.png", Texture.class);

		if (!pref.getBoolean("SoundMuted", false))
			soundButton = assetManager.get("Sound.png", Texture.class);
		else
			soundButton = assetManager.get("SoundMuted.png", Texture.class);

		soundButtonBounds = new Rectangle(imageProvider.getScreenWidth() - 5 - soundButton.getWidth(), imageProvider.getScreenHeight() - pause.getHeight() - 5, pause.getWidth(), pause.getHeight());
		musicButtonBounds = new Rectangle(soundButtonBounds.x - musicButton.getWidth() - 5, imageProvider.getScreenHeight() - pause.getHeight() - 5, pause.getWidth(), pause.getHeight());
	}

	public void createDust() {
		for (int i = 0; i < dust.length; i++) {
			dust[i] = new Rectangle(rand.nextInt((int) imageProvider.getScreenWidth()), rand.nextInt(4) + 10, 1, 1);
		}
	}

	@Override
	public void show() {
		player = new Player(20, 12, 100, assetManager);
		player.setSoundVolume(soundVolume);
		running = true;
		paused = false;
		performRestart();
		if (!pref.getBoolean("MusicMuted"))
			music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0.19f, 0.54f, 0.85f, 1);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		shapeRenderer.end();

		update();

		drawBackground();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
		if (running)
			mountains.update(cacti[0].getSpeed());
		mountains.render(batch);
		for (Cloud c : clouds)
			c.render(batch);
		player.render(batch);
		for (Cactus c : cacti)
			c.render(batch);
		text.setText("" + (int) cactusScore);
		if ((!running) && (!paused))
			gameOver();
		if (running) {
			text.draw(batch, 1);
			batch.draw(pause, pauseBounds.x, pauseBounds.y);
		}
		batch.end();

		updateEnd();
		drawPause();
		drawVolumeButtons();
	}

	public void checkPause() {
		if (running) {
			if (Gdx.input.justTouched()) {
				if (pauseBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
					if (!pref.getBoolean("SoundMuted"))
						assetManager.get("Select.wav", Sound.class).play(soundVolume);
					volumeButtonPressed = true;
					paused = true;
					running = false;
				}
			}
		} else {
			if (paused) {
				if (Gdx.input.justTouched()) {
					if (unpauseBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Select.wav", Sound.class).play(soundVolume);
						volumeButtonPressed = true;
						running = true;
						paused = false;
					}
					if (pausedBackBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Select.wav", Sound.class).play(soundVolume);
						music.pause();
						game.setScreen(oldScreen);
					}
				}
			}
		}
	}

	public void drawPause() {
		if (paused) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(0f, 0f, 0f, 0.2f);
			shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);

			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(restart, unpauseBounds.x, unpauseBounds.y);
			batch.draw(backButton, pausedBackBounds.x, pausedBackBounds.y, pausedBackBounds.width, pausedBackBounds.height);
			batch.end();
		}
	}

	public void drawVolumeButtons() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(soundButton, soundButtonBounds.x, soundButtonBounds.y);
		batch.draw(musicButton, musicButtonBounds.x, musicButtonBounds.y);
		batch.end();
	}

	public void updateVolumeControl() {
		if ((soundButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) && (Gdx.input.justTouched())) {
			volumeButtonPressed = true;
			if (pref.getBoolean("SoundMuted")) {
				pref.putBoolean("SoundMuted", false);
				pref.flush();
				soundButton = assetManager.get("Sound.png", Texture.class);
			} else {
				pref.putBoolean("SoundMuted", true);
				pref.flush();
				soundButton = assetManager.get("SoundMuted.png", Texture.class);
			}
		}

		if ((musicButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) && (Gdx.input.justTouched())) {
			volumeButtonPressed = true;
			if (pref.getBoolean("MusicMuted")) {
				pref.putBoolean("MusicMuted", false);
				pref.flush();
				musicButton = assetManager.get("Music.png", Texture.class);
				music.play();
			} else {
				pref.putBoolean("MusicMuted", true);
				pref.flush();
				musicButton = assetManager.get("MusicMuted.png", Texture.class);
				music.pause();
			}
		}
	}

	public void update() {
		checkPause();
		updateScore();
		updateSpeed();
		updateVolumeControl();

		if (paused) {
			music.pause();
		} else {
			if (!pref.getBoolean("MusicMuted"))
				music.play();
		}

		//		drawBackground();
		if (running) {
			updateClouds();
			updateCacti();
			for (Cloud c : clouds)
				c.update(cacti[0].getSpeed());
			player.update(volumeButtonPressed);
			for (Cactus c : cacti)
				c.update();

		}
		checkCollisions();
		if ((!running) && (!paused)) {
			restart();
			toMenu();
			leaderboardsPressed();
		}
		volumeButtonPressed = false;
	}

	public void updateEnd() {
		if ((!running) && (!paused))
			gameOverShape();
	}

	public void gameOver() {
		if (cactusScore > highScoreValue) {
			pref.putInteger("score", cactusScore);
			highScoreValue = cactusScore;
			pref.flush();
		}
		if (!unlocksChecked)
			checkUnlocks();

		if (shouldDrawCharacterUnlock) {

		} else {
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
			if (endSlate.y < startEndSlateY) {
				endSlate.y++;
				endSlateBorder.y++;
				restartButton.y++;
				restartBounds.y++;
				backButtonBounds.y++;
				leaderboardsButtonBounds.y++;
				endSlateAlpha = endSlate.y / (startEndSlateY);
			}
			if (endSlateAlpha < 1)
				restartable = false;
			else
				restartable = true;
			if (!hasSubmitted) {
				if (googleServices.isSignedIn()) {
					googleServices.submitScore(highScoreValue);
				}
				hasSubmitted = true;
			}
		}
		unlocksChecked = true;
	}

	public void checkUnlocks() {
		if (highScoreValue >= 5) {
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQBQ");
		}
		if (highScoreValue >= 10) {
			unlock("Ryan");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQBA");
		}
		if (highScoreValue >= 20) {
			unlock("Ash");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQBg");
		}
		if (highScoreValue >= 30) {
			unlock("Rob");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQBw");
		}
		if (highScoreValue >= 40) {
			unlock("BattleCat");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQCA");
		}
		if (highScoreValue >= 50) {
			unlock("Xorp");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQCQ");
		}
		if (highScoreValue >= 60) {
			unlock("Rootsworth");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQCg");
		}
		if (highScoreValue >= 70) {
			unlock("Snap");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQCw");
		}
		if (highScoreValue >= 80) {
			unlock("Metatron");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQDA");
		}
		if (highScoreValue >= 90) {
			unlock("Abaddon");
			googleServices.unlockAchievement("CgkIjIKHuuIVEAIQDQ");
		}
	}

	public void unlock(String name) {
		if (!pref.getBoolean(name, false)) {
			pref.putBoolean(name, true);
			pref.flush();

			setupUnlockMessage(name);
		}
	}

	public void setupUnlockMessage(String name) {
		unlockPodium = new Podium(name, assetManager);
		unlockPodium.setPos(88, 41);
		unlockPodium.setAnimate(true);
		unlockPodium.setSelected(true);

		shouldDrawCharacterUnlock = true;
		if (!pref.getBoolean("SoundMuted"))
			assetManager.get("Yay.wav", Sound.class).play(soundVolume);
		unlockTime = System.currentTimeMillis();
	}

	public void displayUnlockMessage(SpriteBatch batch) {
		endSlateAlpha = 0;

		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0f, 0f, 0f, 0.3f);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
		unlockPodium.render(batch);
		batch.draw(characterUnlocked, 10, imageProvider.getScreenHeight() - 19, characterUnlocked.getWidth() / 2, characterUnlocked.getHeight() / 2);
		batch.end();

		if (System.currentTimeMillis() - unlockTime > 1000)
			if (Gdx.input.isTouched())
				shouldDrawCharacterUnlock = false;
	}

	public void gameOverShape() {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, endSlateAlpha);
		shapeRenderer.rect(endSlateBorder.x, endSlateBorder.y, endSlateBorder.width, endSlateBorder.height);
		shapeRenderer.setColor(0.886f, 0.8f, 0.592f, endSlateAlpha);
		shapeRenderer.rect(endSlate.x, endSlate.y, endSlate.width, endSlate.height);

		shapeRenderer.setColor(0.827f, 0.616f, 0.376f, endSlateAlpha);
		shapeRenderer.line(endSlate.x + 2, endSlate.y + endSlate.height - 2, endSlate.x + endSlate.width - 2, endSlate.y + endSlate.height - 2);
		shapeRenderer.line(endSlate.x + 2, endSlate.y + endSlate.height - 2, endSlate.x + 2, endSlate.y + 3);
		shapeRenderer.line(endSlate.x, endSlate.y + 0.5f, endSlate.x + endSlate.width, endSlate.y + 0.5f);
		shapeRenderer.setColor(0.95f, 0.859f, 0.635f, endSlateAlpha);
		shapeRenderer.line(endSlate.x + 2, endSlate.y + 3, endSlate.x + endSlate.width - 2, endSlate.y + 3);
		shapeRenderer.line(endSlate.x + endSlate.width - 2, endSlate.y + 3, endSlate.x + endSlate.width - 2, endSlate.y + endSlate.height - 2);

		shapeRenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);

		endScoreOffset = 0;
		if (cactusScore >= 10)
			endScoreOffset = 8;
		if (cactusScore >= 100)
			endScoreOffset = 16;
		if (cactusScore >= 1000)
			endScoreOffset = 24;
		if (cactusScore >= 10000)
			endScoreOffset = 32;
		if (cactusScore >= 100000)
			endScoreOffset = 40;
		if (cactusScore >= 1000000)
			endScoreOffset = 48;

		highScoreOffset = 0;
		if (highScoreValue >= 10)
			highScoreOffset = 8;
		if (highScoreValue >= 100)
			highScoreOffset = 16;
		if (highScoreValue >= 1000)
			highScoreOffset = 24;
		if (highScoreValue >= 10000)
			highScoreOffset = 32;
		if (highScoreValue >= 100000)
			highScoreOffset = 40;
		if (highScoreValue >= 1000000)
			highScoreOffset = 48;

		gameOverScore.setPosition(endSlate.x + endSlate.width - 20 - endScoreOffset, endSlate.y + endSlate.height - gameOverScore.getHeight() - 15);
		gameOverScore.setColor(gameOverScore.getColor().r, gameOverScore.getColor().g, gameOverScore.getColor().b, endSlateAlpha);
		highScore.setPosition(endSlate.x + endSlate.width - 20 - highScoreOffset, gameOverScore.getY() - 30);
		highScore.setColor(highScore.getColor().r, highScore.getColor().g, highScore.getColor().b, endSlateAlpha);

		medalHolder = blankMedal;
		if (cactusScore >= 20)
			medalHolder = bronzeMedal;
		if (cactusScore >= 30)
			medalHolder = silverMedal;
		if (cactusScore >= 40)
			medalHolder = goldMedal;
		if (cactusScore >= 50)
			medalHolder = platinumMedal;

		batch.enableBlending();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		alphaValue = endSlateAlpha;
		if (endSlateAlpha < 0)
			alphaValue = 0;
		if (endSlateAlpha > 1)
			alphaValue = 1;

		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alphaValue);
		text.setPosition(imageProvider.getScreenWidth() / 2 - scorePadding, imageProvider.getScreenHeight() - 20);
		batch.draw(medal, endSlate.x + 10, endSlate.y + endSlate.height - medal.getHeight() - 4);
		batch.draw(scoreTex, endSlate.x + endSlate.width - 10 - scoreTex.getWidth(), endSlate.y + endSlate.height - scoreTex.getHeight() - 4);
		gameOverScore.setText("" + (int) cactusScore);
		gameOverScore.draw(batch, 1);
		batch.draw(hi, endSlate.x + endSlate.width - 10 - hi.getWidth(), gameOverScore.getY() - 12);
		highScore.setText("" + highScoreValue);
		highScore.draw(batch, 1);
		batch.draw(medalHolder, endSlate.x + 9, endSlate.y + 7.5f);
		batch.draw(restart, restartBounds.x, restartBounds.y, restartBounds.width, restartBounds.height);
		batch.draw(backButton, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);
		batch.draw(leaderboardsButton, leaderboardsButtonBounds.x, leaderboardsButtonBounds.y, leaderboardsButtonBounds.width, leaderboardsButtonBounds.height);
		batch.end();

		if (shouldDrawCharacterUnlock) {
			displayUnlockMessage(batch);
		}
	}

	public void restart() {
		if (!restarting)
			if (restartable)
				if ((restartBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) && (Gdx.input.justTouched())) {
					if (!pref.getBoolean("SoundMuted"))
						assetManager.get("Select.wav", Sound.class).play(soundVolume);
					performRestart();
				}
	}

	public void performRestart() {
		if (!pref.getBoolean("SoundMuted"))
			assetManager.get("Select.wav", Sound.class).play(soundVolume);
		restartable = false;
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
		cactus0Passed = false;
		cactus1Passed = false;
		cactusScore = -1;
		gameOverText.setColor(gameOverText.getColor().r, gameOverText.getColor().g, gameOverText.getColor().b, 0);
		gameOverText.setPosition(imageProvider.getScreenWidth() / 4.5f, imageProvider.getScreenHeight() / 1.3f);//+7f);
		clouds[0].setShouldUpdate(true);
		clouds[1].setShouldUpdate(true);
		player.setHealth(100);
		player.updateGameSpeed(1);
		endSlate.setY(endEndSlateY);
		endSlateBorder = new Rectangle(endSlate.x - 1, endSlate.y - 1, endSlate.width + 2, endSlate.height + 2);
		restartButton = new Rectangle(endSlate.x, endSlate.y - endSlate.height / 2 - 3, endSlate.width / 2, endSlate.height / 2);
		restartBounds.y = restartButton.y;
		backButtonBounds.y = restartBounds.y;
		leaderboardsButtonBounds.y = backButtonBounds.y;
		canPlayHitSound = true;
		hasSubmitted = false;
		restarting = false;
		running = true;
		unlocksChecked = false;
	}

	public void toMenu() {
		if ((backButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) && (Gdx.input.justTouched())) {
			if (!pref.getBoolean("SoundMuted"))
				assetManager.get("Select.wav", Sound.class).play(soundVolume);
			music.pause();
			game.setScreen(oldScreen);
		}
	}

	public void leaderboardsPressed() {
		if ((leaderboardsButtonBounds.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) && (Gdx.input.justTouched())) {
			if (!pref.getBoolean("SoundMuted"))
				assetManager.get("Select.wav", Sound.class).play(soundVolume);
			if (googleServices.isSignedIn())
				googleServices.showScores();
			else
				googleServices.signIn();
		}
	}

	public void updateSpeed() {
		if (running) {
			for (int i = 0; i < cacti.length; i++) {
				if (cacti[i].getSpeed() < 3.5f)
					cacti[i].increaseSpeedBy(0.0005f);
				if (cacti[i].getSpeed() > 3.5f)
					cacti[i].setSpeed(3.5f);

			}
			player.updateGameSpeed(cacti[0].getSpeed());
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
			if (player.getSprite().getBoundingRectangle().overlaps(cacti[i].getSprite().getBoundingRectangle())) {
				if (doesCollide(player, cacti[i])) {
					if (canPlayHitSound) {
						if (!pref.getBoolean("SoundMuted"))
							assetManager.get("Hit.wav", Sound.class).play(soundVolume);
						canPlayHitSound = false;
					}
					cacti[0].setSpeed(0);
					running = false;

				}
			}
		if ((!running) && (!paused)) {
			player.setHealth(0);
			for (int i = 0; i < cacti.length; i++)
				cacti[i].setShouldUpdate(false);
			for (int i = 0; i < clouds.length; i++)
				clouds[i].setShouldUpdate(false);
		}
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

	public void updateCacti() {
		int randValue;

		if (!cactus0Passed)
			if (cacti[0].getX() + cacti[0].getSprite().getWidth() < player.getX()) {
				cactusScore++;
				if (cactusScore > 0)
					if (!pref.getBoolean("SoundMuted"))
						assetManager.get("Point.wav", Sound.class).play(soundVolume);
				cactus0Passed = true;
			}
		if (!cactus1Passed)
			if (cacti[1].getX() + cacti[1].getSprite().getWidth() < player.getX()) {
				cactusScore++;
				if (cactusScore > 0)
					if (!pref.getBoolean("SoundMuted"))
						assetManager.get("Point.wav", Sound.class).play(soundVolume);
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
		music.dispose();
	}

}
