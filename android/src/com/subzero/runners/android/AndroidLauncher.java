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

package com.subzero.runners.android;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.subzero.runners.Runners;
import com.subzero.services.IGoogleServices;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {

	private GameHelper _gameHelper;
	private final static int REQUEST_CODE_UNUSED = 9002;
	private IInAppBillingService mService;
	private Preferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//		 Create the GameHelper
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);

		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {

			@Override
			public void onSignInSucceeded() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSignInFailed() {
				// TODO Auto-generated method stub

			}
		};

		_gameHelper.setup(gameHelperListener);
		
//		signIn();
		
		Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
		serviceIntent.setPackage("com.android.vending");
		bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
//		queryPurchases();
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		initialize(new Runners(this), config);
	}
	
	ServiceConnection mServiceConn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = IInAppBillingService.Stub.asInterface(service);
		}
	};

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				//@Override
				public void run() {
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				//@Override
				public void run() {
					_gameHelper.signOut();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		// Replace the end of the URL with the package of your game
		String str = "https://play.google.com/store/apps/details?id=com.subzero.runners";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void submitScore(long score) {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
//			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		} else {
			// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		else {
			// Maybe sign in here then redirect to showing scores?
		}
	}
	
	@Override
	public void unlockAchievement(String achievementId) {
		if(isSignedIn() == true)
			Games.Achievements.unlock(_gameHelper.getApiClient(), achievementId);
	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}

	@Override
	protected void onStart() {
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1001){
			int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
			String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
			String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
			
			if(resultCode == RESULT_OK){
				try{
					JSONObject jo = new JSONObject(purchaseData);
					String sku = jo.getString("productId");
					String name = null;
					
					if(sku.equals("ryan"))
						name = "Ryan";
					else if(sku.equals("ash"))
						name = "Ash";
					else if(sku.equals("rob"))
						name = "Rob";
					else if(sku.equals("battle_cat"))
						name = "BattleCat";
					else if(sku.equals("xorp"))
						name = "Xorp";
					else if(sku.equals("rootsworth"))
						name = "Rootsworth";
					else if(sku.equals("snap"))
						name = "Snap";
					else if(sku.equals("metatron"))
						name = "Metatron";
					else if(sku.equals("abaddon"))
						name = "Abaddon";
					
					pref.putBoolean(name, true);
					pref.flush();
				}catch(JSONException e){
//					alert("Failed to parse purchase data.");
					e.printStackTrace();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(mService != null){
			unbindService(mServiceConn);
		}
	}

	@Override
	public void makePurchase(String itemID, Preferences pref) {
		this.pref = pref;
		try {
			Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), itemID, "inapp", null);
			PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
			startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SendIntentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void queryPurchases(Preferences pref) {
		this.pref = pref;
		try {
			Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
			int response = ownedItems.getInt("RESPONSE_CODE");
			if(response == 0){
				ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
				
				for(int i = 0; i < ownedSkus.size(); i++){
					String name = null;
					
					if(ownedSkus.get(i).equals("ryan"))
						name = "Ryan";
					else if(ownedSkus.get(i).equals("ash"))
						name = "Ash";
					else if(ownedSkus.get(i).equals("rob"))
						name = "Rob";
					else if(ownedSkus.get(i).equals("battle_cat"))
						name = "BattleCat";
					else if(ownedSkus.get(i).equals("xorp"))
						name = "Xorp";
					else if(ownedSkus.get(i).equals("rootsworth"))
						name = "Rootsworth";
					else if(ownedSkus.get(i).equals("snap"))
						name = "Snap";
					else if(ownedSkus.get(i).equals("metatron"))
						name = "Metatron";
					else if(ownedSkus.get(i).equals("abaddon"))
						name = "Abaddon";
					
					pref.putBoolean(name, true);
					pref.flush();
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

}
