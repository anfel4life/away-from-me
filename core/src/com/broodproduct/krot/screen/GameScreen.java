package com.broodproduct.krot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.broodproduct.krot.render.GameRenderer;
import com.broodproduct.krot.render.GameWorld;
import com.broodproduct.krot.tool.Constants;
import com.broodproduct.krot.tool.InputHandler;

public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;
    private Box2DDebugRenderer debug = new Box2DDebugRenderer( true, true, true, true, true, true);

	public GameScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		world = new GameWorld();
        renderer = new GameRenderer(world);
        Gdx.input.setInputProcessor(new InputHandler(world, renderer, screenWidth / Constants.GAME_PIXEL_WIDTH, screenHeight / Constants.GAME_PIXEL_HEIGTH));
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
        debug.render(world.getBoxWorld(), renderer.getDebugCam().combined);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
        world.dispose();
        renderer.dispose();
	}
}
