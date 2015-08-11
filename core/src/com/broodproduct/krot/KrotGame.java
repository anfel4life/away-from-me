package com.broodproduct.krot;

import com.badlogic.gdx.Game;
import com.broodproduct.krot.screen.GameScreen;
import com.broodproduct.krot.tool.AssetLoader;

public class KrotGame extends Game {
	
	@Override
	public void create () {
        AssetLoader.load();
        setScreen(new GameScreen());
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
