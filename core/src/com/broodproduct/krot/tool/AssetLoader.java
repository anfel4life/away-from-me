package com.broodproduct.krot.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Texture texture;
	public static TextureRegion background;

	public static void load() {
		texture = new Texture(Gdx.files.internal("img/background_2.jpg"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		background = new TextureRegion(texture, 0, 0, 1024, 768);
	}

	public static void dispose() {
		texture.dispose();
	}
}