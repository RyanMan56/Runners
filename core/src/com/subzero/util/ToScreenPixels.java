package com.subzero.util;

import com.subzero.images.ImageProvider;

public class ToScreenPixels {
	static ImageProvider imageProvider = new ImageProvider();

	public static float toScreenWidthPixels(float in){
		return in*imageProvider.getScreenWidth()/180f;
	}
	public static float toScreenHeightPixels(float in){
		return in*imageProvider.getScreenHeight()/120f;
	}
	
}
