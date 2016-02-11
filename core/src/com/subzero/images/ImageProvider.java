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

package com.subzero.images;

public class ImageProvider {
	//private Vector2 size = Scaling.fit.apply(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 180, 120);
	private float SCREEN_WIDTH = 213.5f;//121.5f;//180; // 18/22 = 0.818 18*9 = 162 
	private float SCREEN_HEIGHT = 120;//81;//120; // 162 * 0.818 = 132.516
	//FIX THIS BECAUSE NIKOLA'S TOO WIDE
	private float aspectRatio = 18/22f;
//	private float SCREEN_WIDTH = Gdx.graphics.getWidth()*0.1875f;
//	private float SCREEN_HEIGHT = Gdx.graphics.getHeight()*0.1875f;
//	private float SCREEN_HEIGHT = SCREEN_WIDTH / (Gdx.graphics.getWidth()/Gdx.graphics.getHeight());
	// TODO This
	
	public float getScreenWidth(){
		return SCREEN_WIDTH;
	}
	
	public float getScreenHeight(){
		return SCREEN_HEIGHT; 
	}

}
