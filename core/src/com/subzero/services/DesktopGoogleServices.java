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

package com.subzero.services;

import com.badlogic.gdx.Preferences;

public class DesktopGoogleServices implements IGoogleServices {
	@Override
	public void signIn() {
		System.out.println("DesktopGoogleServies: signIn()");
	}

	@Override
	public void signOut() {
		System.out.println("DesktopGoogleServies: signOut()");
	}

	@Override
	public void rateGame() {
		System.out.println("DesktopGoogleServices: rateGame()");
	}

	@Override
	public void submitScore(long score) {
		System.out.println("DesktopGoogleServies: submitScore(" + score + ")");
	}

	@Override
	public void showScores() {
		System.out.println("DesktopGoogleServies: showScores()");
	}

	@Override
	public boolean isSignedIn() {
		System.out.println("DesktopGoogleServies: isSignedIn()");
		return false;
	}
	
	@Override
	public void unlockAchievement(String achievementId){
		System.out.println("DesktopGoogleServices : unlockAchievement()");
	}
	
	@Override
	public void makePurchase(String itemID, Preferences pref){
		System.out.println("IInAppBillingService : makePurchase()");
	}
	
	@Override
	public void queryPurchases(Preferences pref){
		System.out.println("IInAppBillingService : queryPurchases()");
	}
}