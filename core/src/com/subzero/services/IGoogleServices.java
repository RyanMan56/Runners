package com.subzero.services;

import com.badlogic.gdx.Preferences;

public interface IGoogleServices {
	public void signIn();

	public void signOut();

	public void rateGame();

	public void submitScore(long score);

	public void showScores();

	public boolean isSignedIn();
	
	public void unlockAchievement(String achievementId);
	
	public void makePurchase(String itemID, Preferences pref);
	
	public void queryPurchases(Preferences pref);
}
