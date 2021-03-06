package com.broodproduct.krot.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.broodproduct.krot.tool.AssetLoader;
import com.broodproduct.krot.tool.Constants;

public class GameRenderer {
    private OrthographicCamera cam, debugCam;
    private SpriteBatch batcher;
    private BitmapFont font;
    private GameWorld world;
    private ShapeRenderer sr = new ShapeRenderer();

    public GameRenderer(GameWorld world) {
        this.world = world;
        cam = new OrthographicCamera(Constants.GAME_PIXEL_WIDTH, Constants.GAME_PIXEL_HEIGTH);
        cam.setToOrtho(false);
        debugCam = new OrthographicCamera(world.unitWidth, world.unitHeight);
        debugCam.setToOrtho(false, world.unitWidth, world.unitHeight);
        batcher = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera follow
//        Body boxBody = world.getDestroBox().getBody();
//        if(boxBody != null) {
//            Vector2 boxPosition = Constants.scaleToPixel(boxBody.getWorldCenter());
//            cam.position.x = boxPosition.x;
////            cam.position.y = boxPosition.y;
////            cam.update();
//
//            debugCam.position.x = Constants.scaleToUnit(boxPosition.x);
////            debugCam.position.y = Constants.scaleToUnit(boxPosition.y);
//            debugCam.update();
//        }

        batcher.setProjectionMatrix(cam.combined);
        batcher.begin();
        batcher.draw(AssetLoader.background, 0, 0, Constants.GAME_PIXEL_WIDTH, Constants.GAME_PIXEL_HEIGTH);
        font.draw(batcher, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 520);
        font.draw(batcher, "E: fly back ", Constants.GAME_PIXEL_WIDTH - 150, Constants.GAME_PIXEL_HEIGTH - 20);
        font.draw(batcher, "R: fly forward ", Constants.GAME_PIXEL_WIDTH - 150, Constants.GAME_PIXEL_HEIGTH - 40);
        font.draw(batcher, "Enter: reset ", Constants.GAME_PIXEL_WIDTH - 150, Constants.GAME_PIXEL_HEIGTH - 60);
        font.draw(batcher, "Space: pull forward ", Constants.GAME_PIXEL_WIDTH - 150, Constants.GAME_PIXEL_HEIGTH - 80);
        batcher.end();

//        //power bar
//        sr.setProjectionMatrix(cam.combined);
//        sr.setColor(Color.RED);
//        sr.begin(ShapeRenderer.ShapeType.Filled);
//        sr.rect((Constants.GAME_PIXEL_WIDTH / 2) - 150, Constants.GAME_PIXEL_HEIGTH - 40, world.getDestroBox().getPullPower() * 50, 20);
//        sr.end();

    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public OrthographicCamera getDebugCam() {
        return debugCam;
    }

    public void dispose() {
        batcher.dispose();
        font.dispose();
        sr.dispose();
    }
}
