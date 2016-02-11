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

package com.subzero.background;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.subzero.images.ImageProvider;

public class Floor {
	ImageProvider imageProvider = new ImageProvider();

	public void render(ShapeRenderer shapeRenderer) {
		//shapeRenderer.setColor(0.86f, 0.61f, 0.25f, 1); // Light
		//shapeRenderer.setColor(0.72f, 0.51f, 0.208f, 1); // Dark
		shapeRenderer.setColor(0.745f, 0.525f, 0.216f, 1);
		shapeRenderer.rect(0, 0, imageProvider.getScreenWidth(), 15);
		//shapeRenderer.setColor(0.72f, 0.51f, 0.208f, 1); // Light
		//shapeRenderer.setColor(0.612f, 0.43f, 0.176f, 1); // Dark
		shapeRenderer.setColor(0.643f, 0.455f, 0.184f, 1);
		shapeRenderer.rect(0, 9, imageProvider.getScreenWidth(), 6);
		shapeRenderer.setColor(0.745f, 0.525f, 0.216f, 1);
		shapeRenderer.rect(0, 15, imageProvider.getScreenWidth(), 25);
	}
}
