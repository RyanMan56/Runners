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