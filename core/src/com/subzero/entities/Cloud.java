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

package com.subzero.entities;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cloud extends Entity {
	private Texture smallCloud, bigCloud;

	public Cloud(float x, float y, float health, AssetManager assetManager) {
		super(x, y, health, assetManager);
		smallCloud = assetManager.get("Cloud.png", Texture.class);
		bigCloud = assetManager.get("Cloud2.png", Texture.class);
		texture = smallCloud;
		sprite = new Sprite(texture, 20, 11);
		sprite.setX(x);
		sprite.setY(y);
		speed = 0.1f;
	}
	
	public void update(float gameSpeed){
		if (shouldUpdate) {
			x -= gameSpeed/16f;
			sprite.setX(x);
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	/**
	 * Randomly selects which cloud texture this cloud should take
	 */
	public void randomCloud(){
		Random rand = new Random();
		int value = rand.nextInt(2);
		if(value == 0)
			texture = smallCloud;
		else
			texture = bigCloud;
		sprite = new Sprite(texture);
		sprite.setX(x);
		sprite.setY(y);
	}

}
